Highcharts.common_theme = {
    colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
    navigation: {
        buttonOptions: {
            theme: {
                stroke: '#CCCCCC'
            }
        }
    },
    credits: { enabled: false },
    legend: { enabled: false },
    tooltip: { crosshairs: true, dateTimeLabelFormats: { day:"%e %B, %H:%M, %A" }},
    yAxis: { title: {text: null }},
    xAxis: { gridLineWidth: 1, type: "datetime", labels: { format: '{value:%e %b}' } },
    global: { useUTC: false },
    lang: {
        months: ['января', 'февраля', 'марта', 'апреля', 'мая', 'июня',
            'июля', 'августа', 'сентября', 'октября', 'ноября', 'декабря'],
        shortMonths: ['янв', 'фев', 'мар', 'апр', 'май', 'июн', 'июл',
            'авг', 'сен', 'окт', 'ноя', 'дек'],
        weekdays: ['воскресенье', 'понедельник', 'вторник', 'среда', 'четверг', 'пятница', 'суббота']
    }
};

var chartApp = angular.module('chartApp', []);
chartApp
    .directive('hcDateChart', function(){
        return {
            restrict: 'C',
            replace: true,
            scope: {
                chartData: '='
            },
            controller: function ($scope, $element, $attrs) {},
            link: function (scope, element, attrs) {
                var color = element.attr("chart-color");
                var chart = new Highcharts.Chart({
                    title: { text: element.attr("chart-title") },
                    chart: {
                        renderTo: element.attr("id"),
                        zoomType: 'x',
                        spacingRight: 20,
                        backgroundColor: {
                            linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
                            stops: [[0, 'rgb(255, 255, 255)'], [1, 'rgb(240, 240, 255)']]
                        },
                        plotBackgroundColor: 'rgba(255, 255, 255, .9)',
                        plotShadow: true,
                        plotBorderWidth: 1
                    },
                    tooltip: { crosshairs: true, dateTimeLabelFormats: { day:"%e %B, %A" }},
                    xAxis: { maxZoom: 14 * 24 * 3600000 },
                    yAxis: { title: { text: null }, minorTickInterval: 'auto', lineWidth: 1, tickWidth: 1 },
                    plotOptions: {
                        area: {
                            lineWidth: 1,
                            marker: { enabled: false },
                            shadow: false,
                            states: { hover: { lineWidth: 2 } },
                            threshold: null,
                            fillColor: {
                                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, Highcharts.getOptions().colors[color]],
                                    [1, Highcharts.Color(Highcharts.getOptions().colors[color]).setOpacity(0).get('rgba')]
                                ]
                            },
                            series: { pointInterval: 24 * 3600 * 1000 }
                        }
                    },
                    series: [{
                        type: 'area',
                        name: element.attr('chart-title').split(" ")[0],
                        color: Highcharts.getOptions().colors[color],
                        data: scope.data
                    }]
                });
                scope.$watch("chartData", function (newValue) {
                    chart.series[0].setData(newValue, true);
                }, true);
            }
        }
    })
    .directive('hcWeatherChart', function(){
        return {
            restrict: 'C',
            replace: true,
            scope: {
                chartData: '='
            },
            controller: function ($scope, $element, $attrs) {},
            link: function (scope, element, attrs) {
                var color = element.attr("chart-color");
                var chart = new Highcharts.Chart({
                    title: { text: element.attr("chart-title") },
                    chart: {
                        type: "spline",
                        renderTo: element.attr("id")
                    },
                    xAxis: { tickInterval: 24 * 3600 * 1000 },
                    tooltip: { valueSuffix: element.attr("chart-value-suffix") },
                    series: [{
                        name: element.attr("chart-tooltip"),
                        color: Highcharts.getOptions().colors[color],
                        pointInterval: 3 * 3600 * 1000,
                        data: scope.data
                    }]
                });
                scope.$watch("chartData", function (newValue) {
                    chart.series[0].setData(newValue, true);
                }, true);
            }
        }
    })
    .directive('hcAverageChart', function(){
        return {
            restrict: 'C',
            replace: true,
            scope: {
                chartDataRanges: '=',
                chartDataAverages: '='
            },
            controller: function ($scope, $element, $attrs) {},
            link: function (scope, element, attrs) {
                var color = element.attr("chart-color");
                var chart = new Highcharts.Chart({
                    title: { text: element.attr("chart-title") },
                    chart: { renderTo: element.attr("id") },
                    tooltip: {
                        crosshairs: true,
                        shared: true,
                        formatter: function(){
                            var s = Highcharts.dateFormat("%e %B %Y, %A", this.x);
                            var temp = this.points[0];
                            s += "<br/><b>" + temp.series.name + "</b>: " + temp.point.y + " °C";
                            var range = this.points[1];
                            s += "<br/>Минимум: " + range.point.low + " °C";
                            s += "<br/>Максимум: " + range.point.high + " °C";
                            return s;
                        }
                    },
                    series: [{
                        data: scope.chartDataAverages,
                        zIndex: 1,
                        name: element.attr("chart-tooltip"),
                        marker: { fillColor: 'white', lineWidth: 2, lineColor: Highcharts.getOptions().colors[0] }
                    }, {
                        name: 'Мин и макс',
                        data: scope.chartDataRanges,
                        type: "arearange",
                        lineWidth: 0, fillOpacity: 0.3, zIndex: 0, color: Highcharts.getOptions().colors[0]
                    }]
                });
                scope.$watch("chartDataAverages", function (newValue) {
                    chart.series[0].setData(newValue, true);
                }, true);
                scope.$watch("chartDataRanges", function (newValue) {
                    chart.series[1].setData(newValue, true);
                }, true);
            }
        }
    })
    .directive('hcColumnChart', function(){
        return {
            restrict: 'C',
            replace: true,
            scope: {
                chartData1: '=',
                chartData2: '='
            },
            controller: function ($scope, $element, $attrs) {},
            link: function (scope, element, attrs) {
                var color = element.attr("chart-color");
                var chart = new Highcharts.Chart({
                    title: { text: element.attr("chart-title") },
                    chart: {
                        type: 'column',
                        renderTo: element.attr("id")
                    },
                    tooltip: {
                        crosshairs: true,
                        shared: true,
                        valueSuffix: element.attr("chart-value-suffix"),
                        dateTimeLabelFormats: { day:"%e %B, %A" }
                    },
                    plotOptions: {
                        series: {
                            stacking: 'normal',
                            pointPadding: 0,
                            groupPadding: 0.1,
                            pointInterval: 24 * 3600 * 1000
                        }
                    },
                    series: [{
                        color: "#65CFDB",
                        data: scope.chartData1,
                        name: element.attr("chart-name1")
                    },{
                        color: Highcharts.getOptions().colors[6],
                        data: scope.chartData2,
                        name: element.attr("chart-name2")
                    }]
                });
                scope.$watch("chartData1", function (newValue) {
                    chart.series[0].setData(newValue, true);
                }, true);
                scope.$watch("chartData2", function (newValue) {
                    chart.series[1].setData(newValue, true);
                }, true);
            }
        }
    });