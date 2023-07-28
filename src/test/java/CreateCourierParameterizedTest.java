import com.example.pojo.CreateCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import static com.example.controllers.CourierController.*;
import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

@RunWith(Parameterized.class)
public class CreateCourierParameterizedTest {
    private final CreateCourier credential; // credential - путь к JSON с учётными данными для создания курьера
    public CreateCourierParameterizedTest(CreateCourier credential) {
        this.credential = credential;
    }


    @Parameterized.Parameters
    public static Object[][] getJsonLoginVariable() {
        return new Object[][] {
                {new CreateCourier("","12345","NARUTOOOOOOO")},
                {new CreateCourier("NarutoUzumakovich","","NARUTOOOOOOO")}
        };
    }
    @Test
    @DisplayName("Падение (400) при создании курьера")
    public void failCreateTest() {
        Response response = executeCreate(credential);
        // Проверить наличие ошибки при отправлении неверных учётных данных (отсутствие логина или пароля)
        response.then().assertThat().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
