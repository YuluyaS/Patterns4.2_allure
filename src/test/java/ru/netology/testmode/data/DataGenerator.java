package ru.netology.testmode.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import lombok.val;
//import lombok.var;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final Faker FAKER = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
    .setBaseUri("http://localhost")
    .setPort(9999)
    .setAccept(ContentType.JSON)
    .setContentType(ContentType.JSON)
    .log(LogDetail.ALL)
    .build();

    static void sendRequest(DataGenerator.RegistrationDto user) {
        given()
                .spec(requestSpec)

                .body(new RegistrationDto(user.getLogin(), user.getPassword(), user.getStatus()))
                //.body(new RegistrationDto("vasya", "password", "active"))
                .when().log().all()
                .post("/api/system/users")
                .then().log().all()
                .statusCode(200);

    // private static void sendRequest(RegistrationDto user) {
        // TODO: отправить запрос на указанный в требованиях path, передав в body запроса объект user
        //  и не забудьте передать подготовленную спецификацию requestSpec.
        //  Пример реализации метода показан в условии к задаче.
    }

    public static String getRandomLogin() {
        // TODO: добавить логику для объявления переменной login и задания её значения, для генерации
        //  случайного логина используйте faker
        return FAKER.name().username();
    }

    public static String getRandomPassword() {
        // TODO: добавить логику для объявления переменной password и задания её значения, для генерации
        //  случайного пароля используйте faker
        return FAKER.internet().password();
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto getUser(String status) {
            // TODO: создать пользователя user используя методы getRandomLogin(), getRandomPassword() и параметр status
            return new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
        }

        public static RegistrationDto getRegisteredUser(String status) {
            var user = getUser(status);
            sendRequest(user);
            // TODO: объявить переменную registeredUser и присвоить ей значение возвращённое getUser(status).
            // Послать запрос на регистрацию пользователя с помощью вызова sendRequest(registeredUser)
            return user;
        }
    }

    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }
}
