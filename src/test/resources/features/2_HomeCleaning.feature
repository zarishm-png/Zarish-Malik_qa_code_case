Feature: Home Cleaning booking
  As a logged-in Justlife Dubai customer
  I want to book a Home Cleaning service
  So that I can reach the checkout page with my booking details

  Scenario: Book a Home Cleaning session through to checkout
    Given the user is on the Justlife Dubai homepage
    When the user selects the Home Cleaning service
    Then the Home Cleaning details page should be displayed
    When the user confirms the booking address
    And the user proceeds to the add-ons step
    And the user skips the add-ons step
    And the user confirms the booking frequency
    And the user proceeds to checkout
    Then the checkout page should be displayed
