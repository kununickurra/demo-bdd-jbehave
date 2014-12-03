package com.cgi.poc.jbehave.registration.context;

import com.cgi.service.customer.dto.CustomerType;
import org.springframework.stereotype.Component;

/**
 * Storage class for the customers that allows to store and retrieve context information between steps execution
 * Note that, as steps can be run using multiple threads (configurable in Jbehave)
 * it is necessary to use a ThreadLocal variable or any other thread-safe solution
 */

@Component
public class CustomerContext {

    private ThreadLocal<CustomerType> customerContext = new ThreadLocal<CustomerType>();
    private ThreadLocal<CustomerType> lastRegisteredContext = new ThreadLocal<CustomerType>();

    public void setCurrentCustomer(CustomerType customer) {
        lastRegisteredContext.set(customerContext.get());
        customerContext.set(customer);
    }

    public CustomerType getCurrentCustomer() {
        return customerContext.get();
    }

    public CustomerType getLastRegisteredContext() {
        return lastRegisteredContext.get();
    }

    public void cleanContext() {
        lastRegisteredContext.set(null);
        customerContext.set(null);
    }
}


