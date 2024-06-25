package rs.ac.uns.acs.nais.GraphDatabaseService.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ac.uns.acs.nais.GraphDatabaseService.dto.CourseRecommendationDTO;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Teacher;
import java.util.List;
import java.util.Map;

import rs.ac.uns.acs.nais.GraphDatabaseService.dto.CourseDTO;;

@Repository
public interface CourseRepository extends Neo4jRepository<Course, Long> {

    @Query("MATCH (t:Teacher {id: $teacherId})-[r:TEACHING]->(c:Course) return c")
    List<Course> findAllByTeacher(Long teacherId);

    @Query("MATCH (p:Program {id: $programId})-[r:CONTAINS]->(c:Course) return c")
    List<Course> findAllByProgram(String programId);

    @Query("MATCH (c:Course) OPTIONAL MATCH (s:Student) - [com:COMPLETED] -> (c) WITH c, count(s) as numStudents WHERE numStudents<$threshold set c.isActive=false return count(c) as updatedCount")
    int deactivate(int threshold);


    @Query("MATCH (s:Student {index: $index})-[:ENROLLED]->(p:Program) " +
           "WITH s, p " +
           "MATCH (c:Course) " +
           "WHERE NOT (s)-[:COMPLETED]->(c) AND NOT (p)-[:CONTAINS]->(c) " +
           "MATCH (c)<-[r:COMPLETED]-(otherStudent:Student) " +
           "WITH c, AVG(r.grade) AS avgGrade " +
           "WHERE avgGrade > 8 " +
           "RETURN c")
           List<Course> findRecommendedCourses(String index);

    
    /*@Query("MATCH (c:Course {id:2}) return c.description AS description")
    CourseDTO finddd();  */     

    // Prikaz predmeta odredjenog tipa koji su aktivni
    @Query("MATCH (c:Course) WHERE c.isActive=true AND c.type=$type RETURN c")
    List<Course> findAllActiveByType(String type);

    //Prikaz prosecnog broja espb bodova po tipu predmeta, za predmete koji su aktivni
    @Query("MATCH (c:Course) WHERE c.isActive=true WITH c.type AS courseType, avg(c.espb) AS averageEspb RETURN {type: courseType, avgESPB: averageEspb}")
    Iterable<Map<String, Object>> findAverageESPBByType();




}
