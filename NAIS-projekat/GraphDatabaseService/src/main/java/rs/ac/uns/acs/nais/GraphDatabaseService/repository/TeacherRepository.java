package rs.ac.uns.acs.nais.GraphDatabaseService.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Teacher;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;

import java.util.List;
import java.util.Map;


@Repository
public interface TeacherRepository extends Neo4jRepository<Teacher, Long> {

    @Query("MATCH (t:Teacher {id: $teacherId})-[r:TEACHING]->(c:Course {id: $courseId}) DELETE r")
    void deleteTeachesRelationship(Long teacherId, Long courseId);

    //Prikaz redovnih profesore, broja aktivnih predmeta koji predaju i prosecnog broja espb bodova za te predmete
    @Query("MATCH (t:Teacher {position: 'Full Professor'})-[r:TEACHING]->(c:Course) " +
           "WHERE c.isActive = true " +
           "WITH c, count(course) AS numOfCourses, avg(c.espb) AS averageEspb " +
           "RETURN t.firstName AS name, t.lastName AS lastname, numOfCourses, averageEspb " +
           "ORDER BY numOfCourses DESC")
    Iterable<Map<String, Object>> findRegularProfessorsWithCoursesAndAvgESPB();

    //Prikaz redovnih profesora, njihovih predmeta i broja studenata koji su polozili te predmete
    @Query("MATCH (t:Teacher {position: 'Full Professor'})-[r:TEACHING]->(c:Course)<-[:COMPLETED]-(s:Student) " +
           "WHERE c.isActive = true " +
           "RETURN t.firstName AS name, t.lastName AS lastName, c.name AS courseName, count(s) AS numOfStudentsWhoPassed " +
           "ORDER BY numOfStudentsWhoPassed DESC")
    Iterable<Map<String, Object>> findRegularProfessorsWithCoursesAndStudentsWhoPassed();

}
