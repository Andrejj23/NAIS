package rs.ac.uns.acs.nais.GraphDatabaseService.service.impl;

import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Image;
import com.lowagie.text.Font;

import rs.ac.uns.acs.nais.GraphDatabaseService.model.Student;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.StudentRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.IStudentService;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Program;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.ProgramRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.IProgramService;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.CompletedCourseDTO;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.StudentProgressDTO;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.TeacherRecommendationDTO;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.CourseRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.ICourseService;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.CompletedCourse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.awt.*;



@Service
public class StudentService implements IStudentService{

    public final StudentRepository studentRepository;
    public final ProgramRepository programRepository;
    public final CourseRepository courseRepository;
    private final Neo4jClient neo4jClient;
    


    public StudentService(StudentRepository studentRepository, ProgramRepository programRepository, CourseRepository courseRepository, Neo4jClient neo4jClient){
        this.studentRepository = studentRepository;
        this.programRepository = programRepository;
        this.courseRepository = courseRepository;
        this.neo4jClient = neo4jClient;
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

    @Override
    public boolean updateGrade(String index, Long courseId, int grade){
        Optional<Student> student = studentRepository.findById(index);
        Optional<Course> course = courseRepository.findById(courseId);
        if(student.isPresent() && course.isPresent()){
            studentRepository.updateGrade(index, courseId, grade);
            return true;
        }
        return false;
    }

    @Override
    public List<Student> findAllFromProgram(String programId){
        return studentRepository.findAllFromProgrm(programId);        
    }

    @Override
    public byte[] export(List<Student> students, String programName) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        com.lowagie.text.Document document = new com.lowagie.text.Document();

        String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss")) + ".pdf";

        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        // Load the image from the resources folder
        Image logo = Image.getInstance(getClass().getResource("/logo1.jpg"));
        logo.setAbsolutePosition(50, document.top() - 70); // Positioning the logo
        logo.scaleToFit(75, 75); // Resize the image
        document.add(logo);

        // Add report generation date and time
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        Paragraph dateParagraph = new Paragraph("Report is generated on " + dateTime, FontFactory.getFont(FontFactory.HELVETICA, 12));
        dateParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(dateParagraph);

        Paragraph autorParagraph = new Paragraph("Report is generated by Andrej Anisic", FontFactory.getFont(FontFactory.HELVETICA, 12));
        autorParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(autorParagraph);

        // Add some space after the date
        document.add(new Paragraph("\n\n"));



        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, Font.BOLD);

        Paragraph title = new Paragraph("STUDENTS ENROLLED IN PROGRAM: " + programName, titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add some space before the table
        document.add(new Paragraph("\n\n"));

        PdfPTable reportTable = new PdfPTable(5);
        reportTable.setWidthPercentage(100);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD);
        PdfPCell headerCell1 = new PdfPCell(new Paragraph("INDEX", headerFont));
        PdfPCell headerCell2 = new PdfPCell(new Paragraph("FIRST NAME", headerFont));
        PdfPCell headerCell3 = new PdfPCell(new Paragraph("LAST NAME", headerFont));
        PdfPCell headerCell4 = new PdfPCell(new Paragraph("YEAR OF STUDY", headerFont));
        PdfPCell headerCell5 = new PdfPCell(new Paragraph("FINANCING METHOD", headerFont));

        headerCell1.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell2.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell3.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell4.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell5.setBackgroundColor(new Color(110, 231, 234, 255));

        headerCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell5.setHorizontalAlignment(Element.ALIGN_CENTER);

        reportTable.addCell(headerCell1);
        reportTable.addCell(headerCell2);
        reportTable.addCell(headerCell3);
        reportTable.addCell(headerCell4);
        reportTable.addCell(headerCell5);

        for (Student student : students) {

            reportTable.setWidthPercentage(100);
			reportTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			reportTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            reportTable.addCell(student.getIndex());
            reportTable.addCell(student.getFirstName());
            reportTable.addCell(student.getLastName());
            reportTable.addCell(String.valueOf(student.getYearOfStudy()));
            reportTable.addCell(student.getMethodOfFinancing());
            
        }

        document.add(reportTable);

        /*BufferedImage chartImage = createPieChart(courses);
        ByteArrayOutputStream chartOut = new ByteArrayOutputStream();
        ImageIO.write(chartImage, "png", chartOut);
        chartOut.flush();
        Image chart = Image.getInstance(chartOut.toByteArray());
        document.add(chart);*/

        document.close();

        return byteArrayOutputStream.toByteArray();
    }


    @Override
    public List<StudentProgressDTO> getTopStudents() {
        String cypherQuery = """
            MATCH (s:Student)-[r:COMPLETED]->(c:Course)
            WHERE s.yearOfStudy = 4 AND r.grade >= 8
            WITH s, COUNT(DISTINCT c) AS coursesCompleted, AVG(r.grade) AS avgGrade
            WHERE coursesCompleted > 1
            MATCH (s)-[:ENROLLED]->(p:Program)
            WITH s, coursesCompleted, avgGrade, p
            MATCH (p)-[:CONTAINS]->(c)
            WHERE NOT EXISTS((s)-[:COMPLETED]->(c))
            WITH s, coursesCompleted, avgGrade, COUNT(c) AS requiredCoursesLeft
            ORDER BY requiredCoursesLeft, avgGrade DESC, coursesCompleted DESC
            RETURN s AS student, avgGrade, coursesCompleted, requiredCoursesLeft
            LIMIT 5
            """;

        Collection<StudentProgressDTO> result =  neo4jClient.query(cypherQuery)
                          .fetchAs(StudentProgressDTO.class)
                          .mappedBy((typeSystem, record) -> {
                                var s = record.get("student").asNode();
                                Student student = new Student(
                                  s.get("index").asString(),
                                  s.get("firstName").asString(),
                                  s.get("lastName").asString(),
                                  s.get("email").asString(),
                                  s.get("yearOfStudy").asInt(),
                                  s.get("methodOfFinancing").asString(),
                                  s.get("numberOfEnrollingYear").asInt()
                              );
                              return new StudentProgressDTO(student, record.get("avgGrade").asDouble(), record.get("coursesCompleted").asInt(), record.get("requiredCoursesLeft").asInt());
                          }).all();

        

        return new ArrayList<>(result);
    }

    @Override
    public byte[] exportComplex(List<StudentProgressDTO> studentProgress) throws IOException {

        List<Student> students = findAll();
        List<Program> programs = programRepository.findAll();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        com.lowagie.text.Document document = new com.lowagie.text.Document();

        String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss")) + ".pdf";

        PdfWriter.getInstance(document, byteArrayOutputStream);

        // Add a footer before opening the document
        HeaderFooter footer = new HeaderFooter(new Phrase("Page ", new Font(Font.HELVETICA, 8)), true);
        footer.setAlignment(HeaderFooter.ALIGN_CENTER);
        footer.setBorder(HeaderFooter.NO_BORDER);
        document.setFooter(footer);


        document.open();

        // Load the image from the resources folder
        Image logo = Image.getInstance(getClass().getResource("/logo1.jpg"));
        //logo.setAbsolutePosition(50, document.top() - 70); // Positioning the logo
        logo.setAlignment(Element.ALIGN_LEFT);
        logo.scaleToFit(75, 75); // Resize the image
        document.add(logo);

        // Report Title and Date
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD);

        Paragraph title = new Paragraph("REPORT: STUDENTS ", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add some space before the table
        document.add(new Paragraph("\n\n"));

        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        Paragraph dateParagraph = new Paragraph("Generated on: " + dateTime, FontFactory.getFont(FontFactory.HELVETICA, 12));
        dateParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(dateParagraph);


        // Add some space before the table
        document.add(new Paragraph("\n"));

        Paragraph prostaSekcija = new Paragraph("Simple section of report", FontFactory.getFont(FontFactory.HELVETICA, 12));
        prostaSekcija.setAlignment(Element.ALIGN_LEFT);
        document.add(prostaSekcija);

        // Collect data for the pie chart
        /*Map<String, Integer> programCounts = new HashMap<>();
        for (Program p : programs) {
            programCounts.put(p.getName(), findAllFromProgram(p.getId()).size());
        }*/

        for (Program p : programs) {

            // Add some space before the table
        document.add(new Paragraph("\n\n"));

        Paragraph subtitle = new Paragraph("Students enrolled in program: " + p.getName(), FontFactory.getFont(FontFactory.HELVETICA, 12));
        subtitle.setAlignment(Element.ALIGN_LEFT);
        document.add(subtitle);

        document.add(new Paragraph("\n"));

        PdfPTable reportTable = new PdfPTable(5);
        reportTable.setWidthPercentage(100);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD);
        PdfPCell headerCell1 = new PdfPCell(new Paragraph("INDEX", headerFont));
        PdfPCell headerCell2 = new PdfPCell(new Paragraph("FIRST NAME", headerFont));
        PdfPCell headerCell3 = new PdfPCell(new Paragraph("LAST NAME", headerFont));
        PdfPCell headerCell4 = new PdfPCell(new Paragraph("YEAR OF STUDY", headerFont));
        PdfPCell headerCell5 = new PdfPCell(new Paragraph("FINANCING METHOD", headerFont));

        headerCell1.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell2.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell3.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell4.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell5.setBackgroundColor(new Color(110, 231, 234, 255));

        headerCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell5.setHorizontalAlignment(Element.ALIGN_CENTER);

        reportTable.addCell(headerCell1);
        reportTable.addCell(headerCell2);
        reportTable.addCell(headerCell3);
        reportTable.addCell(headerCell4);
        reportTable.addCell(headerCell5);

        for (Student student : findAllFromProgram(p.getId())) {

            reportTable.setWidthPercentage(100);
			reportTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			reportTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            reportTable.addCell(student.getIndex());
            reportTable.addCell(student.getFirstName());
            reportTable.addCell(student.getLastName());
            reportTable.addCell(String.valueOf(student.getYearOfStudy()));
            reportTable.addCell(student.getMethodOfFinancing());
            
        }

        document.add(reportTable);
   
        }

        document.add(new Paragraph("\n\n"));

        Paragraph slozenaSekcija = new Paragraph("Complex section of report: Identifying potential tutors among final year students", FontFactory.getFont(FontFactory.HELVETICA, 12));
        slozenaSekcija.setAlignment(Element.ALIGN_LEFT);
        document.add(slozenaSekcija);

        document.add(new Paragraph("\n"));


        PdfPTable reportTable = new PdfPTable(6);
        reportTable.setWidthPercentage(100);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD);
        PdfPCell headerCell1 = new PdfPCell(new Paragraph("INDEX", headerFont));
        PdfPCell headerCell2 = new PdfPCell(new Paragraph("FIRST NAME", headerFont));       
        PdfPCell headerCell3 = new PdfPCell(new Paragraph("LAST NAME", headerFont));
        PdfPCell headerCell4 = new PdfPCell(new Paragraph("AVERAGE GRADE", headerFont));
        PdfPCell headerCell5 = new PdfPCell(new Paragraph("NUM. OF COMPLETED COURSES", headerFont));
        PdfPCell headerCell6 = new PdfPCell(new Paragraph("NUM. OF LEFT COURSES", headerFont));

        headerCell1.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell2.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell3.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell4.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell5.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell6.setBackgroundColor(new Color(110, 231, 234, 255));

        headerCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell6.setHorizontalAlignment(Element.ALIGN_CENTER);

        reportTable.addCell(headerCell1);
        reportTable.addCell(headerCell2);
        reportTable.addCell(headerCell3);
        reportTable.addCell(headerCell4);
        reportTable.addCell(headerCell5);
        reportTable.addCell(headerCell6);

        for (StudentProgressDTO dto : studentProgress) {

            reportTable.setWidthPercentage(100);
			reportTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			reportTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            reportTable.addCell(dto.getStudent().getIndex());
            reportTable.addCell(dto.getStudent().getFirstName());
            reportTable.addCell(dto.getStudent().getLastName());
            reportTable.addCell(String.valueOf(dto.getAvgGrade()));
            reportTable.addCell(String.valueOf(dto.getCoursesCompleted()));
            reportTable.addCell(String.valueOf(dto.getRequiredCoursesLeft()));
            
        }

        document.add(reportTable);        

        document.close();

        return byteArrayOutputStream.toByteArray();
    }


    @Override
    public List<Student> findAllFirstTimeFourthYearBudgetStudents(){
        return studentRepository.findAllFirstTimeFourthYearBudgetStudents();
    }

    @Override
    public Iterable<Map<String, Object>> findNumberOfBudgetStudentsByStudyYear(){
        return studentRepository.findNumberOfBudgetStudentsByStudyYear();
    }


}
