FROM java:8
ENV TZ=Asia/Yekaterinburg
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
VOLUME /tmp
WORKDIR /opt
ADD jaip.jar /opt/jaip.jar
CMD ["java","-Xms32m","-Xmx64m","-jar","jaip.jar"]
