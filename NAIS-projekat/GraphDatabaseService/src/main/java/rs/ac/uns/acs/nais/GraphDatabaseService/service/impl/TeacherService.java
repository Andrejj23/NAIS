package rs.ac.uns.acs.nais.GraphDatabaseService.service.impl;

import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Teacher;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.TeacherRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.ITeacherService;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.CourseRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.ICourseService;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.TeacherRecommendationDTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.awt.*;

@Service
public class TeacherService implements ITeacherService {

    public final TeacherRepository teacherRepository;
    public final CourseRepository courseRepository;
    private final Neo4jClient neo4jClient;


    public TeacherService(TeacherRepository teacherRepository, CourseRepository courseRepository, Neo4jClient neo4jClient){
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.neo4jClient = neo4jClient;
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher findOneById(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if(teacher.isPresent()){
            return teacher.get();
        }
        return null;
    }

    @Override
    public Teacher addNewTeacher(Teacher teacher) {
        Teacher teacher1 = teacherRepository.save(teacher);
        return teacher1;
    }

    @Override
    public boolean updateTeacher(Long id, String email) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if(teacher.isPresent()){
            teacher.get().setEmail(email);
            teacherRepository.save(teacher.get());
            return true;
        }
        return false;
    }

    @Override
    public Teacher addTeachingCourse(Long teacherId, Long courseId){
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        Optional<Course> course = courseRepository.findById(courseId);
        if(teacher.isPresent() && course.isPresent()){
            teacher.get().addTeaching(course.get());
            return teacherRepository.save(teacher.get());
        }
        return null;

    }

    @Override
    public boolean removeTeachingCourse(Long teacherId, Long courseId) {
        teacherRepository.deleteTeachesRelationship(teacherId, courseId);
        return true;
    }

    @Override
    public List<Course> findTeachingCourses(Long teacherId){
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        if(teacher.isPresent()){
            return courseRepository.findAllByTeacher(teacher.get().getId());
        }
        return null;
    }

    @Override
    public List<TeacherRecommendationDTO> recommendTeachersBySuccessRate() {
        String cypherQuery = """
            MATCH (t:Teacher)-[:TEACHING]->(c:Course)<-[:COMPLETED]-(s:Student)
            WITH t, c, COUNT(s) AS studentsCompleted
            MATCH (p:Program)-[:CONTAINS]->(c)
            MATCH (se:Student)-[:ENROLLED]->(p)
            WITH t, c, studentsCompleted, COUNT(DISTINCT se) AS studentsEnrolled
            WITH t, AVG(toFloat(studentsCompleted) / studentsEnrolled) AS avgSuccessRate
            RETURN t.firstName AS teacher, avgSuccessRate
            ORDER BY avgSuccessRate DESC
            LIMIT 2
            """;

        Collection<TeacherRecommendationDTO> result =  neo4jClient.query(cypherQuery)
                          .fetchAs(TeacherRecommendationDTO.class)
                          .mappedBy((typeSystem, record) -> new TeacherRecommendationDTO(
                              record.get("teacher").asString(),
                              record.get("avgSuccessRate").asDouble()))
                          .all();

        return new ArrayList<>(result);
    }

    @Override
    public Iterable<Map<String, Object>> findRegularProfessorsWithCoursesAndAvgESPB(){
        return teacherRepository.findRegularProfessorsWithCoursesAndAvgESPB();
    }

    @Override
    public Iterable<Map<String, Object>> findRegularProfessorsWithCoursesAndStudentsWhoPassed(){
        return teacherRepository.findRegularProfessorsWithCoursesAndStudentsWhoPassed();
    }

    @Override
    public boolean updateTeacherPosition(Long id, String position) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if(teacher.isPresent()){
            teacher.get().setPosition(position);
            teacherRepository.save(teacher.get());
            return true;
        }
        return false;
    }

}
