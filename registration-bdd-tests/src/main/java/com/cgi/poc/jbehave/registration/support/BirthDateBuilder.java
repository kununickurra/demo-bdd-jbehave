package com.cgi.poc.jbehave.registration.support;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Birth Date builder class.
 */
public class BirthDateBuilder {

    private GregorianCalendar calendar;

    public BirthDateBuilder(int requiredBirthDate) {
        calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.YEAR, -requiredBirthDate);
    }

    public BirthDateBuilder yesterday() {
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return this;
    }

    public XMLGregorianCalendar build() {
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
}
