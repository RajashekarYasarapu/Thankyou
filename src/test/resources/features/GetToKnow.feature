Feature: Get To Know feature
  I want use get to know feature in dev link

  @tag1
  Scenario: Add Get to know for a new user in Link
    Given User with Dev Link application url
    Then User login in link app with valid credentials
    Then User clicks on any user profile card
    Then User adds gtk interaction with new user
    Then User clicks on Add button
    Then Establish connection with devsql server
    And User should be able to see email template genarated for this interaction
  
  @tag2
  Scenario: Complete the interaction with assigned user
  Given User goes to Get to Know page
  Then User should impersonate as WhoShouldKnow
  Then Go to his Get To Know page
  
  @tag3
  Scenario: Create a PowerPoint presentation which contains test evidence of above test cases for RFC approval
  Given Automate the process of creating ppt
  Then Mail to BizappsHyd team.