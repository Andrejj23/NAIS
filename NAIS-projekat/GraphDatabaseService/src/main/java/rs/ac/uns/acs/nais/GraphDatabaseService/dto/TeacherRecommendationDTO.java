package rs.ac.uns.acs.nais.GraphDatabaseService.dto;

public class TeacherRecommendationDTO {

    private String teacher;
    private Double avgSuccessRate;

    public TeacherRecommendationDTO(String teacher, Double avgSuccessRate){
        this.teacher = teacher;
        this.avgSuccessRate = avgSuccessRate;
    }

    public String getTeacher(){
        return teacher;
    }

    public void setTeacher(String teacher){
        this.teacher = teacher;
    }

    public Double getAvgSuccessRate(){
        return avgSuccessRate;
    }

    public void setAvgSuccessRate(Double avgSuccessRate){
        this.avgSuccessRate = avgSuccessRate;
    }

}
