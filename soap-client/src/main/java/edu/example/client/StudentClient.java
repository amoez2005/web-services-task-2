package edu.example.client;

import java.net.URI;
import java.net.URL;
import java.util.List;
import edu.example.client.generated.Student;
import edu.example.client.generated.StudentPort;
import edu.example.client.generated.StudentService;
import edu.example.client.generated.StudentNotFoundException;
import jakarta.xml.ws.BindingProvider;

/** Thin application client built entirely on wsimport-generated transport types. */
public final class StudentClient {
    private final StudentPort port;

    /**
     * Creates a client using the live WSDL and an explicit endpoint address.
     * @param wsdl published WSDL URL
     * @param address SOAP endpoint address */
    public StudentClient(URL wsdl, String address) {
        port = new StudentService(wsdl).getStudentPort();
        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
    }

    /**
     * Returns all students returned by the server.
     * @return all students returned by the server
     */
    public List<Student> getStudents() {
        return port.getStudents();
    }

    /**
     * Looks up one student through the generated SOAP port.
     * @param id requested identifier
     * @return requested student
     * @throws StudentNotFoundException if no student matches */
    public Student getStudent(int id) throws StudentNotFoundException {
        return port.getStudent(id);
    }

    /**
     * Runs a demonstration against the published SOAP service.
     * @param args optional SOAP endpoint address
     * @throws Exception if the endpoint cannot be reached or returns a fault */
    public static void main(String[] args) throws Exception {
        String address = args.length == 0 ? "http://localhost:8080/students" : args[0];
        StudentClient client = new StudentClient(URI.create(address + "?wsdl").toURL(), address);
        for (Student student : client.getStudents()) {
            System.out.printf("%d: %s, GPA %.1f, %d subjects%n", student.getId(), student.getName(),
                    student.getGpa(), student.getSubjects().getSubject().size());
        }
        System.out.println("Lookup 1: " + client.getStudent(1).getName());
    }
}
