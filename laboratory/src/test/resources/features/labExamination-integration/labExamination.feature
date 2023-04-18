Feature: Osnovne funkcije sa pregledima u laboratoriji

  Scenario: Kreiranje zakazanog laboratorijskog pregleda
    When kreiranih 1 laboratorijskih pregleda
    Then on se nalazi u bazi

  Scenario: Promena status zakazanog laboratorijskog pregleda
    When kreiranih 1 laboratorijskih pregleda
    When promenjen status laboratorijskog pregleda
    Then status pregleda je promenjen i u bazi

  Scenario: Pronalazenje zakazanih laboratorijskih pregleda za dati dan
    When kreiranih 100 laboratorijskih pregleda
    Then pronalazenje za odredjeni datum daje tacne rezultate

  Scenario: Pronalazenje zakazanih laboratorijskih pregleda
    When kreiranih 100 laboratorijskih pregleda
    Then pronalazenje daje tacne rezultate

  Scenario: Pronalazenje zakazanih laboratorijskih pregleda za dati lbp i raspon datuma
    When kreiranih 100 laboratorijskih pregleda
    When kreiranih 100 filtera za laboratorijski pregled
    Then pronalazenje za odredjeni lbp i raspon datuma daje tacne rezultate