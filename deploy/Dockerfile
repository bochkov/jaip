FROM sb-java
ENV TZ=Asia/Yekaterinburg
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
VOLUME /tmp
WORKDIR /opt
ADD jaip.jar /opt/jaip.jar
CMD ["java","-Xms32m","-Xmx64m","-Dfile.encoding=UTF-8","-Djava.security.egd=file:/dev/./urandom","-jar","jaip.jar"]
