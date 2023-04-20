Feature: Pracenje stanja pacijenta

  Scenario: Belezenje stanja pacijenta
    When stanje zabelezeno
    Then stanje se nalazi u bazi

  Scenario: Filtriranje stanja pacijent
    Given ima 100 zabeleznih stanja za pacijenta i 10000 zeljenih filtera
    Then  filtriranje filtrira korektno