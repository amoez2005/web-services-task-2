package edu.example.service;

import java.util.List;
import java.util.Optional;

/** Storage abstraction used by the SOAP service. */
public interface StudentRepository {
    /**
     * Returns all students in identifier order.
     * @return all students in identifier order
     */
    List<Student> findAll();

    /**
     * Finds one student in the backing store.
     * @param id student identifier
     * @return matching student, if present */
    Optional<Student> findById(int id);
}
