Feature: Osnovne operacije sa bolnickim sobama

  Scenario: Dodavanje bolnicke sobe
    When bolnicka soba napravljena
    Then bolnicka soba se nalazi u bazi bolnickih soba


  Scenario: Brisanje bolnicke sobe
    When bolnicka soba napravljena
    When ista ta bolnicka soba obrisana
    Then te bolnicke sobe nema u bazi bolnickih soba

  Scenario: Pronalazak bolnicke sobe po ID sobe
    When bolnicka soba napravljena
    Then pretraga po njenom ID pronalazi tu sobu

  Scenario: Pronalazak bolnickih soba po ID departmana
    When 100 bolnickih soba napravljeno i 100 ID departmana izabrano
    Then filtriranje po svakom od izabranih ID departmana daje korektne bolnicke sobe