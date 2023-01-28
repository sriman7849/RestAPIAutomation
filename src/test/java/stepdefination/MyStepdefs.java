package stepdefination;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import API_Resources.APIResources;
import API_Resources.TestDataBind;
import API_Resources.util;

import java.io.IOException;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;

public class MyStepdefs extends util {

    RequestSpecification request_spec;
    public static Response response;
    TestDataBind test = new TestDataBind();
    public static String placeid ;
    public static String name;
    public static String token;

    @Given("Add Place Payload with {string} {string}")
    public void add_Place_Payload_with(String name, String address) throws IOException {
        //request spec
        request_spec = given().spec(requestSpecificationForPlaceAPI()).body(test.addPlacePayLoad(name, address));
    }

    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String methodName) {
        System.out.println(APIResources.valueOf(resource).getResource() + "->" + methodName);
        if (methodName.equalsIgnoreCase("POST"))
            response = request_spec.when().post(APIResources.valueOf(resource).getResource());
        else if (methodName.equalsIgnoreCase("GET"))
            response = request_spec.when().get(APIResources.valueOf(resource).getResource());

    }

    @Then("the API call got success with status code {int}")
    public void the_api_call_got_success_with_status_code(int statusCode) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(response.getStatusCode(), statusCode);
    }

  @Override
    public RequestSpecification requestSpecificationForPlaceAPI() throws IOException {
        return super.requestSpecificationForPlaceAPI();
    }


    @And("{string} in response body is {string}")
    public void inResponseBodyIs(String key, String value) {
        assertEquals(util.getJson_Path(response, key), value);
    }

    @And("verify place_Id created maps to {string} using {string}")
    public void verifyPlace_IdCreatedMapsToUsing(String expectedName, String resourceName) throws IOException {
        //ADD place response in which we pass json data (name,address e.t.c) then we get response in form of place id.
        placeid = util.getJson_Path(response, "place_id");
        //pass query parameter
        request_spec = given().spec(requestSpecificationForPlaceAPI()).queryParam("place_id", placeid);
        // Now we are passing get request to fetch stored data in json form using place id
        user_calls_with_http_request(resourceName, "GET");
       /* String s = response.asString();
        JsonPath js = new JsonPath(s);
        name=js.get("name").toString();*/
        name = util.getJson_Path(response, "name");
        assertEquals(expectedName, name);
    }


    @Given("DeletePlace Payload")
    public void deleteplacePayload() throws IOException {

        request_spec = given().spec(requestSpecificationForPlaceAPI()).body(test.deletePlacePayload(placeid));
    }

}



