package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utilities.JdbcUtils;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class DatabaseTestStepDefinitions {

    List<Object> createdByList;
    @Given("user connects to the database")
    public void user_connects_to_the_database() {
        JdbcUtils.connectToDatabase("medunna.com","medunna_db","medunna_user","medunna_pass_987");

    }

    @When("user sends the query to get the names of {string} column from {string} table")
    public void user_sends_the_query_to_get_the_names_of_column_from_table(String columnName, String tableName) {
        createdByList = JdbcUtils.getColumnList(columnName,tableName);
        // System.out.println("createdByList = " + createdByList);
        
    }
    @Then("assert that there are some rooms created by {string}")
    public void assert_that_there_are_some_rooms_created_by(String name) {

        assertTrue(createdByList.contains(name));

    }
    @Then("close the connection")
    public void close_the_connection() {

        JdbcUtils.closeConnectionAndStatement();

    }
}
