Feature: Dohvatanje odeljenja i ustanova

  Scenario: Odeljenje na kome radi zaposljeni
    Given 10 zaposljenih
    Then  vidimo na kom odeljenju ko radi

  Scenario: Ustanova sa odeljenjima
    Given 5 zdravstvenih ustanova
    Then vidimo koja sve odeljenja ima

  Scenario: Doktori na odeljenju
    Given Odeljenja sa po 10 doktora
    Then vidimo koji doktori rade na kom odeljenju

  Scenario: Sve ustanove koje imaju odeljenje
    Given odeljenja cije ustanove pretrazujemo
    Then vidimo koje zdravstvene ustanove imaju odeljenje sa odredjenim nazivom
