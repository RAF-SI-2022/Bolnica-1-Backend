Feature: Osnovne operacije sa LabWorkOrder-ima

  Scenario: Pravljenje LabWorkOrdera iz uputa
    Given napravljeno 1 uputa za lbp "MOJLBP"
    When napravljen LabWorkOrder za te upute
    Then taj LabWorkOrder je sacuvan u bazi

  Scenario: Registrovanje LabWorkOrder-a da su u obradi prilikom dolaska pacijenta
    Given napravljeno 100 uputa za lbp "MOJLBP"
    Given napravljen LabWorkOrder za te upute
    When registrovanje pacijenta
    Then LabWorkOrder-i su azurirani da su u obradi

  Scenario: Verifikacija rezultata za LabWorkOrder kada rezultati nisu gotovi
    Given napravljeno 1 uputa za lbp "MOJLBP"
    Given napravljen LabWorkOrder za te upute
    When verifikujemo rezultate LabWorkOrder-a
    Then operacija javlja da nisu gotovi rezultati

  Scenario: Verifikacija rezultata za LabWorkOrder kada rezultati jesu gotovi
    Given napravljeno 1 uputa za lbp "MOJLBP"
    Given napravljen LabWorkOrder za te upute
    When uradimo analize i postavimo rezultate
    When verifikujemo rezultate LabWorkOrder-a
    Then operacija javlja da su gotovi rezultati
    Then LabWorkOrder je obelezen sa obradjen
    Then uput je obelezen sa realizovan

  Scenario: Postavljanje rezultata za analizu
    Given napravljeno 1 uputa za lbp "MOJLBP"
    Given napravljen LabWorkOrder za te upute
    When postavimo rezultat
    Then taj rezultat je uspesno postavljen

  Scenario: Filtriranje LabWorkOrdera po datumu i LBP
    Given napravljeno 100 uputa za lbp "MOJLBP"
    Given napravljen LabWorkOrder za te upute
    Given napravljeno 100 filtera za LabWorkOrder
    When dobavljeni rezultati filtriranja po datumu i LBP
    Then filtriranje po tim filterima daje zeljene rezultate

  Scenario: Filtriranje LabWorkOrdera po datumu, LBP i statusu
    Given napravljeno 100 uputa za lbp "MOJLBP"
    Given napravljen LabWorkOrder za te upute
    Given napravljeno 100 filtera za LabWorkOrder
    When dobavljeni rezultati filtriranja po datumu, LBP i statusu
    Then filtriranje po tim filterima daje zeljene rezultate

  Scenario: Dobavljanje obradjenih rezultata sa svim statusima
    Given prijavljeni smo kao ROLE_MED_BIOHEMICAR
    Given napravljeno 100 uputa za lbp "MOJLBP"
    Given napravljen LabWorkOrder za te upute
    When dobavimo rezultate oni su tacni

  Scenario: Brisanje LabWorkOrdera
    Given napravljeno 1 uputa za lbp "MOJLBP"
    Given napravljen LabWorkOrder za te upute
    When obrisemo LabWorkOrder
    Then on i njegove analize se vise ne nalaze u bazi

  Scenario: Azuriranje statusa LabWorkOrdera
    Given napravljeno 1 uputa za lbp "MOJLBP"
    Given napravljen LabWorkOrder za te upute
    When azuriramo status LabWorkOrdera
    Then je on azuriran

  Scenario: Dobavljanje LabWorkOrdera po ID
    Given napravljeno 1 uputa za lbp "MOJLBP"
    Given napravljen LabWorkOrder za te upute
    Then dobavljanje LabWorkOrdera po ID daje bas taj LabWorkOrder