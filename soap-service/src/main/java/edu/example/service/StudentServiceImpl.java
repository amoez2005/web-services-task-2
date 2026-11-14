package edu.example.service;

import java.util.List;
import java.util.Objects;
import jakarta.jws.WebService;

/** SOAP adapter delegating data access to a repository. */
@WebService(endpointInterface = "edu.example.service.StudentService",
        serviceName = "StudentService", portName = "StudentPort",
        targetNamespace = "https://example.edu/students")
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;

    /** Creates the default sample service. */
    public StudentServiceImpl() {
        this(new InMemoryStudentRepository());
    }

    /**
     * Sets storage implementation.
     * @param repository storage implementation
     */
    public StudentServiceImpl(StudentRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository");
    }

    @Override
    public List<Student> getStudents() {
        return repository.findAll();
    }

    @Override
    public Student getStudent(int id) throws StudentNotFoundException {
        return repository.findById(id).orElseThrow(() -> new StudentNotFoundException(
                "Student " + id + " was not found", new StudentFault("NOT_FOUND", id)));
    }
}
