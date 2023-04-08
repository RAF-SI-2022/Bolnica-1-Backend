Feature: Verify work order when results not entered

  Scenario: Verify work order when results not entered
    Given a lab work order with id "1"
    When the work order is verified
    Then a CantVerifyLabWorkOrderException is thrown