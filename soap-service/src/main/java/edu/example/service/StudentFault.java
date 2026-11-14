package edu.example.service;

/** Structured detail transported inside a SOAP fault. */
public class StudentFault {
    private String code;
    private int studentId;

    /** Creates an empty JAXB bean. */
    public StudentFault() {
    }

    /**
     * Creates structured missing-student fault details.
     * @param code machine-readable error code
     * @param studentId requested identifier */
    public StudentFault(String code, int studentId) {
        this.code = code;
        this.studentId = studentId;
    }

    /**
     * Returns machine-readable error code.
     * @return machine-readable error code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets machine-readable error code.
     * @param code machine-readable error code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Returns requested identifier.
     * @return requested identifier
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Sets requested identifier.
     * @param studentId requested identifier
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
