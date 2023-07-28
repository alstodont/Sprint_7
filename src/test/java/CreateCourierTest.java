import com.example.pojo.CreateCourier;
import com.example.pojo.LoginCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static com.example.controllers.CourierController.*;
import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_OK;

public class CreateCourierTest {


    @Test
    @DisplayName("Успешное создание курьера")
    public void successSingleTest() {
        LoginCourier loginCourier = new LoginCourier("NarutoUzumakovich","12345");
        CreateCourier createCourier = new CreateCourier("NarutoUzumakovich","12345","NARUTOOOOOOO");
        if (executeLogin(loginCourier).getStatusCode() == SC_OK) {
            executeDelete(loginCourier);
        } else {
            Response response = executeCreate(createCourier);
            response.then().assertThat().statusCode(SC_CREATED)
                    .and()
                    .body("ok", equalTo(true));
        }
    }

    @Test
    @DisplayName("Падение по дублю")
    public void failDoubleTest() {
        CreateCourier createCourier = new CreateCourier("NarutoUzumakovich","12345","NARUTOOOOOOO");
        executeCreate(createCourier);
        Response response = executeCreate(createCourier);
        response.then().assertThat().statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

    }

    @After
    public void deleteChanges() {
        LoginCourier loginCourier = new LoginCourier("NarutoUzumakovich","12345");
        executeDelete(loginCourier);
    }
}
