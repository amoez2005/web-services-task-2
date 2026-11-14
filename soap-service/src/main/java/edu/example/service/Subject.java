package edu.example.service;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/** A subject studied by a student. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"name", "credits", "score"})
public class Subject {
    private String name;
    private int credits;
    private float score;

    /** Creates an empty JAXB bean. */
    public Subject() {
    }

    /**
     * Creates a subject.
     * @param name subject name
     * @param credits credit count
     * @param score score out of 100
     */
    public Subject(String name, int credits, float score) {
        this.name = name;
        this.credits = credits;
        this.score = score;
    }

    /**
     * Returns subject name.
     * @return subject name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets subject name.
     * @param name subject name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns credit count.
     * @return credit count
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Sets credit count.
     * @param credits credit count
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Returns score out of 100.
     * @return score out of 100
     */
    public float getScore() {
        return score;
    }

    /**
     * Sets score out of 100.
     * @param score score out of 100
     */
    public void setScore(float score) {
        this.score = score;
    }
}
