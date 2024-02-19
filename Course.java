package studentgradingsystem; 
import java.io.*;
public class Course{

    private String courseCode;
    private String courseName;
    private int creditHours;
    private String instructor;

    public Course(String courseCode, String courseName, int creditHours, String instructor) {
        this.courseCode = courseCode.trim();
        this.courseName = courseName.trim();
        this.creditHours = creditHours;
        this.instructor = instructor.trim();
    }
 
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public static String getCourseName(String code) throws IOException {
        BufferedReader readCourse = new BufferedReader(new FileReader("course_info.txt"));
        String course="courseName";
        String line;
        while((line = readCourse.readLine())!= null){
            String[] atoms = line.split(",");
            if(atoms[1].toLowerCase().equals(code.toLowerCase()))
                course = atoms[0];
                break;
        } 
        readCourse.close();
        return course; 
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }
    public String getInstructor(){
        return instructor;
    }
    public void setInstructor(String instructor){
        this.instructor = instructor;
    }
    @Override
    public String toString(){
        return this.courseName + "," + this.courseCode + "," + this.creditHours + "," + this.instructor + "\n";
    }
}
