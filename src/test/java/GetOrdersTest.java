import com.example.pojo.CreateCourier;
import com.example.pojo.CreateOrder;
import com.example.pojo.LoginCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import java.util.HashMap;

import static com.example.controllers.CourierController.*;
import static com.example.controllers.OrderController.*;
import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

public class GetOrdersTest {
    LoginCourier loginCourier = new LoginCourier("NarutoUzumakovich","12345");
    CreateOrder createOrder = new CreateOrder("Shikamaru","Nara","Konoha, 123 apt.",4,"+7-800-355-35-35", 5,"2020-06-06","Sasuke, come back to Konoha", new String[]{"BLACK"});
    private int courierId;
    private int orderId;


    @Test
    @DisplayName("Успешное (200) получение списка заказов")
    public void successGetAllOrdersTest() {
        executeOrder(createOrder);
        Response response = executeListOrder(new HashMap<String, String>());
        response.then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("orders", hasSize(greaterThan(0)));
    }

    @Test
    @DisplayName("Успешное получение заказов для курьера")
    public void successGetOrdersWithCourierTest() {
        courierId = getCourierId(loginCourier);
        orderId = parseOrderIdFromResponse(executeOrder(createOrder));
        executeAcceptOrder(courierId, orderId);
        Response response = executeListOrder(courierId);
        response.then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("orders.id", hasItem(orderId));
    }

    @Test
    @DisplayName("Ошибка получения заказов")
    public void failGetOrdersTest() {
        CreateCourier createCourier = new CreateCourier("NarutoUzumakovich","12345","NARUTOOOOOOO");
        LoginCourier loginCourier = new LoginCourier("NarutoUzumakovich","12345");
        if (executeLogin(loginCourier).getStatusCode() == SC_OK) {
            executeDelete(loginCourier);
        } else {
            executeCreate(createCourier);
            executeDelete(loginCourier);
            Response response = executeListOrder(courierId);
            response.then().assertThat().statusCode(SC_NOT_FOUND)
                    .and()
                    .body("message", equalTo("Курьер с идентификатором " + courierId + " не найден"));
        }
    }

    @After
    public void deleteChanges() {
        executeDelete(loginCourier);
        executeDeleteOrder(orderId);
    }
}
