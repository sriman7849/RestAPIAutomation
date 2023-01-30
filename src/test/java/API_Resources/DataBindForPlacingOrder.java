package API_Resources;

import pojo.OrderDetails;
import pojo.Orders;

import java.util.ArrayList;
import java.util.List;

public class DataBindForPlacingOrder {

    public Orders placingOrderPayload(String productId){
        OrderDetails orderDetail = new OrderDetails();
        orderDetail.setCountry("India");
        orderDetail.setProductOrderedId(productId);

        List<OrderDetails> orderDetailList = new ArrayList<OrderDetails>();
        orderDetailList.add(orderDetail);
        Orders orders = new Orders();
        orders.setOrders(orderDetailList);

        return orders;

    }
}
