package rs.ac.uns.acs.nais.GraphDatabaseService.model;

import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;

@RelationshipProperties
public class CompletedCourse {

    @RelationshipId
    private  Long id;
    @TargetNode
    private  Course course;
    private int grade;
    private String date;

    public CompletedCourse(){
    }

    public CompletedCourse(Long id, Course course, int grade, String date){
        this.id = id;
        this.course = course;
        this.grade = grade;
        this.date = date;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse(){
        return course;
    }

    public void setCourse(Course course){
        this.course = course;
    }

    public int getGrade(){
        return grade;
    }

    public void setGrade(int grade){
        this.grade = grade;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }



}
