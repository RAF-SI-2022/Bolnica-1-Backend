Feature: Rad sa kartonom pacijenta

  Scenario: Dobavljanje kartona pacijenta
    When  dobavljen karton pacijenta sa LBP "P0002"
    Then  dobavljen karton je tacan koji smo trazili

  Scenario: Upisivanje istorije pregleda u karton
    When napravljen i upisan pregled za pacijenta sa LBP "P0002"
    When dobavljen karton pacijenta sa LBP "P0002"
    Then upisani pregled se nalazi u kartonu