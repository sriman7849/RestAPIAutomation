Feature: Validating Ecommerce_API's

  @LoginForEcommerce
  Scenario Outline: Login validation for ecommerce site
    Given Body payload  with credentials "<userEmail>" and "<userPassword>"
    When user calls the resource "Login_API" with "POST" method
    Then the API call will get success with status code 200
    Examples:
      | userEmail | userPassword |
      |srimannaidu7849@gmail.com|Sriman@7849|

  @AddProduct
  Scenario Outline:Validating Addition of the product
    Given The authorization token is fetched with the data "<productName>", "<productCategory>", "<productSubCategory>", "<productPrice>","<productDescription>","<productFor>"
    When user calls the resource "ADD_PRODUCT_API" with "POST" method
    Then the API call will pass successfully with status code 201
    And The "message" will be rendered as "Product Added Successfully"
    Examples:
      | productName | productCategory | productSubCategory | productPrice | productDescription | productFor |
      | Laptop      | Electronics     |Computer devices    |90000         |Best Laptop         |everyone    |

  @PlacingOrder
  Scenario: Placing the order for product created
    Given sending the placing order payload with required properties
    When user calls the resource "PLACE_ORDER_API" with "POST" method
    Then the API call will pass successfully with status code 201
    And The "message" will be rendered as "Order Placed Successfully"
    And the orderNumber is fetched

  @FetchingOrderDetails
  Scenario: Fetching the order details
    Given Fetching the order details by sending orderNumber as query parameter
    When user calls the resource "GET_ORDER_API" with "GET" method
    Then the API call will pass successfully with status code 200
    And The message will be displayed as "Orders fetched for customer Successfully"

  @DeleteProduct
  Scenario: Deleting the product
    Given Fetching the productId that to be deleted
    When user calls the resource "DELETE_PRODUCT_API" with "DELETE" method
    Then the API call will pass successfully with status code 200
    And The "message" is "Product Deleted Successfully"



