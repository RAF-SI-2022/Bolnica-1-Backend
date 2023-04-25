Feature: Osnovne operacije sa zaposlenima

  Scenario: Kreiranje zaposlenog
    Given napravljen 1 zaposleni
    Then on se nalazi u bazi
    Then njegove permisije su upisane u bazu

  Scenario: Pronalazak zaposlenog
    Given napravljen 1 zaposleni
    Then dobavljanje njega po njegovom lbz funkcionise

  Scenario: Brisanje zaposlenog
    Given napravljen 1 zaposleni
    When taj zaposleni obrisan
    Then taj zaposleni se ne nalazi u bazi

