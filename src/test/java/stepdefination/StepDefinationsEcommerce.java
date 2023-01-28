package stepdefination;

import API_Resources.APIResources;
import API_Resources.DataBindForLogin;
import API_Resources.DataBindForPlacingOrder;
import API_Resources.util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.LoginResponseForEcommerce;
import pojo.ViewOrders;

import java.io.*;


import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class StepDefinationsEcommerce extends util {
    RequestSpecification request;
    static Response response;
    DataBindForLogin data = new DataBindForLogin();
    DataBindForPlacingOrder placeOrder = new DataBindForPlacingOrder();
    static String token;
    static String userId;
    static String productId;
    static String orderNumber;

    @Override
    public RequestSpecification requestSpecificationForEcommerce() throws IOException {
        return super.requestSpecificationForEcommerce();
    }

    /**
     * SCENARIO 1 -> LOGIN
     */

    @Given("Body payload  with credentials {string} and {string}")
    public void bodyPayloadWithCredentialsAnd(String userEmail, String userPassword) throws IOException {
        request = given().urlEncodingEnabled(false).spec(requestSpecificationForEcommerce())
                .body(data.addLoginPayload(userEmail, userPassword));
    }

    @When("user calls the resource {string} with {string} method")
    public void userCallsTheResourceWithMethod(String resource, String methodName) {
        System.out.println(APIResources.valueOf(resource).getResource() + "->" + methodName);
        if (methodName.equalsIgnoreCase("POST")) {
            response = request.when().post(APIResources.valueOf(resource).getResource());
        } else if (methodName.equalsIgnoreCase("GET")) {
            response = request.when().get(APIResources.valueOf(resource).getResource());
        } else {
            response = request.when().delete(APIResources.valueOf(resource).getResource());
        }
    }

    @Then("the API call will get success with status code {int}")
    public void theAPICallWillGetSuccessWithStatusCode(int statusCode) {
        LoginResponseForEcommerce responseForEcommerce = response.as(LoginResponseForEcommerce.class);
        token = responseForEcommerce.getToken();
        userId = responseForEcommerce.getUserId();
        assertEquals(response.getStatusCode(), statusCode);
    }

    /**
     * SCENARIO 2 -> ADDING PRODUCT
     */

    @Given("The authorization token is fetched with the data {string}, {string}, {string}, {string},{string},{string}")
    public void theAuthorizationTokenIsFetchedWithTheData(String productName, String productCategory,
                                                          String productSubCategory, String productPrice,
                                                          String productDescription, String productFor) throws IOException {
        PrintStream stream = new PrintStream(new FileOutputStream("AddProductEcommerce.txt"));
        RequestSpecification addProductBaseReq = new RequestSpecBuilder()
                .setRelaxedHTTPSValidation()
                .setBaseUri(util.retrieveGlobalProperties("baseUrl"))
                .addHeader("authorization", token)
                .addFilter(RequestLoggingFilter.logRequestTo(stream))
                .addFilter(ResponseLoggingFilter.logResponseTo(stream))
                .build();


        request = given().spec(addProductBaseReq)
                .param("productName", productName)
                .param("productAddedBy", userId) /*UserId */
                .param("productCategory", productCategory)
                .param("productSubCategory", productSubCategory)
                .param("productPrice", productPrice)
                .param("productDescription", productDescription)
                .param("productFor", productFor)
                .multiPart("productImage", new File(util.retrieveGlobalProperties("productImagePath")));

    }

    @Then("the API call will pass successfully with status code {int}")
    public void theAPICallWillPassSuccessfullyWithStatusCode(int statusCode) {
        assertEquals(response.getStatusCode(), statusCode);
    }

    @And("The {string} will be rendered as {string}")
    public void theWillBeRenderedAs(String key, String value) {
        productId = util.getJson_Path(response, "productId");
        String message = util.getJson_Path(response, key);
        assertEquals(message, value);
    }

    /**
     * SCENARIO 3 -> PLACING ORDER
     */

    @Given("sending the placing order payload with required properties")
    public void sendingThePlacingOrderPayloadWithRequiredProperties() throws IOException {
        PrintStream stream = new PrintStream(new FileOutputStream("PlacingOrderEcommerce.txt"));
        RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setRelaxedHTTPSValidation()
                .setBaseUri(util.retrieveGlobalProperties("baseUrl"))
                .addHeader("authorization", token).setContentType(ContentType.JSON)
                .addFilter(RequestLoggingFilter.logRequestTo(stream))
                .addFilter(ResponseLoggingFilter.logResponseTo(stream))
                .build();
        request = given().spec(createOrderBaseReq).body(placeOrder.placingOrderPayload(productId));
    }


    @And("the orderNumber is fetched")
    public void theOrderNumberIsFetched() {
        orderNumber = util.getJson_Path(response, "orders[0]");
        System.out.println(orderNumber);
    }

    /**
     * SCENARIO 4 -> GETTING ORDER DETAILS
     */

    @Given("Fetching the order details by sending orderNumber as query parameter")
    public void fetchingTheOrderDetailsBySendingOrderNumberAsQueryParameter() throws IOException {
        PrintStream stream = new PrintStream(new FileOutputStream("GettingOrderEcommerce.txt"));
        RequestSpecification viewOrderRequestSpec = new RequestSpecBuilder().setRelaxedHTTPSValidation()
                .setBaseUri(util.retrieveGlobalProperties("baseUrl"))
                .addFilter(RequestLoggingFilter.logRequestTo(stream))
                .addFilter(ResponseLoggingFilter.logResponseTo(stream))
                .addQueryParam("id", orderNumber)
                .addHeader("authorization", token).build();
        request = given().spec(viewOrderRequestSpec);
    }

    @And("The message will be displayed as {string}")
    public void theWillBeDisplayedAs(String value) {
        ViewOrders orders = response.as(ViewOrders.class);
        String message = orders.getMessage();
        assertEquals(message, value);
        productId = orders.getData().getProductOrderedId();
    }

    /**
     * SCENARIO 5 -> DELETING THE PRODUCT
     */

    @Given("Fetching the productId that to be deleted")
    public void fetchingTheProductIdThatToBeDeleted() throws IOException {
        PrintStream stream = new PrintStream(new FileOutputStream("DeletingProductEcommerce.txt"));
        RequestSpecification deleteProdBaseReq = new RequestSpecBuilder().setRelaxedHTTPSValidation()
                .setBaseUri(util.retrieveGlobalProperties("baseUrl"))
                .addFilter(RequestLoggingFilter.logRequestTo(stream))
                .addFilter(ResponseLoggingFilter.logResponseTo(stream))
                .addHeader("authorization", token).setContentType(ContentType.JSON)
                .build();
        request = given().spec(deleteProdBaseReq).pathParam("productId", productId);
    }

    @And("The {string} is {string}")
    public void theIs(String key, String value) {
        String message = util.getJson_Path(response, key);
        System.out.println(message);
        assertEquals(message, value);
    }
}
