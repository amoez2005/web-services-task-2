package edu.example.client;

import java.net.InetSocketAddress;
import java.net.URI;
import com.sun.net.httpserver.HttpServer;
import edu.example.client.generated.Student;
import edu.example.client.generated.StudentNotFoundException;
import edu.example.service.StudentServiceImpl;
import jakarta.xml.ws.Endpoint;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudentClientTest {
    @Test
    void generatedClientReadsPrimitiveFieldsNestedListsAndTypedFaultsOverHttp() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        Endpoint endpoint = Endpoint.create(new StudentServiceImpl());
        endpoint.publish(server.createContext("/students"));
        server.start();
        String address = "http://127.0.0.1:" + server.getAddress().getPort() + "/students";
        try {
            StudentClient client = new StudentClient(URI.create(address + "?wsdl").toURL(), address);
            assertEquals(2, client.getStudents().size());
            Student student = client.getStudent(1);
            assertAll(() -> assertEquals("Alice Morgan", student.getName()),
                    () -> assertEquals(20, student.getAge()),
                    () -> assertEquals(3.8f, student.getGpa()),
                    () -> assertTrue(student.isActive()),
                    () -> assertEquals(65, student.getGroupCode()),
                    () -> assertEquals(2, student.getSubjects().getSubject().size()),
                    () -> assertEquals("Web Services", student.getSubjects().getSubject().get(0).getName()));
            StudentNotFoundException fault = assertThrows(StudentNotFoundException.class,
                    () -> client.getStudent(999));
            assertEquals("NOT_FOUND", fault.getFaultInfo().getCode());
            assertEquals(999, fault.getFaultInfo().getStudentId());
            StudentClient.main(new String[]{address});
        } finally {
            endpoint.stop();
            server.stop(0);
        }
    }
}
