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
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Teacher;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.impl.TeacherService;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;

import rs.ac.uns.acs.nais.GraphDatabaseService.model.Program;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.impl.ProgramService;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/programs.json")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("")
    public ResponseEntity<List<Program>> findAll() {
        List<Program> programs = programService.findAll();
        return new ResponseEntity<>(programs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Program> findOneById(@PathVariable String id) {
        Program program = programService.findOneById(id);
        if(program!=null){
            return new ResponseEntity<>(program, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Program> addNewProgram(@RequestBody Program program){
        Program createdProgram = programService.addNewProgram(program);
        return new ResponseEntity<>(createdProgram, HttpStatus.CREATED);
    }

    //fali delete ako treba!

    @PutMapping("/{id}")
    public ResponseEntity<Program> updateProgram(@PathVariable("id") String id, @RequestParam("financedNum") int financedNum ){
        if(programService.updateProgram(id, financedNum)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("addContainingCourse")
    public ResponseEntity addNewContainingCourse(@RequestParam String programId, @RequestParam Long courseId){
        if(programService.addContainingCourse(programId, courseId) != null){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("removeContainingCourse")
    public ResponseEntity removeContainingCourse(@RequestParam String programId, @RequestParam Long courseId){
        if(programService.removeContainingCourse(programId, courseId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("findContainingCourses/{id}")
    public ResponseEntity<List<Course>> findContainingCourses(@PathVariable String id){
        List<Course> courses = programService.findContainingCourses(id);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

}
