package rs.ac.uns.acs.nais.GraphDatabaseService.dto;

public class CompletedCourseDTO {

    private String studentIndex;
    private Long courseId;
    private int grade;
    private String date;

    public CompletedCourseDTO(){
    }

    public String getStudentIndex(){
        return studentIndex;
    }

    public void setStudentIndex(String studentIndex){
        this.studentIndex = studentIndex;
    }

    public Long getCourseId(){
        return courseId;
    }

    public void setCourseId(Long courseId){
        this.courseId = courseId;
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
