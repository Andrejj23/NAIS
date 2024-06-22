package rs.ac.uns.acs.nais.GraphDatabaseService.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.util.ArrayList;
import java.util.List;


@Node
public class Course{

    @Id
    private Long id;

    private String description;

    private Boolean isActive;

    private String type;

    private int espb;

    public Course(){
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public int getEspb(){
        return espb;
    }

    public void setEspb(int espb){
        this.espb=espb;
    }

}
