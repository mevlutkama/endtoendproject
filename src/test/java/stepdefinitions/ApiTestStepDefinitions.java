package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class ApiTestStepDefinitions {

    Response response;
    @Given("user sends GET request to {string}")
    public void user_sends_get_request_to(String endPoint) {

        response = given().when().get(endPoint);
        response.prettyPrint();

    }
    @Then("HTTP status code should be {int}")
    public void http_status_code_should_be(Integer statusCode) {

        assertEquals(200,response.getStatusCode());
    }
}
