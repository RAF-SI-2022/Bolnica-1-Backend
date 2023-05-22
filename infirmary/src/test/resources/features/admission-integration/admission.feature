Feature: Hendlovanje dolaska pacijenta u stacionar

#  Scenario: Hospitalizovanje pacijenta
#    When  otvorimo hospitalizaciju za pacijenta
#    Then  hospitalizacija tog pacijenta se nalazi u bazi i uput i termin su obelezeni kao zakazani i zauzeto je mesto u sobi

  Scenario: Zakazivanje termina hospitalizacije
    Given imamo uput u bazi sacuvan
    When  termin zakazan za taj uput
    Then  termin se nalazi u bazi

  Scenario: Postavljanje status termina
    When  promenimo status termina
    Then  u bazi se promenio status termina

  Scenario: Pretraga termina po ID uputa
    When  zatrazimo termin po ID uputa
    Then  dobili smo odgovarajuci termin sa tim ID uputa

  Scenario: Pretraga termina po filteru
    When  ubacimo 100 termina i probamo 100 filtera
    Then  dobicemo tacan rezultat za svaki filter termina

  Scenario: Pretraga uputa po filteru
    When  ubacimo 100 uputa i probamo 100 filtera
    Then  dobicema tacan rezultat za svaki filter uputa