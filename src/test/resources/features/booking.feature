Feature: Search in Booking

  Scenario: Looking for 'Akra Kemer' hotel
    Given booking search page is opened
    When user searches for "Akra Kemer"
    Then "Akra Kemer - Ultra All Inclusive" hotel is show
    And "Akra Kemer" hotel raiting is "9,1"

  Scenario Outline: Looking hotels
    Given booking search page is opened
    When user searches for "<hotel>"
    Then "<expectedResult>" hotel is show
    Examples:
    | hotel | expectedResult |
    | Akra Kemer | Akra Kemer - Ultra All Inclusive |
    | Meraki     | Meraki Resort - Adults Only |