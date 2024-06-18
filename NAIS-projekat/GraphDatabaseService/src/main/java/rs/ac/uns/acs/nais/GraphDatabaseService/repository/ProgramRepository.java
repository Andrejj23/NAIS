package rs.ac.uns.acs.nais.GraphDatabaseService.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Teacher;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Program;

import java.util.List;

@Repository
public interface ProgramRepository extends Neo4jRepository<Program, String> {

    @Query("MATCH (p:Program {id: $programId})-[con:CONTAINS]->(c:Course {id: $courseId}) DELETE con")
    void deleteContainsRelationship(String programId, Long courseId);

    @Query("MATCH (s:Student {index: $studentIndex})-[e:ENROLLED]->(p:Program) return p")
    List<Program> findAllByStudent(String studentIndex);

}
