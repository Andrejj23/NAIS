package rs.ac.uns.acs.nais.GraphDatabaseService.service;

import rs.ac.uns.acs.nais.GraphDatabaseService.model.Student;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Program;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.CompletedCourseDTO;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.StudentProgressDTO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface IStudentService {

    List<Student> findAll();

    Student findOneById(String index);

    Student addNewStudent(Student student);

    boolean deleteStudent(String index);

    Student addEnrolledProgram(String studentIndex, String programId);

    boolean removeEnrolledProgram(String studentIndex, String programId);

    List<Program> findEnrolledPrograms(String studentIndex);

    Student addCompletedCourse(CompletedCourseDTO completedCourseDTO);

    boolean updateGrade(String index, Long courseId, int grade);

    List<Student> findAllFromProgram(String programId);

    public byte[] export(List<Student> students, String programName) throws IOException;

    List<StudentProgressDTO> getTopStudents();

    public byte[] exportComplex(List<StudentProgressDTO> studentProgress) throws IOException;

    List<Student> findAllFirstTimeFourthYearBudgetStudents();

    Iterable<Map<String, Object>> findNumberOfBudgetStudentsByStudyYear();

}
