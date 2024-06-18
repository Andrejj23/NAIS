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
public class Program {

    @Id
    private String id;
    private String name;
    //UNDERGRADUTE,GRADUTE,PHD
    private String level;
    private int totalNum;
    private int financedNum;
    @Relationship(value = "CONTAINS", direction = Relationship.Direction.OUTGOING)
    private List<Course> contains = new ArrayList<>();

    public Program(){
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getLevel(){
        return level;
    }

    public void setLevel(String level){
        this.level = level;
    }

    public int getTotalNum(){
        return totalNum;
    }

    public void setTotalNum(int totalNum){
        this.totalNum = totalNum;
    }

    public int getFinancedNum(){
        return financedNum;
    }

    public void setFinancedNum(int financedNum){
        this.financedNum = financedNum;
    }

    /*public List<Course> getTeaching(){
        return teaching;
    }

    public void setTeaching(List<Course> teaching){
        this.teaching = teaching;
    }*/

    public void addContaining(Course course){
        this.contains.add(course);
    }


}
