package com.cgi.poc.jbehave.registration.steps;

import com.cgi.poc.jbehave.commons.context.Fault;
import com.cgi.poc.jbehave.commons.context.FaultContext;
import com.cgi.poc.jbehave.registration.context.CustomerContext;
import com.cgi.poc.jbehave.registration.support.BirthDateBuilder;
import com.cgi.service.customer.CustomerService;
import com.cgi.service.customer.dto.CustomerType;
import com.cgi.service.customer.dto.GetCustomerByNationalNumberRequestDTO;
import com.cgi.service.customer.dto.GetCustomerByNationalNumberResponseDTO;
import com.cgi.service.customer.dto.RegisterCustomerRequestDTO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jbehave.core.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Step class for the Registration story
 */

@Component
public class RegistrationSteps {

    private static int MINIMAL_AGE = 18;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerContext customerContext;

    @Autowired
    private FaultContext faultContext;

    @BeforeScenario
    public void cleanContext() {
        // Clean the execution context between each scenarios
        customerContext.cleanContext();
    }

    @Given("a visitor that is not registered")
    public void givenAVisitorThatIsNotRegistered() {
        // Create a customer with a Random National Number (UUID to make sure it stays unique)
        // so that the test be repeatedly run against the same environment.
        CustomerType customerType = createNewCustomer(UUID.randomUUID().toString());
        // Set this customer in the current context as it will be reused in later steps.
        customerContext.setCurrentCustomer(customerType);
    }

    @Given("the visitor is minor")
    public void givenAMinorVisitor() {
        // TODO Solve edge case below.
        // As both the Jbehave test and the service rely on system date for the age check the test might fail
        // in case it is executed at midnight. Date might have switched between this step
        // and the next "When" step. No error message will be send and customer will be registered and test will fail.
        // Service is Fine but Jbehave need to be fixed
        customerContext.getCurrentCustomer().setBirthDate(new BirthDateBuilder(MINIMAL_AGE).yesterday().build());
    }

    @Given("a customer already registered to the system")
    @Composite(steps = {
            "Given a visitor that is not registered",
            "When the visitor registers to the service with its details"})
    public void givenAVisitorAlreadyRegisteredToTheSystem() {
        // Could have called steps manually.
    }

    @Given("the visitor enter a national number already registered")
    public void givenTheVisitorUsesANationalNumberThatAlreadyExists() {
        // Update current customer national number with the same national Number as the last registered customer
        // to generate the duplicate national number error.
        customerContext.getCurrentCustomer().setNationalNumber(customerContext.getLastRegisteredContext().getNationalNumber());
    }

    @When("the visitor registers to the service with its details")
    public void whenTheVisitorRegister() {
        // Get the current customer from the context.
        RegisterCustomerRequestDTO dto = new RegisterCustomerRequestDTO();
        dto.setCustomer(customerContext.getCurrentCustomer());
        try {
            // Call the customer-service web service to register the customer
            customerService.registerCustomer(dto);
        } catch (Exception fault) {
            // In case of error, populate the FaultContext so that it can be tested afterwards.
            faultContext.setCurrentFault(new Fault(fault));
        }
    }

    @Then("the new customer is successfully registered")
    public void thenTheNewCustomerIsSuccessfullyRegistered() {
        // Here we have to make sure that the customer present in the system and that the correct data has been stored.
        GetCustomerByNationalNumberRequestDTO request = new GetCustomerByNationalNumberRequestDTO();
        request.setNationalNumber(customerContext.getCurrentCustomer().getNationalNumber());
        // Call the customer-service web service to retrieve customer using its national number
        GetCustomerByNationalNumberResponseDTO response = customerService.getCustomerByNationalNumber(request);
        // Make sure that all fields have been persisted correctly according to what we have entered before
        CustomerType registeredCustomer = response.getCustomer();
        assertThat(registeredCustomer, is(notNullValue()));
        CustomerType contextCustomer = customerContext.getCurrentCustomer();
        assertThat("Incorrect customer returned by the system," + // A nice (generic) message might help in case of error
                        " expected is " + ToStringBuilder.reflectionToString(registeredCustomer) +
                        " actual is : " + ToStringBuilder.reflectionToString(registeredCustomer),
                EqualsBuilder.reflectionEquals(registeredCustomer, contextCustomer), is(true));
    }

    @Then("the visitor should not be registered")
    public void thenTheVisitorShouldNotBeRegistered() {
        GetCustomerByNationalNumberRequestDTO request = new GetCustomerByNationalNumberRequestDTO();
        request.setNationalNumber(customerContext.getCurrentCustomer().getNationalNumber());
        // Call the customer-service web service to retrieve customer using its national number
        GetCustomerByNationalNumberResponseDTO response = customerService.getCustomerByNationalNumber(request);
        assertThat(response.getCustomer(), is(nullValue()));
    }

    @Then("the visitor should receive an error message $message")
    public void thenTheCustomerShouldReceiveAnErrorMessage(@Named("message") String message) {
        assertThat(faultContext.getLastFault().getMessage(), is(message.replace("xxxx", customerContext.getCurrentCustomer().getNationalNumber())));
    }

    private CustomerType createNewCustomer(String nationalNumber) {
        // Create customer with the default values.
        CustomerType customerType = new CustomerType();
        customerType.setLastName("Last Name");
        customerType.setFirstName("First name");
        customerType.setNationalNumber(nationalNumber);
        customerType.setBirthDate(new BirthDateBuilder(MINIMAL_AGE).build());
        return customerType;
    }
}
