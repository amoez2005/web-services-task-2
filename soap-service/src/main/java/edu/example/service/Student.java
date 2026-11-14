package edu.example.service;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;

/** JAXB student data transfer object containing primitive and dependent object fields. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "name", "age", "gpa", "active", "groupCode", "subjects"})
public class Student {
    private int id;
    private String name;
    private int age;
    private float gpa;
    private boolean active;
    private char groupCode;
    @XmlElementWrapper(name = "subjects")
    @XmlElement(name = "subject")
    private List<Subject> subjects = new ArrayList<>();

    /** Creates an empty JAXB bean. */
    public Student() {
    }

    /**
     * Returns unique identifier.
     * @return unique identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Sets unique identifier.
     * @param id unique identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns student name.
     * @return student name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets student name.
     * @param name student name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns age in years.
     * @return age in years
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets age in years.
     * @param age age in years
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns grade point average.
     * @return grade point average
     */
    public float getGpa() {
        return gpa;
    }

    /**
     * Sets grade point average.
     * @param gpa grade point average
     */
    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    /**
     * Returns whether enrolled.
     * @return whether enrolled
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets whether enrolled.
     * @param active whether enrolled
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns group character; JAXB represents char as XML unsignedShort.
     * @return group character; JAXB represents char as XML unsignedShort
     */
    public char getGroupCode() {
        return groupCode;
    }

    /**
     * Sets group character.
     * @param groupCode group character
     */
    public void setGroupCode(char groupCode) {
        this.groupCode = groupCode;
    }

    /**
     * Returns mutable subjects list, as required by JAXB.
     * @return mutable subjects list, as required by JAXB
     */
    public List<Subject> getSubjects() {
        return subjects;
    }

    /**
     * Sets subjects list.
     * @param subjects subjects list
     */
    public void setSubjects(List<Subject> subjects) {
        this.subjects = new ArrayList<>(subjects);
    }
}
