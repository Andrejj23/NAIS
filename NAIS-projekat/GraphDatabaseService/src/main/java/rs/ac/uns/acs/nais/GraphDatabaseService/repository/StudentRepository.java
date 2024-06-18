package rs.ac.uns.acs.nais.GraphDatabaseService.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Student;

@Repository
public interface StudentRepository extends Neo4jRepository<Student, String>{

    Student findByIndex(String index);

    @Query("MATCH (s:Student {index: $studentIndex})-[e:ENROLLED]->(p:Program {id: $programId}) DELETE e")
    void deleteEnrolledRelationship(String studentIndex, String programId);



}
