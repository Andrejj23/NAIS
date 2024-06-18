package rs.ac.uns.acs.nais.GraphDatabaseService.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Teacher;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.TeacherRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.ITeacherService;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.CourseRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.ICourseService;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Program;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.ProgramRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.IProgramService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.awt.*;

@Service
public class ProgramService implements IProgramService{

    public final ProgramRepository programRepository;
    public final CourseRepository courseRepository;


    public ProgramService(ProgramRepository programRepository, CourseRepository courseRepository){
        this.programRepository = programRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Program> findAll() {
        return programRepository.findAll();
    }

    @Override
    public Program findOneById(String id) {
        Optional<Program> program = programRepository.findById(id);
        if(program.isPresent()){
            return program.get();
        }
        return null;
    }

    @Override
    public Program addNewProgram(Program program) {
        Program program1 = programRepository.save(program);
        return program1;
    }

    @Override
    public boolean updateProgram(String id, int financedNum) {
        Optional<Program> program = programRepository.findById(id);
        if(program.isPresent()){
            program.get().setFinancedNum(financedNum);
            programRepository.save(program.get());
            return true;
        }
        return false;
    }

    @Override
    public Program addContainingCourse(String programId, Long courseId){
        Optional<Program> program = programRepository.findById(programId);
        Optional<Course> course = courseRepository.findById(courseId);
        if(program.isPresent() && course.isPresent()){
            program.get().addContaining(course.get());
            return programRepository.save(program.get());
        }
        return null;

    }

    @Override
    public boolean removeContainingCourse(String programId, Long courseId) {
        programRepository.deleteContainsRelationship(programId, courseId);
        return true;
    }

    @Override
    public List<Course> findContainingCourses(String programId){
        Optional<Program> program = programRepository.findById(programId);
        if(program.isPresent()){
            return courseRepository.findAllByProgram(program.get().getId());
        }
        return null;
    }

}
