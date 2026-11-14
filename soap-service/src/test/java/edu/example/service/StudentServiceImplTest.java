package edu.example.service;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {
    @Test
    void delegatesThroughRepositoryInterface() throws Exception {
        Student expected = new Student();
        expected.setId(42);
        StudentRepository repository = new StudentRepository() {
            public List<Student> findAll() {
                return List.of(expected);
            }
            public Optional<Student> findById(int id) {
                return id == 42 ? Optional.of(expected) : Optional.empty();
            }
        };
        StudentService service = new StudentServiceImpl(repository);
        assertSame(expected, service.getStudent(42));
        assertEquals(List.of(expected), service.getStudents());
        StudentNotFoundException fault = assertThrows(StudentNotFoundException.class,
                () -> service.getStudent(999));
        assertEquals("NOT_FOUND", fault.getFaultInfo().getCode());
        assertEquals(999, fault.getFaultInfo().getStudentId());
        assertThrows(NullPointerException.class, () -> new StudentServiceImpl(null));
    }
}
