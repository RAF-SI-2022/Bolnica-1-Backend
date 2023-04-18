Feature: Osnovne operacije sa AnalysisParameter

  Scenario: Kreiranje AnalysisParameter
    When kreirano 1 AnalysisParameter
    Then nalazi se u bazi

  Scenario: Azuriranje AnalysisParameter
    When kreirano 1 AnalysisParameter
    Then azuriranje menja i u bazi

  Scenario: Brisanje AnalysisParameter
    When kreirano 1 AnalysisParameter
    When obrisan taj AnalysisParameter
    Then ne nalazi se u bazi

  Scenario: Dohvatanje AnalysisParameter po ID
    When kreirano 1 AnalysisParameter
    Then dohvatanje po njegovom ID daje njega

  Scenario: Dohvatanje Parameter za zadati Analysis
    When kreirano 20 AnalysisParameter
    Then dohvatanje Parameter po Analysis je ispravno