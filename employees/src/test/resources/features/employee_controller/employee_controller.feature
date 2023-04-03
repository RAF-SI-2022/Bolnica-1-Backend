Feature: Kontroler za mikroservis za zaposlene

  Scenario: Pravimo novog zaposlenog preko API-a
    Given logovali smo se na aplikaciju kao administrator
    When pravimo novog zaposlenog
    Then proveravamo da li je novi zaposleni upisan u bazu podataka


