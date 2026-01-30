Feature: Device Handover Management
  As a system administrator
  I want to manage device handovers
  So that I can track which devices are assigned to which users

  Scenario: Successfully create and confirm a device handover
    Given a department exists with name "IT Department"
    And a user exists with username "john.doe" in department "IT Department"
    And a device exists with serial number "SN-12345"
    When I create a handover protocol for device "SN-12345" to user "john.doe"
    And I confirm the handover protocol for device "SN-12345"
    Then user "john.doe" should have device "SN-12345" in their device list

  Scenario: Assign multiple devices to one user
    Given a department exists with name "Engineering"
    And a user exists with username "alice.smith" in department "Engineering"
    And a device exists with serial number "LAPTOP-001"
    And a device exists with serial number "MOUSE-002"
    When I create a handover protocol for device "LAPTOP-001" to user "alice.smith"
    And I confirm the handover protocol for device "LAPTOP-001"
    And I create a handover protocol for device "MOUSE-002" to user "alice.smith"
    And I confirm the handover protocol for device "MOUSE-002"
    Then user "alice.smith" should have 2 devices

  Scenario: Multiple users in different departments
    Given a department exists with name "Sales"
    And a department exists with name "Marketing"
    And a user exists with username "bob.jones" in department "Sales"
    And a user exists with username "carol.white" in department "Marketing"
    And a device exists with serial number "PHONE-001"
    And a device exists with serial number "TABLET-002"
    When I create a handover protocol for device "PHONE-001" to user "bob.jones"
    And I confirm the handover protocol for device "PHONE-001"
    And I create a handover protocol for device "TABLET-002" to user "carol.white"
    And I confirm the handover protocol for device "TABLET-002"
    Then user "bob.jones" should have device "PHONE-001" in their device list
    And user "carol.white" should have device "TABLET-002" in their device list

#  Scenario: Create handover protocol without confirmation
#    Given a department exists with name "HR"
#    And a user exists with username "dave.brown" in department "HR"
#    And a device exists with serial number "LAPTOP-003"
#    When I create a handover protocol for device "LAPTOP-003" to user "dave.brown"
#    Then the handover protocol should exist but not be confirmed


