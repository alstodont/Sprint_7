import com.example.pojo.CreateOrder;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static com.example.controllers.OrderController.executeOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.SC_CREATED;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest {

    private final CreateOrder pathToOrderRequestBody;
    public CreateOrderParameterizedTest(CreateOrder pathToOrderRequestBody) {
        this.pathToOrderRequestBody = pathToOrderRequestBody;
    }


    @Parameterized.Parameters
    public static Object[][] colorForOrder() {
        return new Object[][] {
                {new CreateOrder("Shikamaru","Nara","Konoha, 123 apt.",4,"+7-800-355-35-35", 5,"2020-06-06","Asuma, come back to Konoha", new String[]{"BLACK"})},
                {new CreateOrder("Temari","Sobaku no","neKonoha, 13 apt.",3,"+7-800-355-35-35", 3,"2020-06-06","Gaara, come back to noKonoha", new String[]{"GREY"})},
                {new CreateOrder("Asuma","Sarutobi","Konoha, 73 apt.",2,"+7-800-355-35-35", 4,"2020-06-06","Shikamaru, come back to Konoha", new String[]{"BLACK", "GREY"})},
                {new CreateOrder("Itachi","Uciha","Konoha, 52 apt.",1,"+7-800-355-35-35", 2,"2020-06-06","Sasuke, come back to Konoha", new String[]{""})}
        };
    }
    @Test
    @DisplayName("Успешное создание заказов параметризованное")
    public void successCreateOrderTest() {
        Response response = executeOrder(pathToOrderRequestBody);
        response.then().assertThat().statusCode(SC_CREATED)
                .and()
                .body("track", notNullValue());
    }
}
