package rs.ac.uns.acs.nais.GraphDatabaseService.dto;

import java.util.List;

public class CourseRecommendationDTO {

    private List<String> recommendedCourse;
    //private int avgGrade;

    public CourseRecommendationDTO(List<String> recommendedCourse/*, int avgGrade*/){
        this.recommendedCourse = recommendedCourse;
        //this.avgGrade = avgGrade;
    }

    public List<String> getRecommendedCourse(){
        return recommendedCourse;
    }

    public void setRecommendedCourse(List<String> recommendedCourse){
        this.recommendedCourse = recommendedCourse;
    }

   /*  public int getAvgGrade(){
        return avgGrade;
    }

    public void setAvgGrade(int avgGrade){
        this.avgGrade = avgGrade;
    }*/

}
