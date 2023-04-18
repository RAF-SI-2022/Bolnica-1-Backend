Feature: Osnovne operacije sa parametrima

  U ovim testovima podrzumevamo da se govori samo o
  onom parametru koji smo u testu kreirali

  Scenario: Kreiranje parametra
    When kreiramo parametar
    Then on se nalazi u bazi

  Scenario: Azuriranje parametra
    When kreiramo parametar
    When azuriramo parametar
    Then parametar je u bazi azuriran

  Scenario: Brisanje parametra
    When kreiramo parametar
    When obrisemo parametar
    Then parametar se ne nalazi u bazi

  Scenario: Dobavljanje parametra
    When kreiramo parametar
    Then dobavljanje parametar nam daje odgovarajuci parametar