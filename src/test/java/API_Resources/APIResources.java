package API_Resources;
//enum is special class in java which has collection of constants or  methods
public enum APIResources {
	
	ADD_PLACE_API("/maps/api/place/add/json"),
	GET_PLACE_API("/maps/api/place/get/json"),
	DELETE_PLACE_API("/maps/api/place/delete/json"),
	Login_API("/api/ecom/auth/login"),
	ADD_PRODUCT_API("/api/ecom/product/add-product"),
	PLACE_ORDER_API("/api/ecom/order/create-order"),
	GET_ORDER_API("/api/ecom/order/get-orders-details"),
	DELETE_PRODUCT_API("/api/ecom/product/delete-product/{productId}");
	private String resource ;

	APIResources(String resource)
	{
		this.resource=resource;
	}

	public String getResource()
	{
		return resource;
	}
}
