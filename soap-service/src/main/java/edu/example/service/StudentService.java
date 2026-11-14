package edu.example.service;

import java.util.List;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

/** Document/literal wrapped SOAP contract. */
@WebService(name = "StudentPort", targetNamespace = "https://example.edu/students")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL,
        parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface StudentService {
    /**
     * Returns all students.
     * @return all students
     */
    @WebMethod
    @WebResult(name = "student")
    List<Student> getStudents();

    /**
     * Finds one student by identifier.
     * @param id requested identifier
     * @return requested student
     * @throws StudentNotFoundException when the identifier is not present */
    @WebMethod
    @WebResult(name = "student")
    Student getStudent(@WebParam(name = "id") int id) throws StudentNotFoundException;
}
