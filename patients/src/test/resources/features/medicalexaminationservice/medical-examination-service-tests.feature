Feature: CRUD operaciej nad medical examination
  Scenario: Dodavanje examination istorije
    When Kada se prosledi "P0002" kao lbp napravi se novi examination history
    Then Taj examination je sacuvan u bazi


  Scenario: Dodavanje medical istorije
    When Kada se prosledi "P0002" kao lbp napravi se novi medical history
    Then Taj medical je sacuvan u bazi
