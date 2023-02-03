@UITest @E2ETest
Feature:Google_Test

  Scenario:TC01_Google_Search
    Given user goes to "https://google.com"
    Then user closes the application