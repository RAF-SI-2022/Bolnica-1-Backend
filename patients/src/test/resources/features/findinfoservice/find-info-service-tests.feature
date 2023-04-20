Feature: Operacije nad find info service

  Scenario: Dohvatanje GeneralMedicalData po LBP
    When Kada se prosledi lbp preko koga dohvatamo osnovne medicinske podatke
    Then Odgovor treba da sadrzi odgovarajuci osnovni medicinski podaci


  Scenario: Dohvatanje liste operacije koje odgovaraju LBP
    When Kada se prosledi lbp preko koga dohvatamo operaciju
    Then Odgovor treba da sadrzi odgovarajuce operacije


  Scenario: Dohvatanje liste MedicalHistory po LBP
    When Kada se prosledi lbp preko koga dohvatamo istoriju
    Then Odgovor treba da sadrzi odgovarajucu istoriju


  Scenario: Dohvatanje liste ExaminationHistory po LBP
    When Kada se prosledi lbp preko koga dohvatamo istoriju pregleda
    Then Odgovor treba da sadrzi odgovarajucu istoriju pregleda

  Scenario: Dohvatanje celog MedicalRecord po LBP
    When Kada se prosledi lbp preko koga dohvatamo zdravstveni karton
    Then Odgovor treba da sadrzi odgovarajuci zdravstveni karton


  Scenario: Dohvatanje liste MedicalHistory po LBP sa paginacijom
    When Kada se prosledi lbp i broj strane preko koga dohvatamo istoriju
    Then Odgovor treba da sadrzi odgovarajucu istoriju sa te strane



  Scenario: Dohvatanje liste MedicalHistory po LBP i kodu dijagnoze sa paginacijom
    When Kada se prosledi lbp, kod dijagnoze i broj strane preko koga dohvatamo istoriju
    Then Odgovor treba da sadrzi odgovarajucu medicinsku istoriju sa te strane


  Scenario: Dohvatanje liste MedicalHistory po LBP i pocetnim i krajnjim datumom sa paginacijom
    When Kada se prosledi lbp, pocetnim i krajnjim datumom i broj strane preko koga dohvatamo istoriju
    Then Odgovor treba da sadrzi odgovarajucu medicinsku istoriju sa te strane i prosledjenim datumom