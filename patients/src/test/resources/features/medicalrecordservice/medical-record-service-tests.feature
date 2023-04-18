Feature: operaciej nad medical records

  Scenario: Pretraga svih alergija
    When Kada pronadjemo sve alergije
    Then Odgovor treba da sadrzi listu svih alergija


  Scenario: Pretraga svih vakcina
    When Kada pronadjemo sve vakcine
    Then Odgovor treba da sadrzi listu svih vakcina




  Scenario: Pretraga svih dijagnoza
    When Kada pronadjemo sve dijagnoze
    Then Odgovor treba da sadrzi listu svih dijagnoza


  Scenario: Dodavanje alergije
    When Kada se doda nova alergija
    Then Ta alergija treba biti sacuvana u bazi



  Scenario: Dodavanje vakcine
    When Kada se doda nova vakcina
    Then Ta vakcina treba biti sacuvana u bazi


  Scenario: Dodavanje operacije
    When Kada se doda nova operacija
    Then Ta operacija treba biti sacuvana u bazi


  Scenario: Dodavanje osnovnih zdravstvenih podataka
    When Kada se dodaju osnovni zdravstveni podaci
    Then Ti podaci treba biti sacuvana u bazi