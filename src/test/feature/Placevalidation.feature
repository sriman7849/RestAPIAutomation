Feature: Validating Place API's

 @AddPlace
  Scenario Outline: ADD Place API
    Given Add Place Payload with "<name>" "<address>"
    When user calls "ADD_PLACE_API" with "POST" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And verify place_Id created maps to "<name>" using "GET_PLACE_API"
    Examples:

      | name   | address  |
      | Sriman | Hyderabad|
      #| Swetha | Chicago  |

  @DeletePlace
  Scenario: Verify if Delete Place functionality is working
    Given DeletePlace Payload
    When user calls "DELETE_PLACE_API" with "POST" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"



