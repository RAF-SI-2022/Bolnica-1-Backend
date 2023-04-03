Feature: Servis za rad sa zakazanim pregledima

  U ovom servisu pravimo menjamo i gledamo zakazane preglede.

  Scenario: Izmena statusa pregleda
    When Kada se doda neki pregled
    Then Se taj izmenjen pregled nadje promeni i sacuva