package rs.ac.uns.acs.nais.GraphDatabaseService.repository;

import java.util.List;
import java.util.Map;

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

    @Query("MATCH (s:Student {index: $index}) - [com:COMPLETED] -> (c:Course {id: $courseId}) set com.grade=$grade")
    void updateGrade(String index, Long courseId, int grade);

    @Query("MATCH (s:Student) - [:ENROLLED] -> (p:Program {id: $programId}) return s")
    List<Student> findAllFromProgrm(String programId);

    //Prikaz svih studenata 4 godine koji su na budzetu i prvi put su upisali 4 godinu
    @Query("MATCH (s:Student) WHERE s.yearOfStudy=4 AND s.methodOfFinancing='BUDGET' and s.numberOfEnrollingYear=1 RETURN s")
    List<Student> findAllFirstTimeFourthYearBudgetStudents();

    //Prikaz broja budzetskih studenata po godini studija
    @Query("MATCH (s:Student) WHERE s.methodOfFinancing='BUDGET' WITH s.yearOfStudy AS yearOfStudy, count(s) AS numOfBudgetStudents" +
    "RETURN {yearOfStudy: yearOfStudy, numOfBudgetStudents: numOfBudgetStudents}")
    Iterable<Map<String, Object>> findNumberOfBudgetStudentsByStudyYear();

}
