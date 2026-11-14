package edu.example.service;

import jakarta.xml.ws.WebFault;

/** Checked service exception mapped to an explicit WSDL fault. */
@WebFault(name = "StudentFault", targetNamespace = "https://example.edu/students")
public class StudentNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    private final StudentFault faultInfo;

    /**
     * Creates a checked exception carrying structured SOAP fault details.
     * @param message human-readable explanation
     * @param faultInfo structured error detail */
    public StudentNotFoundException(String message, StudentFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * Returns SOAP fault detail.
     * @return SOAP fault detail
     */
    public StudentFault getFaultInfo() {
        return faultInfo;
    }
}
