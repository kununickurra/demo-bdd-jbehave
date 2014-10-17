package com.cgi.poc.jbehave.commons.context;

import org.springframework.stereotype.Component;

/**
 * Storage class for the Faults that allows to store and retrieve context information between steps execution
 * Note that, as steps can be run using multiple threads (configurable in Jbehave)
 * it is necessary to use a ThreadLocal variable.
 */

@Component
public class FaultContext {

    private ThreadLocal<Fault> lastFault = new ThreadLocal<Fault>();

    public void setCurrentFault(Fault fault) {
        lastFault.set(fault);
    }

    public Fault getLastFault() {
        return lastFault.get();
    }

}
