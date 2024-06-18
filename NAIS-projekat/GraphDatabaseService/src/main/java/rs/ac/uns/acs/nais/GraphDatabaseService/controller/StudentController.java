package rs.ac.uns.acs.nais.GraphDatabaseService.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Student;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.impl.StudentService;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Program;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.ProgramRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.ICourseService;

import rs.ac.uns.acs.nais.GraphDatabaseService.dto.CompletedCourseDTO;

import java.io.ByteArrayOutputStream;
import java.util.List;


@RestController
@RequestMapping("/students.json")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("")
    public ResponseEntity<List<Student>> findAll() {
        List<Student> students = studentService.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{index}")
    public ResponseEntity<Student> findOneById(@PathVariable String index) {
        Student student = studentService.findOneById(index);
        if(student!=null){
            return new ResponseEntity<>(student, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Student> addNewStudent(@RequestBody Student student){
        Student createdStudent = studentService.addNewStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    //brisanje studenta brise i grane povezane sa kursom tog studenta
    @DeleteMapping
    public ResponseEntity<Student> deleteStudent(@RequestParam("index") String index){
        if(studentService.deleteStudent(index)){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("addEnrolledProgram")
    public ResponseEntity addNewEnrolledProgram(@RequestParam String studentId, @RequestParam String programId){
        if(studentService.addEnrolledProgram(studentId, programId) != null){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("removeEnrolledProgram")
    public ResponseEntity removeEnrolledProgram(@RequestParam String studentId, @RequestParam String programId){
        if(studentService.removeEnrolledProgram(studentId, programId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("findEnrolledPrograms/{index}")
    public ResponseEntity<List<Program>> findEnrolledPrograms(@PathVariable String index){
        List<Program> programs = studentService.findEnrolledPrograms(index);
        return new ResponseEntity<>(programs, HttpStatus.OK);
    }

    @PostMapping("addCompletedCourse")
    public ResponseEntity addNewCompletedCourse(@RequestBody CompletedCourseDTO completedCourseDTO){
        if(studentService.addCompletedCourse(completedCourseDTO) != null){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
