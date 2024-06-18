// package rs.ac.uns.acs.nais.GraphDatabaseService.model;

// import org.springframework.data.neo4j.core.schema.RelationshipId;
// import org.springframework.data.neo4j.core.schema.RelationshipProperties;
// import org.springframework.data.neo4j.core.schema.TargetNode;
// import rs.ac.uns.acs.nais.GraphDatabaseService.model.Student;
// import java.util.Date;
// import java.util.ArrayList;
// import java.util.List;
// import com.fasterxml.jackson.annotation.JsonFormat;


// @RelationshipProperties
// public class Mentor {

//     @RelationshipId
//     private  Long id;
//     @TargetNode
//     private  Student student;
//     /*@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//     private Date startDate;*/
//     private String researchTopic;

//     public Mentor() {
//     }

//     public Mentor(Long id, Student student, /*Date startDate, */String researchTopic){
//         this.id = id;
//         this.student = student;
//         //this.startDate = startDate;
//         this.researchTopic = researchTopic;
//     }

//     public Long getId(){
//         return id;
//     }

//     public void setId(Long id){
//         this.id = id;
//     }

//     public Student getStudent(){
//         return student;
//     }

//     public void setStudent(Student student){
//         this.student = student;
//     }

//     /*public Date getStartDate(){
//         return startDate;
//     }

//     public void setStartDate(Date startDate){
//         this.startDate = startDate;
//     }*/

//     public String getResearchTopic(){
//         return researchTopic;
//     }

//     public void setResearchTopic(String researchTopic){
//         this.researchTopic = researchTopic;
//     }


// }
