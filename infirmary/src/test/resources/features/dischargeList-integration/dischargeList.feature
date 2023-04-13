Feature: Osnovne operacije sa otpusnom listom

  Scenario: Pravljenje nove otpusne liste
    When napravili smo otpusnu listu i smestili smo je u bazu
    Then ona se nalazi u bazi otpusnih listi

  Scenario: Pronalazak otpusne liste po ID hospitalizacije
    When napravili smo otpusnu listu i smestili smo je u bazu
    Then  pretraga po njenom ID hospitalizacije je pronalazi