Feature: Create New Task
  Scenario: The user can add new task
    Given Click Add new Task
    And Enter TaskName
    And Enter TaskDesc
    When Click Save
    Then Task added successfully