Feature: Slanje i primanje uputa za stacionar

  Pravimo servis koji prima upute poslate stacionaru
  i salje upute za laboratoriju napravljene na stacionaru



  Scenario: Primanje uputa za stacionar
    When primljen uput za stacionar
    Then uput se nalazi u bazi uputa

#  Scenario: Slanje uputa na laboratoriju
#    When poslat uput na laboratoriju
#    Then taj uput se nalazi na laboratoriji