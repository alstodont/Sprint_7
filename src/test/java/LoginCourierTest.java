import com.example.pojo.CreateCourier;
import com.example.pojo.LoginCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static com.example.controllers.CourierController.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

public class LoginCourierTest {

    @Test
    @DisplayName("Успешная авторизация курьера")
    public void successCourierLoginTest() {
        CreateCourier createCourier = new CreateCourier("NarutoUzumakovich","12345","NARUTOOOOOOO");
        executeCreate(createCourier);
        LoginCourier loginCourier = new LoginCourier("NarutoUzumakovich","12345");
        Response response = executeLogin(loginCourier);
        response.then().assertThat().statusCode(SC_OK)
                .and()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Попытка зайти под несуществующим курьером")
    public void failLoginNonExistCourierTest() {
        LoginCourier loginCourier = new LoginCourier("JolineKudjo","asdksa%jkhdk!");
        Response response = executeLogin(loginCourier);
        response.then().assertThat().statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Попытка зайти под неверным логином")
    public void failLoginWithWrongLoginTest() {
        LoginCourier loginCourier = new LoginCourier("NarutoUzumakovich123","12345");
        Response response = executeLogin(loginCourier);
        response.then().assertThat().statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Попытка зайти с неверным паролем")
    public void failLoginWithWrongPasswordTest() {
        LoginCourier loginCourier = new LoginCourier("NarutoUzumakovich","123452937dsfhjl");
        Response response = executeLogin(loginCourier);
        response.then().assertThat().statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void deleteChanges() {
        LoginCourier loginCourier = new LoginCourier("NarutoUzumakovich","12345");
        executeDelete(loginCourier);
    }
}
