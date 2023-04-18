Feature: Osnovne operacije sa uputima

  Scenario: Promena statusa uputa
    Given imamo uput u bazi
    When  promenimo status uputa
    Then  promena je azurirana u bazi