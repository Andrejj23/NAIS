package rs.ac.uns.acs.nais.GraphDatabaseService.service;

import rs.ac.uns.acs.nais.GraphDatabaseService.dto.CourseDTO;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.CourseRecommendationDTO;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Product;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.awt.image.BufferedImage;


public interface ICourseService {

    List<Course> findAll();

    Course addNewCourse(Course course);

    boolean deleteCourse(Long id);

    boolean updateCourse(Long id, String description);

    Course findOneById(Long id);

    int deactivateCourse(int threshold);

    List<Course> getCourseRecommendations(String studentIndex);

    public byte[] export(List<Course> courses) throws IOException;

    //BufferedImage createPieChart(List<Course> courses);

    CourseDTO gettt(Long courseId);

    List<Course> findAllActiveByType(String type);

    Iterable<Map<String, Object>> findAverageESPBByType();

}
