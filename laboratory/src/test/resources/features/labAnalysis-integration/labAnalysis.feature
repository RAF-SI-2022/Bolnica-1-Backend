Feature: Osnovne operacije sa laboratorijskim analizama

  Scenario: Kreiranje laboratorijske analize
    When kreirana laboratorijska analiza
    Then ona se nalazi u bazi

  Scenario: Azuriranje laboratorijske analiza
    When kreirana laboratorijska analiza
    Then analiza je azurirana i u bazi

  Scenario: Brisanje laboratorijske analize
    When kreirana laboratorijska analiza
    When obrisana laboratorijska analiza
    Then ta analiza se ne nalazi u bazi

  Scenario: Dohvatanje laboratorijske analize po ID
    When kreirana laboratorijska analiza
    Then dobavljanje analize to njenom ID daje tu analizu

  Scenario: Dohvatanje svih laboratorijskih analiza
    When kreirana laboratorijska analiza
    Then dobavljanje svih laboratorijskih analiza daje tu analizu i ostale vec postojece analize