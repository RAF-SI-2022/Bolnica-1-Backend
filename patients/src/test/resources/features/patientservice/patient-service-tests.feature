Feature: Operacije nad pregledima pacijenata

  Scenario: Kreiranje novog rasporeda pregleda
    When Kada se kreira novi raspored
    Then Taj raspored je sacuvan u bazi

  Scenario: Brisanje rasporeda iz baze
    When Kada se raspored sa ID 1 izbrise iz baze
    Then Raspored sa ID 1 ne postoji u bazi

  Scenario: Azuriranje statusa dolaska pacijenta
    When Kada se prosledi ID dolaska pacijenta 2 i novi status "ZAVRSENO"
    Then Status dolaska pacijenta sa ID 2 ce biti "ZAVRSENO" u bazi
