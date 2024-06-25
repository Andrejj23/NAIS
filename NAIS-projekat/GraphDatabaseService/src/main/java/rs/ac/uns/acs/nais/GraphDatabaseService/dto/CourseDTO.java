package rs.ac.uns.acs.nais.GraphDatabaseService.dto;

public class CourseDTO {

    private String description;

    public CourseDTO(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

}
