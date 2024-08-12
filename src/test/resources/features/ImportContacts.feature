
Feature: Import contacts from link to outlook
  I want to import contacts from LINK to OUTLOOK

  @tag1
  Scenario: Importing contacts to outlook in all possible ways
    Given Dev Link URL
    When User login into dev link with correct credentials
    When hovers the mouse on any profile card to import contact
    When clicks on Import option in user profile card
    When Imports contacts bulkly from UpFront page
    Then User quits browser and generates test report
    Then Email the test evidence for RFC approvsl



