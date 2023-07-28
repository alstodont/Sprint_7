
import com.example.pojo.CreateCourier;
import com.example.pojo.LoginCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static com.example.controllers.CourierController.executeCreate;
import static com.example.controllers.CourierController.executeLogin;
import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

@RunWith(Parameterized.class)
public class LoginCourierParameterizedTest {
    private final LoginCourier pathToLogin; // pathToLogin - путь к JSON с учётными данными для логина курьера

    public LoginCourierParameterizedTest(LoginCourier pathToLogin) {
        this.pathToLogin = pathToLogin;
    }


    @Parameterized.Parameters
    public static Object[][] getJsonLoginVariable() {
        return new Object[][] {
                {new LoginCourier("","12345")},
                {new LoginCourier("NarutoUzumakovich","")}
        };
    }
    @Test
    @DisplayName("Ошибка при недостаточности данных для входа - параметризованный")
    public void failLoginTestWithoutRequiredFields() {
        CreateCourier createCourier = new CreateCourier("NarutoUzumakovich","12345","NARUTOOOOOOO");
        executeCreate(createCourier);
        Response response = executeLogin(pathToLogin);
        // Проверить наличие ошибки при отправлении неверных учётных данных (отсутствие логина или пароля)
        response.then().assertThat().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}
