package rs.ac.uns.acs.nais.GraphDatabaseService.service;

import rs.ac.uns.acs.nais.GraphDatabaseService.model.Student;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Program;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.CompletedCourseDTO;

import java.io.IOException;
import java.util.List;

public interface IStudentService {

    List<Student> findAll();

    Student findOneById(String index);

    Student addNewStudent(Student student);

    boolean deleteStudent(String index);

    Student addEnrolledProgram(String studentIndex, String programId);

    boolean removeEnrolledProgram(String studentIndex, String programId);

    List<Program> findEnrolledPrograms(String studentIndex);

    Student addCompletedCourse(CompletedCourseDTO completedCourseDTO);

}
