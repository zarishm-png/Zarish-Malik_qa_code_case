Feature: Justlife Dubai login
  As a visitor of the justlife Dubai website
  I want to log in using my mobile number and a one-time password
  So that I can access my account

  Scenario: Log in successfully with a valid mobile number and OTP
    Given the user is on the Justlife Dubai homepage
    When the user clicks the login button
    Then the login page should be displayed
    When the user enters their mobile number
    And the user requests an OTP
    Then the OTP screen should be displayed
    When the user enters the OTP
    And the user submits the OTP
    Then the user should be logged in successfully
