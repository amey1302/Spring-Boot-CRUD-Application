FROM maven:3.8.5-openjdk-17

WORKDIR /books

COPY . .

RUN ./gradlew clean build

CMD ./gradlew bootRun