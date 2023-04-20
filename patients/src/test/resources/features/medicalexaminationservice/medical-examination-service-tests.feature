Feature: CRUD operaciej nad medical examination
  Scenario: Dodavanje examination istorije
    When Kada se prosledi odredjeni lbp napravi se novi examination history za tog pacijenta
    Then Taj examination je sacuvan u bazi


  Scenario: Dodavanje medical istorije
    When Kada se prosledi odredjeni lbp napravi se novi medical history za tog pacijenta
    Then Taj medical je sacuvan u bazi
