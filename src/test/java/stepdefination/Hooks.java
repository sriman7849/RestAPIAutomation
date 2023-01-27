package stepdefination;

import io.cucumber.java.Before;
import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        MyStepdefs myStepdefs = new MyStepdefs();
        if(MyStepdefs.placeid==null) {
            myStepdefs.add_Place_Payload_with("swetha", "chicago");
            myStepdefs.user_calls_with_http_request("ADD_PLACE_API", "POST");
            myStepdefs.verifyPlace_IdCreatedMapsToUsing("swetha","GET_PLACE_API");
        }
    }


}
