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
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.impl.CourseService;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/courses.json")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("")
    public ResponseEntity<List<Course>> findAll() {
        List<Course> courses = courseService.findAll();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findOneById(@PathVariable Long id) {
        Course course = courseService.findOneById(id);
        if(course!=null){
            return new ResponseEntity<>(course, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<Course> addNewCourse(@RequestBody Course course){
        Course createdCourse = courseService.addNewCourse(course);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Course> deleteCourse(@RequestParam("id") Long id){
        if(courseService.deleteCourse(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<Course> updateCourse(@RequestParam("id") Long id, @RequestParam("description") String description ){
        if(courseService.updateCourse(id, description)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
