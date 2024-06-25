package rs.ac.uns.acs.nais.GraphDatabaseService.service.impl;

import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Image;
//import javax.imageio.ImageIO;

import rs.ac.uns.acs.nais.GraphDatabaseService.dto.CourseRecommendationDTO;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Course;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Product;
import rs.ac.uns.acs.nais.GraphDatabaseService.model.Student;
import rs.ac.uns.acs.nais.GraphDatabaseService.repository.CourseRepository;
import rs.ac.uns.acs.nais.GraphDatabaseService.service.ICourseService;
import rs.ac.uns.acs.nais.GraphDatabaseService.dto.CourseDTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.awt.*;


/*import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import java.awt.image.BufferedImage;*/



@Service
public class CourseService implements ICourseService {

    public final CourseRepository courseRepository;

    private final Neo4jClient neo4jClient;

    public CourseService(CourseRepository courseRepository, Neo4jClient neo4jClient){
        this.courseRepository = courseRepository;
        this.neo4jClient = neo4jClient;
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

    @Override
    public int deactivateCourse(int threshold){
        //Optional<Course> course = courseRepository.findById(courseId);
        /*if(course.isPresent()){
            courseRepository.deactivate(courseId);
            return true;
        }
        return false;*/
        return courseRepository.deactivate(threshold);
    }

    @Override
    public List<Course> getCourseRecommendations(String studentIndex) {
        return courseRepository.findRecommendedCourses(studentIndex);
    }

    /*@Override
    public BufferedImage createPieChart(List<Course> courses){

        DefaultPieDataset dataset = new DefaultPieDataset<>();
        for (Course course : courses){
            dataset.setValue(course.getDescription(), course.getEspb());
        }

        JFreeChart chart = ChartFactory.createPieChart("Courses by ESPB", dataset, true, true, false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})"));

        return chart.createBufferedImage(500, 300);

    }*/


    @Override
    public byte[] export(List<Course> courses) throws IOException {
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

        Paragraph title = new Paragraph("COURSE REPORT", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add some space before the table
        document.add(new Paragraph("\n\n"));

        PdfPTable reportTable = new PdfPTable(3);
        reportTable.setWidthPercentage(100);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD);
        PdfPCell headerCell1 = new PdfPCell(new Paragraph("DESCRIPTION", headerFont));
        PdfPCell headerCell2 = new PdfPCell(new Paragraph("ESPB", headerFont));
        PdfPCell headerCell3 = new PdfPCell(new Paragraph("ACTIVE", headerFont));

        headerCell1.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell2.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell3.setBackgroundColor(new Color(110, 231, 234, 255));

        headerCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell3.setHorizontalAlignment(Element.ALIGN_CENTER);

        reportTable.addCell(headerCell1);
        reportTable.addCell(headerCell2);
        reportTable.addCell(headerCell3);

        for (Course course : courses) {

            reportTable.setWidthPercentage(100);
			reportTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			reportTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            reportTable.addCell(course.getDescription());
            reportTable.addCell(String.valueOf(course.getEspb()));
            reportTable.addCell(String.valueOf(course.getActive()));
            
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

    public CourseDTO gettt(Long courseId){
        return neo4jClient.query("MATCH (c:Course {id: $courseId}) RETURN c.description AS description")
                      .bind(courseId).to("courseId")
                      .fetchAs(CourseDTO.class)
                      .mappedBy((typeSystem, record) -> new CourseDTO(record.get("description").asString()))
                      .one()
                      .orElse(new CourseDTO("Default Description"));  // Provide a default value if none is found
    }


}
