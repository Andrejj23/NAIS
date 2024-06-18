package rs.ac.uns.acs.nais.GraphDatabaseService.service;

import rs.ac.uns.acs.nais.GraphDatabaseService.model.Teacher;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Program;

import java.io.IOException;
import java.util.List;

public interface IProgramService {

    List<Program> findAll();

    Program findOneById(String id);

    Program addNewProgram(Program program);

    boolean updateProgram(String id, int financedNum);

    Program addContainingCourse(String programId, Long courseId);

    boolean removeContainingCourse(String programId, Long courseId);

    List<Course> findContainingCourses(String programId);
}
