@ApiTest @E2ETest
Feature: Api_Feature

  Scenario:TC02_Get_Request
    Given user sends GET request to "https://restful-booker.herokuapp.com/booking/10"
    Then HTTP status code should be 200