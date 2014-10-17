package com.cgi.poc.jbehave.registration.context;

import com.cgi.service.customer.dto.CustomerType;
import org.springframework.stereotype.Component;

/**
 * Storage class for the customer that allows to store and retrieve context information between steps execution
 * Note that, as steps can be run using multiple threads (configurable in Jbehave)
 * it is necessary to use a ThreadLocal variable.
 */

@Component
public class CustomerContext {

    private ThreadLocal<CustomerType> customerContext = new ThreadLocal<CustomerType>();

    public void setCurrentCustomer(CustomerType customer) {
        customerContext.set(customer);
    }

    public CustomerType getCurrentCustomer() {
        return customerContext.get();
    }
}


