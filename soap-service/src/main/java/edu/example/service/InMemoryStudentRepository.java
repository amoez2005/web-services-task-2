package edu.example.service;

import java.util.List;
import java.util.Optional;

/** Read-only sample repository; each read returns independent object graphs. */
public final class InMemoryStudentRepository implements StudentRepository {
    /** Creates a sample repository. */
    public InMemoryStudentRepository() {
    }

    @Override
    public List<Student> findAll() {
        return List.of(student(1, "Alice Morgan", 20, 3.8f, true, 'A'),
                student(2, "Daniel Reed", 22, 3.4f, false, 'B'));
    }

    @Override
    public Optional<Student> findById(int id) {
        return findAll().stream().filter(student -> student.getId() == id).findFirst();
    }

    private Student student(int id, String name, int age, float gpa, boolean active, char group) {
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setGpa(gpa);
        student.setActive(active);
        student.setGroupCode(group);
        student.setSubjects(List.of(new Subject("Web Services", 5, 94.5f),
                new Subject("XML Technologies", 4, 89.0f)));
        return student;
    }
}
