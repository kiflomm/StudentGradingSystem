package studentgradingsystem;
import java.io.*;
import java.util.Scanner;
public class StudentGradingSystem{
    public static void main (String[] args) throws IOException{
        System.out.println("+++++++++++++++++++++++++++[ Student Grading System Console App ]+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++[ choose a number 1 - 9 or enter another key to exit the programm ]++++++++++++++++");
        System.out.println("____________________________________________________________________________________________________________");
        System.out.println("|   1. Add new student                                                                                     *");
        System.out.println("|   2. Edit student profile                                                                                *");
        System.out.println("|   3. Search a student by Id                                                                              *");
        System.out.println("|   4. Remove a student                                                                                    *"); 
        System.out.println("|   5. Enter marks by course or by student Id                                                              *");
        System.out.println("|   6. Add new course                                                                                      *");
        System.out.println("|   7. Remove a course                                                                                     *");
        System.out.println("|   8. See course details                                                                                  *");
        System.out.println("|   9. Display all student info                                                                            *"); 
        System.out.println("********************************************[ OR press any other key to exit ]****************************");
        File fileNameStudent = new File("student_info.txt");
        File fileNameCourse = new  File("course_info.txt");
        File fileResults = new File("results_info.txt");
        
        if(!fileNameStudent.exists()){
            new FileWriter("student_info.txt"); 
        }
        if(!fileNameCourse.exists()){  
            
            new FileWriter("course_info.txt");  
            BufferedWriter writer = new BufferedWriter(new FileWriter("course_info.txt",true)); 
            
            Course coa = new Course("COA2021","Computer architecture",5,"miki");
            String coa_info = coa.toString();
            
            Course networking = new Course("NET2021","Computer Networking",7,"dani"); 
            String networking_info = networking.toString();
            
            Course java = new Course("OOP2021","Object oriented programing",5,"Mahfuz");  
            String java_info = java.toString();
            
            Course soft = new Course("Sof2021","Fundamentals of software engineering",7,"sami"); 
            String soft_info = soft.toString();
            
            Course database = new Course("DB2021","Data base",5,"miki");  
            String database_info = database.toString();  
            
            writer.write(coa_info+networking_info+java_info+soft_info+database_info);
            writer.close(); 
        } 
        if(!fileResults.exists()){
            new FileWriter("results_info.txt");
        }
        int choice;
        Scanner input = new  Scanner(System.in);
        System.out.print("Enter your choice: ");
        choice = input.nextInt();
        switch(choice){
            case 1:  
                Menu.addStudent();
                break;
            case 2:  
                Menu.editStudent();
                break;
            case 3: 
                Menu.searchStudent();
                break;
            case 4: 
                Menu.removeStudent();
                break; 
            case 5: 
                Menu.enterMarks();
                break;
            case 6: 
                Menu.addCourse();
                break;
            case 7:  
                Menu.removeCourse();
                break;
            case 8: 
                Menu.courseDetail();
                break;
            case 9: 
                Menu.viewAll();
                break;
            default: 
                System.out.println("+++++++++++++++++++++++++=[ program exited successfully ]+++++++++++++++++++++++++++");
        }   
    } 
}
