package rs.ac.uns.acs.nais.GraphDatabaseService.service;

import rs.ac.uns.acs.nais.GraphDatabaseService.model.Teacher;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.TeacherRecommendationDTO;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ITeacherService {

    List<Teacher> findAll();

    Teacher findOneById(Long id);

    Teacher addNewTeacher(Teacher teacher);

    Teacher addTeachingCourse(Long teacherId, Long courseId);

    boolean removeTeachingCourse(Long teacherId, Long courseId);

    List<Course> findTeachingCourses(Long teacherId);

    boolean updateTeacher(Long id, String email);

    List<TeacherRecommendationDTO> recommendTeachersBySuccessRate();

}
