Feature: Verification of resume visibility change endpoints

  Background: User is signed in as admin
    Given User is authorized with token: "admin"


  Scenario: Start editing article
    Given "POST" request is sent to endpoint "/resume/" with no body