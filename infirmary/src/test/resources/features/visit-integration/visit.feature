Feature: Upravljanje posetama

  Pravimo servis koji upravlja posetama u stacionaru.
  Posetioci budu upisani od strane zaposlenog lica i
  zaposleni moze da proveri istoriju poseta po zadatim
  parametrima.


  Scenario: Belezimo novu posetu
    When kada zabelezimo informacije o novoj poseti
    Then te informacije su sacuvane u bazi


  Scenario: Filtriramo posete
    Given izabrali smo 10000 filtera 100 poseta zabelezeno
    Then  filterisanje po parametrima daje zeljene rezultate