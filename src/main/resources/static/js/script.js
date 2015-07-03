var myApp = angular.module('myApp', ['chartApp']);

myApp
    .filter('html', function($sce) {
        return function(val){ return $sce.trustAsHtml(val); }
    })
    .filter('gtn', function() {
        return function(value) { return value > 0; }
    })
    .filter('ltn', function() {
        return function(value) { return value < 0; }
    })
    .filter('to_mmHg', function () {
        return function(value) { return value * 0.7501; }
    })
    .filter('wind_direction', function (){
        return function(value){
            var direc = ['Северный', 'Северо-северо-восточный', 'Северо-восточный', 'Востоко-северо-восточный',
                'Восточный', 'Востоко-юго-восточный', 'Юго-восточный', 'Юго-юго-восточный',
                'Южный', 'Юго-юго-западный', 'Юго-западный', 'Западно-юго-западный',
                'Западный', 'Западно-северо-западный', 'Северо-западный', 'Северо-северо-западный'];
            var key = parseInt(value / 22.5);
            while (key > 15) key -= 15;
            return direc[key];
        }
    })
    .controller('WeatherController', function($scope, $http) {

        $scope.city = 'Екатеринбург';
        $scope.weather_animate_spin = false;

        Highcharts.setOptions(Highcharts.common_theme);

        var myMap;

        $scope.weather = {};
        $scope.forecast = {};
        $scope.forecast_hourly = {};
        $scope.get_weather = function () {
            $scope.weather_animate_spin = true;
            var offset = 12 * 60 * 60 * 1000;

            $scope.weather_done = false;
            $http.get("/api/weather/" + $scope.city + "/").success(function (data) {
                $scope.weather_done = true;
                $scope.weather = data;
                if (typeof ymaps === "undefined")
                    return;
                ymaps.ready(function(){
                    if (typeof myMap === "undefined") {
                        myMap = new ymaps.Map("map", {center: [data.coord.lat, data.coord.lon], zoom: 10});
                        myMap.controls.add(new ymaps.control.ZoomControl());
                    }
                    else
                        myMap.setCenter([data.coord.lat, data.coord.lon]);
                });
            });

            $scope.daily = {ranges:[], averages: [], rain: [], snow: []};
            $scope.daily_done = false;
            $http.get("/api/forecast/daily/" + $scope.city + "/").success(function (data) {
                $scope.daily_done = true;
                $scope.forecast = data;
                for (var i = 0; i < data.list.length; ++i){
                    $scope.daily.ranges.push([data.list[i].dt * 1000 - offset, data.list[i].temp.min, data.list[i].temp.max]);
                    $scope.daily.averages.push([data.list[i].dt * 1000 - offset, data.list[i].temp.day]);
                    $scope.daily.rain.push([data.list[i].dt * 1000 - offset, data.list[i].rain]);
                    $scope.daily.snow.push([data.list[i].dt * 1000 - offset, data.list[i].snow]);
                }
            });

            $scope.hourly = {temp: [], humidity: [], pressure: []};
            $scope.hourly_done = false;
            $http.get("/api/forecast/" + $scope.city + "/").success(function(data){
                $scope.hourly_done = true;
                $scope.forecast_hourly = data;
                for (var i = 0; i < data.list.length; ++i){
                    $scope.hourly.temp.push([data.list[i].dt * 1000, data.list[i].main.temp]);
                    $scope.hourly.humidity.push([data.list[i].dt * 1000, data.list[i].main.humidity]);
                    $scope.hourly.pressure.push([data.list[i].dt * 1000, data.list[i].main.pressure]);
                }
            });

            $scope.$watchGroup(['weather_done', 'hourly_done', 'daily_done'], function(newValues, oldValues, scope){
                scope.weather_animate_spin = !(newValues[0] && newValues[1] && newValues[2]);
            });
        }
    })
    .controller('ExchangeController', function($scope, $http){
        $scope.exchange = [];

        $scope.get = function(valuteId, sign, order){
            $http.get("/api/cbr/rate/" + valuteId +"/").success(function (data) {
                var res = data;
                res.sign = sign;
                res.order = order;
                $scope.exchange[order] = res;
            });
        };

        $scope.get_exchange = function(){
            $scope.exchange_animate_spin = true;

            $scope.get("R01235", "$", 0);
            $scope.get("R01239", "€", 1);
            $scope.get("R01035", "£", 2);

            $scope.exchange_animate_spin = false;
        };

        $scope.get_rates = function() {
            Highcharts.setOptions(Highcharts.common_theme);
            $scope.dollar = $scope.get_valute_rates("R01235");
            $scope.euro = $scope.get_valute_rates("R01239");
            $scope.pound = $scope.get_valute_rates("R01035");
        };

        $scope.get_valute_rates = function(valuteId) {
            var d = {};
            $http.get("/api/cbr/rates/" + valuteId + "/").success(function(data) {
                d.data = [];
                d.startDate = data.rates[data.rates.length - 1].currentDate;
                var offset = 6 * 60 * 60 * 1000;
                for (var i = 0; i < data.rates.length; ++i)
                    d.data.push([data.rates[i].currentDate - offset, data.rates[i].currentValue]);
            });
            return d;
        };
    })
    .controller('ForismaticController', function ($scope, $http) {
        $scope.forismatic = {};
        $scope.forismatic_animate_spin = false;
        $scope.get_forismatic = function(){
            $scope.forismatic_animate_spin = true;
            $http.get("/api/forismatic/").success(function(data){
                $scope.forismatic_animate_spin = false;
                $scope.forismatic = data;
            });
        }
    })
    .controller('NewsController', function($scope, $http){
        $scope.news = {};
        $scope.get_news = function(slug){
            $http.get("/api/news/" + slug + "/").success(function(data){
                $scope.news = data;
            })
        }
    })
    .controller("PdfController", function($scope){
    })
    .controller('ImageController', function($scope){
        $scope.path = "";
        $scope.random_image = function(count, path) {
            var curPath = $scope.path;
            while (true) {
                if ($scope.path !== curPath)
                    break;
                $scope.path = path + Math.floor(Math.random() * count + 1) + ".png";
            }
        }
    });