package edu.example.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudentModelTest {
    @Test
    void studentPreservesAllFieldTypesAndCopiesAssignedList() {
        Student student = new Student();
        student.setId(7);
        student.setName("Test Student");
        student.setAge(21);
        student.setGpa(3.75f);
        student.setActive(true);
        student.setGroupCode('C');
        List<Subject> subjects = new ArrayList<>(List.of(new Subject("SOAP", 5, 93.5f)));
        student.setSubjects(subjects);
        subjects.clear();
        assertAll(() -> assertEquals(7, student.getId()),
                () -> assertEquals("Test Student", student.getName()),
                () -> assertEquals(21, student.getAge()),
                () -> assertEquals(3.75f, student.getGpa()),
                () -> assertTrue(student.isActive()),
                () -> assertEquals('C', student.getGroupCode()),
                () -> assertEquals(1, student.getSubjects().size()));
    }

    @Test
    void subjectSupportsJaxbBeanConstruction() {
        Subject subject = new Subject();
        subject.setName("XML");
        subject.setCredits(4);
        subject.setScore(88.5f);
        assertAll(() -> assertEquals("XML", subject.getName()),
                () -> assertEquals(4, subject.getCredits()),
                () -> assertEquals(88.5f, subject.getScore()));
    }

    @Test
    void faultBeanAndExceptionPreserveDetails() {
        StudentFault fault = new StudentFault();
        fault.setCode("NOT_FOUND");
        fault.setStudentId(99);
        StudentNotFoundException exception = new StudentNotFoundException("Missing", fault);
        assertAll(() -> assertEquals("Missing", exception.getMessage()),
                () -> assertSame(fault, exception.getFaultInfo()),
                () -> assertEquals("NOT_FOUND", fault.getCode()),
                () -> assertEquals(99, fault.getStudentId()));
    }
}
