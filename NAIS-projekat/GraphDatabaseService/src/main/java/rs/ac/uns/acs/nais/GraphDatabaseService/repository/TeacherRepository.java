package rs.ac.uns.acs.nais.GraphDatabaseService.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Teacher;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;

import java.util.List;


@Repository
public interface TeacherRepository extends Neo4jRepository<Teacher, Long> {

    @Query("MATCH (t:Teacher {id: $teacherId})-[r:TEACHING]->(c:Course {id: $courseId}) DELETE r")
    void deleteTeachesRelationship(Long teacherId, Long courseId);

}
