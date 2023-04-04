Feature: CRUD operaciej nad pacijentima
  Scenario: Registacija novog pacijenta
    When Kada se napravi novi pacijent
    Then Taj pacijent je sacuvan u bazi

  Scenario: Brisanje pacijenta
    When Kada se prosledi "P0002" kao lbp
    Then Pacijentu sa lbp-op "P0002" ce se deleted flag postaviti na true

  Scenario: Azuriranje pacijentovih podataka
    When Kada se proslede novi licni podaci pacijenta sa lbp-om "P0002"
    Then Licni podaci pacijenta sa lbp-om "P0002" ce se promeniti u bazi