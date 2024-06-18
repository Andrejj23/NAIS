package rs.ac.uns.acs.nais.GraphDatabaseService.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.CourseRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.ICourseService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.awt.*;



@Service
public class CourseService implements ICourseService {

    public final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findOneById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if(course.isPresent()){
            return course.get();
        }
        return null;
    }

    @Override
    public Course addNewCourse(Course course) {
        
        course.setActive(true);
        Course course1 = courseRepository.save(course);
        
        return course1;
        
    }

    @Override
    public boolean deleteCourse(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if(course.isPresent()){
            course.get().setActive(false);
            courseRepository.save(course.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCourse(Long id, String description) {
        Optional<Course> course = courseRepository.findById(id);
        if(course.isPresent()){
            course.get().setDescription(description);
            courseRepository.save(course.get());
            return true;
        }
        return false;
    }


}
