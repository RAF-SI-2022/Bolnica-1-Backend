Feature: CRUD operaciej nad medical examination
  Scenario: Dodavanje examination istorije
    When Kada se prosledi "P0002" kao lbp napravi se novi examination history
    Then Taj examination je sacuvan u bazi
