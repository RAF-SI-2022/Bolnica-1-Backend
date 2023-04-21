Feature: Rad sa kartonom paccijenta

  Scenario: Dolazak pacijenta na recepciju
    When dodamo novog pacijenta u sistem
    Then mozemo pronaci pacijenta na osnovu jedinstvenog lbp-a

  Scenario: Dodavanje krvne grupe i rh faktora u karton pacijenta
    When nadjemo karton za pacijenta
    Then upisujemo krvnu grupu i rh faktor
    Then mozemo naci podatke o krvnoj grupi i faktoru

  Scenario: Dodavanje operacije u karton pacijenta
    When nadjemo karton za pacijenta
    Then dodajemo operaciju
    Then mozemo videti da li se dodala operacija za pacijenta

  Scenario: Dodavanje vakcine u karton pacijenta
    When nadjemo karton za pacijenta
    Then dodajemo novu vakcinu za pacijenta
    Then mozemo videti da li se dodala vakcina za pacijenta

  Scenario: Dodavanje alergije u karton pacijenta
    When nadjemo karton za pacijenta
    Then dodajemo novu alergiju za pacijenta
    Then mozemo videti da li se dodala alergija za pacijenta

