FROM java:8
ENV TZ=Asia/Yekaterinburg
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
VOLUME /tmp
WORKDIR /opt
ADD jaip.jar /opt/jaip.jar
CMD ["java","-Xms256m","-Xmx1g","-jar","jaip.jar"]
