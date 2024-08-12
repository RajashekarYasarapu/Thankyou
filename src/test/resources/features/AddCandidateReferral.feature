
@tag
Feature: Add Candidate Referral
  I want to add candidate referral in dev link

  @tag1
  Scenario: Candidate Referral in Dev Link
    Given User with devlink url and login credentials
    When User clicks on Candidate Refrral button in devlink
    Then User should fill in required fields on the form
    Then User uploads candidats RESUME
    Then User clicks on submit button
    And establish a connection with SSMS DB
    Then User should be able to see email template generated for this referral.


  @tag2
  Scenario: Assign this candidate referral to Hiring Manager and AssignedTo Manager
    Given User with devlink url aloong with login credentials
    When User goes to My referral page of candidate referrals
    Then User clicks on Assign button of respective referral
    Then User assigns referral to HM and HR
    Then User clicks on submit
    And User gets respective HR and HM of this referral