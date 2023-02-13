#FROM gradle:7.5
FROM 1217jdk/spring_konlpy:0.0

WORKDIR /var/jenkins_home/workspace/backend/

COPY ./ ./

RUN gradle clean build --no-daemon

CMD ["java", "-jar", "./build/libs/easssue-0.0.1-SNAPSHOT.jar"]
