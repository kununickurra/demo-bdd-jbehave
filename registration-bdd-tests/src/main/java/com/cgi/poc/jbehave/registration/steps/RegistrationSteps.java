package com.cgi.poc.jbehave.registration.steps;

import com.cgi.poc.jbehave.commons.context.Fault;
import com.cgi.poc.jbehave.commons.context.FaultContext;
import com.cgi.poc.jbehave.registration.context.CustomerContext;
import com.cgi.service.customer.CustomerService;
import com.cgi.service.customer.dto.CustomerType;
import com.cgi.service.customer.dto.GetCustomerByNissRequestDTO;
import com.cgi.service.customer.dto.GetCustomerByNissResponseDTO;
import com.cgi.service.customer.dto.RegisterCustomerRequestDTO;
import org.jbehave.core.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Step class for the Registration scenarios
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

    @Given("the visitor is minor")
    public void givenAMinorVisitor() {
        customerContext.getCurrentCustomer().setBirthDate(generateBirthDate(MINIMAL_AGE - 1));
    }

    @Given("a visitor that is not registered")
    public void givenAVisitorThatIsNotRegistered() {
        // Create a customer with a Random National Number (UUID to make sure it stays unique)
        // so that the test be repeatedly against the same environment.
        CustomerType customerType = createNewCustomer(UUID.randomUUID().toString());
        // Set this customer in the current context as it will be reused
        customerContext.setCurrentCustomer(customerType);
    }

    @Given("a customer already registered to the system")
    @Composite(steps = {
            "Given a visitor that is not registered",
            "When the visitor registers to the service with its details"})
    public void givenAVisitorAlreadyRegisteredToTheSystem() {
        // The last added customer should be retrieved so that create a new customer with the same national Number
        // to generate the duplicate national number error.
        String currentNationalNumber = customerContext.getCurrentCustomer().getNiss();
        CustomerType customerType = createNewCustomer(currentNationalNumber);
        customerContext.setCurrentCustomer(customerType);
    }

    @When("the visitor registers to the service with its details")
    @Alias("the visitor registers again to the service with its details")
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
        GetCustomerByNissRequestDTO request = new GetCustomerByNissRequestDTO();
        request.setNiss(customerContext.getCurrentCustomer().getNiss());
        GetCustomerByNissResponseDTO response = customerService.getCustomerByNiss(request);
        // Make sure that all fields have been persisted correctly according to what we have entered before
        // As our DTO does not have an equals method the fields must be tested one by one...
        CustomerType registeredCustomer = response.getCustomer();
        assertNotNull(registeredCustomer);
        CustomerType contextCustomer = customerContext.getCurrentCustomer();
        assertThat(registeredCustomer.getFirstName(), is(contextCustomer.getFirstName()));
        assertThat(registeredCustomer.getLastName(), is(contextCustomer.getLastName()));
        assertThat(registeredCustomer.getNiss(), is(contextCustomer.getNiss()));
        assertThat(registeredCustomer.getBirthDate(), is(contextCustomer.getBirthDate()));
    }

    @Then("The visitor should not be registered")
    public void thenTheCustomerShouldNotBeRegistered() {
        GetCustomerByNissRequestDTO request = new GetCustomerByNissRequestDTO();
        request.setNiss(customerContext.getCurrentCustomer().getNiss());
        GetCustomerByNissResponseDTO response = customerService.getCustomerByNiss(request);
        assertNull(response.getCustomer());
    }


    @Then("the visitor should receive an error message $message")
    public void thenTheCustomerShouldReceiveAnErrorMessage(@Named("message") String message) {
        assertThat(faultContext.getLastFault().getMessage(), is(message.replace("xxxx", customerContext.getCurrentCustomer().getNiss())));
    }

    private XMLGregorianCalendar generateBirthDate(int age) {
        GregorianCalendar requiredBirthDate = new GregorianCalendar();
        requiredBirthDate.set(Calendar.HOUR_OF_DAY, 0);
        requiredBirthDate.set(Calendar.MINUTE, 0);
        requiredBirthDate.set(Calendar.SECOND, 0);
        requiredBirthDate.set(Calendar.MILLISECOND, 0);
        requiredBirthDate.add(Calendar.YEAR, -age);
        return convertToXMLGregorianCalendar(requiredBirthDate);
    }

    private XMLGregorianCalendar convertToXMLGregorianCalendar(GregorianCalendar calendar) {
        try {
            XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            // Just copy the date as we are not interested in the time information.
            xmlGregorianCalendar.setDay(calendar.get(Calendar.DAY_OF_MONTH));
            xmlGregorianCalendar.setMonth(calendar.get(Calendar.MONTH) + 1);
            xmlGregorianCalendar.setYear(calendar.get(Calendar.YEAR));
            return xmlGregorianCalendar;
        } catch (DatatypeConfigurationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private CustomerType createNewCustomer(String nationalNumber) {
        // Create customer with teh default values.
        CustomerType customerType = new CustomerType();
        customerType.setLastName("Last Name");
        customerType.setFirstName("First name");
        customerType.setNiss(nationalNumber);
        customerType.setBirthDate(generateBirthDate(MINIMAL_AGE));
        return customerType;
    }
}
