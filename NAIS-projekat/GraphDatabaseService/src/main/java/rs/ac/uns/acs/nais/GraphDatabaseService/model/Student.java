package rs.ac.uns.acs.nais.GraphDatabaseService.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.CompletedCourse;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Program;
import java.util.ArrayList;
import java.util.List;


@Node
public class Student {

    @Id
    private String index;

    private String firstName;

    private String lastName;

    private String email;

    private int yearOfStudy;

    private String methodOfFinancing;

    private int numberOfEnrollingYear;

    @Relationship(value = "COMPLETED", direction = Relationship.Direction.OUTGOING)
    private List<CompletedCourse> completed = new ArrayList<>();

    @Relationship(value = "ENROLLED", direction = Relationship.Direction.OUTGOING)
    private List<Program> enrolled = new ArrayList<>();

    
    public Student(){
    }

    public String getIndex(){
        return index;
    }

    public void setIndex(String index){
        this.index = index;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public List<CompletedCourse> getCompleted(){
        return completed;
    }

    public void setCompleted(List<CompletedCourse> completed){
        this.completed = completed;
    }

    /*public List<Program> getEnrolled(){
        return enrolled;
    }

    public void setEnrolled(List<Program> enrolled){
        this.enrolled = enrolled;
    }*/

    public void addEnrollment(Program program){
        this.enrolled.add(program);
    }

    public void addCompletedCourse(CompletedCourse completedCourse){
        this.completed.add(completedCourse);
    }

    public int getYearOfStudy(){
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy){
        this.yearOfStudy = yearOfStudy;
    }

    public String getMethodOfFinancing(){
        return methodOfFinancing;
    }

    public void setMethodOfFinancing(String methodOfFinancing){
        this.methodOfFinancing = methodOfFinancing;
    }

    public int getNumberOfEnrollingYear(){
        return numberOfEnrollingYear;
    }

    public void setNumberOfEnrollingYear(int numberOfEnrollingYear){
        this.numberOfEnrollingYear = numberOfEnrollingYear;
    }


}
