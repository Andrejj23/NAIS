package rs.ac.uns.acs.nais.GraphDatabaseService.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Student;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.StudentRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.IStudentService;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Program;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.ProgramRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.IProgramService;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.CompletedCourseDTO;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.CourseRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.ICourseService;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.CompletedCourse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.awt.*;



@Service
public class StudentService implements IStudentService{

    public final StudentRepository studentRepository;
    public final ProgramRepository programRepository;
    public final CourseRepository courseRepository;


    public StudentService(StudentRepository studentRepository, ProgramRepository programRepository, CourseRepository courseRepository){
        this.studentRepository = studentRepository;
        this.programRepository = programRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findOneById(String index) {
        Optional<Student> student = studentRepository.findById(index);
        if(student.isPresent()){
            return student.get();
        }
        return null;
    }

    @Override
    public Student addNewStudent(Student student) {
        Student student1 = studentRepository.save(student);
        return student1;
    }

    @Override
    public boolean deleteStudent(String index) {
        Student student = studentRepository.findByIndex(index);
        if(student != null){
            studentRepository.delete(student);
            return true;
        }
        return false;
    }

    @Override
    public Student addEnrolledProgram(String studentIndex, String programId){
        Optional<Student> student = studentRepository.findById(studentIndex);
        Optional<Program> program = programRepository.findById(programId);
        if(student.isPresent() && program.isPresent()){
            student.get().addEnrollment(program.get());
            return studentRepository.save(student.get());
        }
        return null;

    }

    @Override
    public boolean removeEnrolledProgram(String studentIndex, String programId) {
        studentRepository.deleteEnrolledRelationship(studentIndex, programId);
        return true;
    }

    @Override
    public List<Program> findEnrolledPrograms(String studentIndex){
        Optional<Student> student = studentRepository.findById(studentIndex);
        if(student.isPresent()){
            return programRepository.findAllByStudent(student.get().getIndex());
        }
        return null;
    }

    @Override
    public Student addCompletedCourse(CompletedCourseDTO completedCourseDTO) {
        Optional<Student> student = studentRepository.findById(completedCourseDTO.getStudentIndex());
        Optional<Course> course = courseRepository.findById(completedCourseDTO.getCourseId());
        if(student.isPresent() && course.isPresent()){
            CompletedCourse completedCourse = new CompletedCourse();
            completedCourse.setCourse(course.get());
            completedCourse.setGrade(completedCourseDTO.getGrade());
            completedCourse.setDate(completedCourseDTO.getDate());
            student.get().addCompletedCourse(completedCourse);
            return studentRepository.save(student.get());
        }
        return null;
    }


}
