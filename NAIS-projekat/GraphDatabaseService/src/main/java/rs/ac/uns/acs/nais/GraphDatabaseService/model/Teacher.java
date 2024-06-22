package rs.ac.uns.acs.nais.GraphDatabaseService.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

@Node
public class Teacher {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String email;
    //Assistant Professor, Associate Professor, Full Professor
    private String position;

    private String organizationalUnit;



    /*@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;*/

    @Relationship(value = "TEACHING", direction = Relationship.Direction.OUTGOING)
    private List<Course> teaching = new ArrayList<>();

    /*@Relationship(value = "MENTORING", direction = Relationship.Direction.OUTGOING)
    private List<Mentor> mentoring = new ArrayList<>();*/

    public Teacher(){
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
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

    public String getPosition(){
        return position;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public String getOrganizationalUnit(){
        return organizationalUnit;
    }

    public void setOrganizationalUnit(String organizationalUnit){
        this.organizationalUnit = organizationalUnit;
    }

    /*public Date getBirthDate(){
        return birthDate;
    }

    public void setBirthDate(Date birthDate){
        this.birthDate = birthDate;
    }*/

    /*public List<Course> getTeaching(){
        return teaching;
    }

    public void setTeaching(List<Course> teaching){
        this.teaching = teaching;
    }*/

    /*public List<Mentor> getMentoring(){
        return mentoring;
    }

    public void setMentoring(List<Mentor> mentoring){
        this.mentoring = mentoring;
    }*/

    public void addTeaching(Course course){
        this.teaching.add(course);
    }

}
