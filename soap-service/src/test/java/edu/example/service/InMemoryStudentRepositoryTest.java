package edu.example.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryStudentRepositoryTest {
    @Test
    void listsAndLooksUpStudentsWithoutLeakingMutableState() {
        StudentRepository repository = new InMemoryStudentRepository();
        assertEquals(2, repository.findAll().size());
        Student student = repository.findById(1).orElseThrow();
        assertEquals("Alice Morgan", student.getName());
        student.setName("Changed");
        student.getSubjects().get(0).setName("Changed");
        Student reloaded = repository.findById(1).orElseThrow();
        assertEquals("Alice Morgan", reloaded.getName());
        assertEquals("Web Services", reloaded.getSubjects().get(0).getName());
        assertTrue(repository.findById(999).isEmpty());
    }
}
