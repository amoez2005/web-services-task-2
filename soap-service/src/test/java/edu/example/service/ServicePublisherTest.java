package edu.example.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServicePublisherTest {
    @Test
    void servesWsdlAndStopsEndpoint() throws Exception {
        ServicePublisher publisher = new ServicePublisher("http://127.0.0.1:0/students",
                new StudentServiceImpl());
        try {
            assertTrue(publisher.isPublished());
            HttpResponse<String> response = HttpClient.newHttpClient().send(HttpRequest.newBuilder(
                    URI.create(publisher.getAddress() + "?wsdl")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertTrue(response.body().contains("StudentService"));
            assertTrue(response.body().contains("getStudents"));
        } finally {
            publisher.close();
        }
        assertFalse(publisher.isPublished());
    }
}
