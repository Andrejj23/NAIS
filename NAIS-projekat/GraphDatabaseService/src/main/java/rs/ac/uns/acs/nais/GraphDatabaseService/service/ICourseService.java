package rs.ac.uns.acs.nais.GraphDatabaseService.service;

import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;

import java.io.IOException;
import java.util.List;


public interface ICourseService {

    List<Course> findAll();

    Course addNewCourse(Course course);

    boolean deleteCourse(Long id);

    boolean updateCourse(Long id, String description);

    Course findOneById(Long id);

}
