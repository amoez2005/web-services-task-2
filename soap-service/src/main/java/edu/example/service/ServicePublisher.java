package edu.example.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import com.sun.net.httpserver.HttpServer;
import jakarta.xml.ws.Endpoint;

/** Owns the endpoint lifecycle and provides a standalone application entry point. */
public final class ServicePublisher implements AutoCloseable {
    private final Endpoint endpoint;
    private final HttpServer server;
    private final String address;

    /**
     * Publishes an HTTP SOAP endpoint and owns its listener.
     * @param address HTTP endpoint address
     * @param service service implementation
     * @throws IOException if the HTTP listener cannot be started */
    public ServicePublisher(String address, StudentService service) throws IOException {
        URI uri = URI.create(address);
        if (!"http".equals(uri.getScheme()) || uri.getHost() == null || uri.getPath().isEmpty()
                || uri.getQuery() != null || uri.getFragment() != null) {
            throw new IllegalArgumentException("An HTTP address with a host and path is required");
        }
        int port = uri.getPort() < 0 ? 80 : uri.getPort();
        server = HttpServer.create(new InetSocketAddress(uri.getHost(), port), 0);
        endpoint = Endpoint.create(service);
        try {
            endpoint.publish(server.createContext(uri.getPath()));
            server.start();
        } catch (RuntimeException exception) {
            server.start();
            server.stop(0);
            throw exception;
        }
        this.address = "http://" + uri.getHost() + ":" + server.getAddress().getPort() + uri.getPath();
    }

    /**
     * Returns actual published address, including the assigned port when zero was requested.
     * @return actual published address, including the assigned port when zero was requested
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns whether the endpoint is published.
     * @return whether the endpoint is published
     */
    public boolean isPublished() {
        return endpoint.isPublished();
    }

    @Override
    public void close() {
        endpoint.stop();
        server.stop(0);
    }

    /**
     * Starts the service and waits until the application is stopped.
     * @param args optional endpoint address
     * @throws Exception if publication fails or the process is interrupted */
    public static void main(String[] args) throws Exception {
        String address = args.length == 0 ? "http://localhost:8080/students" : args[0];
        ServicePublisher publisher = new ServicePublisher(address, new StudentServiceImpl());
        Runtime.getRuntime().addShutdownHook(new Thread(publisher::close));
        System.out.println("Student service WSDL: " + address + "?wsdl");
        new CountDownLatch(1).await();
    }
}
