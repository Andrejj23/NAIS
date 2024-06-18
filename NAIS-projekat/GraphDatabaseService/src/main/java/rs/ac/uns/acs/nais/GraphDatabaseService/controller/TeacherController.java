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

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/teachers.json")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("")
    public ResponseEntity<List<Teacher>> findAll() {
        List<Teacher> teachers = teacherService.findAll();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> findOneById(@PathVariable Long id) {
        Teacher teacher = teacherService.findOneById(id);
        if(teacher!=null){
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Teacher> addNewTeacher(@RequestBody Teacher teacher){
        Teacher createdTeacher = teacherService.addNewTeacher(teacher);
        return new ResponseEntity<>(createdTeacher, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") Long id, @RequestParam("email") String email ){
        if(teacherService.updateTeacher(id, email)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("addTeachingCourse")
    public ResponseEntity addNewTeachingCourse(@RequestParam Long teacherId, @RequestParam Long courseId){
        if(teacherService.addTeachingCourse(teacherId, courseId) != null){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("removeTeachingCourse")
    public ResponseEntity removeTeachingCourse(@RequestParam Long teacherId, @RequestParam Long courseId){
        if(teacherService.removeTeachingCourse(teacherId, courseId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("findTeachingCourses/{id}")
    public ResponseEntity<List<Course>> findTeachingCourses(@PathVariable Long id){
        List<Course> courses = teacherService.findTeachingCourses(id);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

}
