Registration story.

Meta:
@Feature Customer registration

Narrative:
As an visitor
I want to register using my mobile device.
In order to access the private services

Scenario: Successful registration

Meta:
@Type Sunny

Given a visitor that is not registered
When the visitor registers to the service with its details
Then the new customer is successfully registered

Scenario: A customer already registered tries to register again with the same national number.

Meta:
@Type Rainy

Given a customer already registered to the system
When the visitor registers again to the service with its details
Then the visitor should receive an error message A consumer with the national number xxxx already exists

Scenario: A mior visitor tries to register

Meta:
@Type Rainy

Given a visitor that is not registered
And the visitor is minor
When the visitor registers to the service with its details
Then The visitor should not be registered
And the visitor should receive an error message You are too young to use this service!