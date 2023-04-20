Feature: Operacije sa uputima

  Scenario: Kreiranje uputa
    When kreiramo novi uput
    Then on se nalazi sacuvan u bazi
    Then sve njegove analize su korektno sacuvane u bazi

  Scenario: Azuriranje uputa
    When napravljena nova verzija uputa
    Then uput se nalazi u bazi
    Then svi prethodne analize za taj uput obrisane i dodate nove

  Scenario: Brisanje uputa
    When obrisan uput sa ID 2 i LBZ "E0003"
    Then on i njegov labWorkOrder se ne nalaze vise u bazi

  Scenario: Pronalazak nerealizovanih uputa pacijenta po LBP i LBZ doktora(paginacija)
    Given dodali smo 100 uputa
    Then pretrazili smo po lbp i lbz(paginacija) uspesno

  Scenario: Dobavljanje rezultata uputa za dati ID uputa
    Given dodali smo 100 uputa
    Then dobavili smo rezultate korektno za uput sa ID 10

  Scenario: Pronalazak nerealizovanih uputa pacijenta po LBP i LBZ doktora
    Given dodali smo 100 uputa
    Then pretrazili smo po lbp i lbz uspesno

  Scenario: Pronalazak nerealizovanih uputa pacijenta po LBP
    Given dodali smo 100 uputa
    Then pretrazili smo po lbp uspesno

  Scenario: Pronalazak svih pacijenata zajedno sa njihovim uputima koji su nerealizovani
    Given dodali smo 100 uputa
    Then pronasli smo sve trazene rezultate