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
import rs.ac.uns.acs.nais.GraphDatabaseService.model.CompletedCourse;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Program;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.ProgramRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.ICourseService;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.impl.ProgramService;

import rs.ac.uns.acs.nais.GraphDatabaseService.dto.CompletedCourseDTO;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.StudentProgressDTO;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/students.json")
public class StudentController {

    private final StudentService studentService;

    private final ProgramService programService;

    public StudentController(StudentService studentService, ProgramService programService) {
        this.studentService = studentService;
        this.programService = programService;
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

    @PutMapping("updateGrade/{index}/{courseId}")
    public ResponseEntity<CompletedCourse> updateGrade(@PathVariable String index, @PathVariable Long courseId, @RequestParam int grade){
        if(studentService.updateGrade(index, courseId, grade)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/export-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> exportPdf(@RequestParam String programId) {
        List<Student> students = studentService.findAllFromProgram(programId);
        Program p = programService.findOneById(programId);
        try {
            byte[] pdfContents = studentService.export(students, p.getName());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "courses.pdf");

            return ResponseEntity.ok()
                                 .headers(headers)
                                 .body(pdfContents);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/recommendedTutors")
    public ResponseEntity<List<StudentProgressDTO>> getTopStudentProgress() {
        List<StudentProgressDTO> students = studentService.getTopStudents();
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(students);
        }
    }



    @GetMapping(value = "/export-pdf-complex", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> exportPdf() {
        List<StudentProgressDTO> students = studentService.getTopStudents();
        try {
            byte[] pdfContents = studentService.exportComplex(students);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "complexStudents.pdf");

            return ResponseEntity.ok()
                                 .headers(headers)
                                 .body(pdfContents);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("findAllFirstTimeFourthYearBudgetStudents")
    public ResponseEntity<List<Student>> findAllFirstTimeFourthYearBudgetStudents(){
        List<Student> students = studentService.findAllFirstTimeFourthYearBudgetStudents();
        if(students != null){
            return new ResponseEntity<>(students, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("findNumberOfBudgetStudentsByStudyYear")
    public ResponseEntity<Iterable<Map<String, Object>>> findNumberOfBudgetStudentsByStudyYear(){
        Iterable<Map<String, Object>> results = studentService.findNumberOfBudgetStudentsByStudyYear();
        if(results != null){
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
