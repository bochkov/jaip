FROM java:8
ENV TZ=Asia/Yekaterinburg
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
VOLUME /tmp
WORKDIR /opt
ADD jaip-1.0.jar /opt/jaip-1.0.jar
CMD ["java","-Xms256m","-Xmx1g","-jar","jaip-1.0.jar"]
