Feature: Dobavljenje hospitalizacija po parametrima

  #LEGENDA:
  #idDep -> ID departmana
  #idHR  -> ID bolnicke sobe
  #name  -> ime pacijenta
  #surname -> prezime pacijent
  #jmbg
  #lbp
  #startDate -> leva granica datuma za filtriranje
  #endDate -> desna granica datuma za filtriranje

  Scenario: Dobavljanje hospitalizcija po ID departmana
    When imamo 100 hospitalizacija i 1000 filteri su: idDep: 1 , idHr: 0 , name: 0 , surname: 0 , jmbg: 0 , lbp: 0 , startDate: 0 , endDate: 0
    Then isfiltrirane hospitalizacije su tacne

  Scenario: Dobavljanje hospitalizcija po ID bolnicke sobe
    When imamo 100 hospitalizacija i 1000 filteri su: idDep: 0 , idHr: 1 , name: 0 , surname: 0 , jmbg: 0 , lbp: 0 , startDate: 0 , endDate: 0
    Then isfiltrirane hospitalizacije su tacne

  Scenario: Dobavljanje hospitalizacije po svemu
    When imamo 100 hospitalizacija i 1000 filteri su: idDep: 1 , idHr: 1 , name: 1 , surname: 1 , jmbg: 1 , lbp: 1 , startDate: 1 , endDate: 1
    Then isfiltrirane hospitalizacije su tacne