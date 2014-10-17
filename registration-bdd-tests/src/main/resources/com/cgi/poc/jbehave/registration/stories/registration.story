Registration story.

Meta:
@Feature Registration

Narrative:
In order to register a to the service
As a new customer
I want to register using my mobile device.

Scenario: Successful registration: A new customer send his details in order to register to the service.

Meta:
@Type Sunny

Given a new visitor that is not registered
And the new visitor is 18 years old.
When the visitor register to the service with its details
Then The new customer is successfully registered

Scenario: A customer already registered tries to register again with the same national number.

Meta:
@Type Rainy

Given a customer already registered to the system
And the new visitor is 18 years old.
When the visitor register to the service with its details
Then the visitor should receive an error message A consumer with the national number xxxx already exists

Scenario: A customer that does not have the minimal age required to use the service tries to register

Meta:
@Type Rainy

Given a new visitor that is not registered
And the new visitor is 17 years old.
When the visitor register to the service with its details
Then The visitor should not be registered
And the visitor should receive an error message You are too young to use this service!