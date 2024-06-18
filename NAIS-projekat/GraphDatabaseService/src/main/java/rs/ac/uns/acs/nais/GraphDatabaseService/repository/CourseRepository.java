package rs.ac.uns.acs.nais.GraphDatabaseService.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Teacher;

import java.util.List;

@Repository
public interface CourseRepository extends Neo4jRepository<Course, Long> {

    @Query("MATCH (t:Teacher {id: $teacherId})-[r:TEACHING]->(c:Course) return c")
    List<Course> findAllByTeacher(Long teacherId);

    @Query("MATCH (p:Program {id: $programId})-[r:CONTAINS]->(c:Course) return c")
    List<Course> findAllByProgram(String programId);
}
