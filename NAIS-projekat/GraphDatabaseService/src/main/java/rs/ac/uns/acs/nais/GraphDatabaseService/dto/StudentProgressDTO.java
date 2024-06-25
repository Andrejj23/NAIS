package rs.ac.uns.acs.nais.GraphDatabaseService.dto;

import rs.ac.uns.acs.nais.GraphDatabaseService.model.Student;

public class StudentProgressDTO {

    private Student student;
    private double avgGrade;
    private int coursesCompleted;
    private int requiredCoursesLeft;

    public StudentProgressDTO(Student student, double avgGrade, int coursesCompleted, int requiredCoursesLeft) {
        this.student = student;
        this.avgGrade = avgGrade;
        this.coursesCompleted = coursesCompleted;
        this.requiredCoursesLeft = requiredCoursesLeft;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public int getCoursesCompleted() {
        return coursesCompleted;
    }

    public void setCoursesCompleted(int coursesCompleted) {
        this.coursesCompleted = coursesCompleted;
    }

    public int getRequiredCoursesLeft() {
        return requiredCoursesLeft;
    }

    public void setRequiredCoursesLeft(int requiredCoursesLeft) {
        this.requiredCoursesLeft = requiredCoursesLeft;
    }



}
