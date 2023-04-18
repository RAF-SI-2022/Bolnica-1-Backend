Feature: Upisivanje i slanje rezultata analiza

  Scenario: Azuriramo rezultat u LabWorkOrder
    Given imamo otvoren uput zajedno sa LabWorkOrderom i analizama
    Then azuriranje rezultata u za neki LabWorkOrder ce biti izvrseno

  Scenario: Slanje rezultata analiza LabWorkOrdera na pacijent servis
    Given otvoren uput i LabWorkOrder sa upisanim rezultatima
    When posaljemo rezultat u pacijent servis
    Then rezultati se nalaze na pacijent servisu