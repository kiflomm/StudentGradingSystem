package studentgradingsystem;

import java.io.*;
import java.util.Scanner;

public class Menu {

    public static void addStudent() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the student name: ");
        String studName = input.nextLine().toLowerCase();
        BufferedReader readStud = new BufferedReader(new FileReader("student_info.txt"));
        String studId;
        boolean repeated = false;
        do {
            if (!repeated) {
                System.out.print("Enter student Id in the format (UGR/dddddd/dd): ");
            } else {
                System.out.print("Unacceptable Id format! \n please enter in the format(UGR/dddddd/dd): ");
            }
            studId = input.nextLine();
            repeated = true;
        } while (!(studId.matches("(?i)^ugr/\\d{6}/\\d{2}$")));
        String current;
        while ((current = readStud.readLine()) != null) {
            String[] attrs = current.split(",");
            if (attrs[1].toLowerCase().equals(studId.toLowerCase())) {
                System.out.println("Registration faild!\nA user with the provided Id [ " + studId + " ] already exists\nplease check it latter ");
                readStud.close();
                System.out.println("Do you want to go back to the main menu: ( YES = 1 NO -> any key )");
                String yes = input.nextLine();
                if (yes.equals("1")) {
                    StudentGradingSystem.main(new String[1]);
                } else {
                    System.out.println(" Successfully exited! ");
                }
                return;
            }
        }
        readStud.close();
        String gender = "";
        int itrations = 0;
        while (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")) {
            if (itrations == 0) {
                System.out.print("Enter gender (male/female): ");
            } else {
                System.out.print("Invalid Gender \nplease enter male or female only :  ");
            }
            gender = input.nextLine();
            itrations++;
        }

        boolean validDate = false;
        String birthDate = "";

        while (!validDate) {
            System.out.print("Enter your birth date (YYYY-MM-DD): ");
            birthDate = input.nextLine();

            if (birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                validDate = true;
            } else {
                System.out.print("Invalid date format. Please use the format YYYY-MM-DD.");
            }
        }

        BufferedReader reader = new BufferedReader(new FileReader("course_info.txt"));
        String line;
        String results = "";
        while ((line = reader.readLine()) != null) {
            String[] attr = line.split(",");
            if (!results.equals("")) {
                results += "," + attr[1] + " 0 F 0.0";
            } else {
                results = attr[1] + " 0 F 0.0";
            }
        }
        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter("results_info.txt", true));
        writer.write(results + "\n");
        writer.close();

        Student stud = new Student(studId, studName, gender, birthDate);
        BufferedWriter register = new BufferedWriter(new FileWriter("student_info.txt", true));
        String stud_info = stud.toString();
        register.write(stud_info);
        register.close();
        System.out.println("A student whose name is [ " + studName + " ] is successfuly registered.");
        System.out.println("Do you want to go back to the main menu: ( YES = 1 NO -> any key )");
        String yes = input.nextLine();
        if (yes.equals("1")) {
            StudentGradingSystem.main(new String[1]);
        } else {
            System.out.println(" Successfully exited! ");
        }
    }

    public static void editStudent() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the ID number of the student you want to edit: ");
        String studId = input.nextLine();
        File fileStud = new File("student_info.txt");
        File fileTemp = new File("temp.txt");

        BufferedReader readStuds = new BufferedReader(new FileReader("student_info.txt"));
        BufferedWriter writeTemp = new BufferedWriter(new FileWriter("temp.txt", true));
        boolean found = false;
        String line;
        while ((line = readStuds.readLine()) != null) {
            String[] atoms = line.split(",");
            if (atoms[1].toLowerCase().equals(studId.toLowerCase())) {
                found = true;
                System.out.print("Enter new student full name: ");
                String newName = input.nextLine();

                String gender = "";
                int itrations = 0;
                while (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")) {
                    if (itrations == 0) {
                        System.out.print("Enter gender (male/female): ");
                    } else {
                        System.out.print("Invalid Gender \nplease enter male or female only :  ");
                    }
                    gender = input.nextLine();
                    itrations++;
                }

                boolean validDate = false;
                String birthDate = "";
                while (!validDate) {
                    System.out.print("Enter your birth date (YYYY-MM-DD): ");
                    birthDate = input.nextLine();
                    if (birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        validDate = true;
                    } else {
                        System.out.print("Invalid date format. Please use the format YYYY-MM-DD.");
                    }
                }
                String tempStr = new Student(studId, newName, gender, birthDate).toString();
                writeTemp.write(tempStr);
            } else {
                writeTemp.write(line + "\n");
            }
        }
        readStuds.close();
        writeTemp.close();
        fileStud.delete();
        fileTemp.renameTo(fileStud);

        if (found) {
            System.out.println("A student's profile with Id number [ " + studId + " ] is modified.");
        } else {
            System.out.println("A student with Id number [ " + studId + " ] is not found.");
        }
        System.out.println("Do you want to go back to the main menu: ( YES = 1 NO -> any key )");
        String yes = input.nextLine();
        if (yes.equals("1")) {
            StudentGradingSystem.main(new String[1]);
        } else {
            System.out.println(" Successfully exited! ");
        }
    }

    public static void searchStudent() throws IOException {
        Scanner input = new Scanner(System.in);
        BufferedReader readStud = new BufferedReader(new FileReader("student_info.txt"));
        BufferedReader readResults = new BufferedReader(new FileReader("results_info.txt"));
        BufferedReader readCourses = new BufferedReader(new FileReader("course_info.txt"));
        String lineCourse;
        int chrs = 0;
        while ((lineCourse = readCourses.readLine()) != null) {
            String[] attrs = lineCourse.split(",");
            chrs += Integer.parseInt(attrs[2]);
        }
        System.out.print("Enter a student Id to search for a student: ");
        String studId = input.nextLine();
        String line;
        Student studentInfo = new Student();
        int lineCounter = 0;
        boolean found = false;
        while ((line = readStud.readLine()) != null) {
            String[] atoms = line.split(",");
            if (studId.toLowerCase().equals(atoms[1].toLowerCase())) {
                found = true;
                studentInfo.setName(atoms[0]);
                studentInfo.setStudentId(atoms[1]);
                studentInfo.setGender(atoms[2]);
                studentInfo.setAge(Integer.parseInt(atoms[3]));
                break;
            }
            lineCounter++;
        }
        readStud.close();
        if (found) {
            System.out.println("+++++++++++++++++++++++++++++++Student details++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("Full Name: " + studentInfo.getName());
            System.out.println("Id number: " + studentInfo.getStudentId());
            System.out.println("Gender: " + studentInfo.getGender());
            System.out.println("Age: " + studentInfo.getStudentAge());
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("________________________________[ Grade Report ]____________________________________________");
            System.out.println("________________________________________________________________________________________");
            System.out.println("course code \t mark \t letter Grade \t Grade points");
            System.out.println("________________________________________________________________________________________");

            String lineResult;
            double totalGP = 0.0;
            for (int i = 0; (lineResult = readResults.readLine()) != null; i++) {
                if (i == lineCounter) {
                    String[] courses = lineResult.split(",");
                    for (int j = 0; j < courses.length; j++) {
                        String[] attrs = courses[j].split(" ");
                        for (int l = 0; l < attrs.length; l++) {
                            if (l == 3) {
                                totalGP += Double.parseDouble(attrs[l]);
                            }
                            System.out.print(attrs[l] + "\t");
                        }
                        System.out.println();
                    }
                    break;
                }
            }
            System.out.println("Total Grade points is: " + totalGP);
            System.out.println("Total credit hours is: " + chrs);
            System.out.println("GPA: " + (totalGP / chrs));
        } else {
            System.out.println("A student with the id number [ " + studId + " ] doesnt exist");
        }
        readResults.close();
        System.out.println("Do you want to go back to the main menu: ( YES = 1 NO -> any key )");
        String yes = input.nextLine();
        if (yes.equals("1")) {
            StudentGradingSystem.main(new String[1]);
        } else {
            System.out.println(" Successfully exited! ");
        }
    }

    public static void removeStudent() throws IOException {
        Scanner input = new Scanner(System.in);
        File fileStud = new File("student_info.txt");
        File fileResult = new File("results_info.txt");
        File fileTempS = new File("tempS.txt");
        File fileTempR = new File("tempR.txt");
        BufferedReader readStud = new BufferedReader(new FileReader("student_info.txt"));
        BufferedReader readResult = new BufferedReader(new FileReader("results_info.txt"));
        BufferedWriter writeTempS = new BufferedWriter(new FileWriter("tempS.txt"));
        BufferedWriter writeTempR = new BufferedWriter(new FileWriter("tempR.txt"));

        System.out.print("Enter a student Id to remove the student:  ");
        String studId = input.nextLine();

        String lineStud;
        String lineResult;
        String name = "";
        boolean found = false;
        while ((lineStud = readStud.readLine()) != null && (lineResult = readResult.readLine()) != null) {
            String[] atoms = lineStud.split(",");
            if (!studId.equals(atoms[1])) {
                writeTempS.write(lineStud + "\n");
                writeTempR.write(lineResult + "\n");
            } else {
                found = true;
                name = atoms[0];
            }
        }
        writeTempS.close();
        writeTempR.close();
        readStud.close();
        readResult.close();
        fileStud.delete();
        fileResult.delete();
        fileTempS.renameTo(fileStud);
        fileTempR.renameTo(fileResult);

        if (found) {
            System.out.println("The student called [ " + name + " ] with id number [ " + studId + " ] is removed from the list.");
        } else {
            System.out.println("A student with an Id number [ " + studId + " ] doesnt exist");
        }

        System.out.println("Do you want to go back to the main menu: ( YES = 1 NO -> any key )");
        String yes = input.nextLine();
        if (yes.equals("1")) {
            StudentGradingSystem.main(new String[1]);
        } else {
            System.out.println(" Successfully exited! ");
        }
    }

    public static void addCourse() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter course title: ");
        String courseTitle = input.nextLine();
        System.out.print("Enter course code: ");
        String courseCode = input.nextLine();
        boolean found = false;
        BufferedReader readCourse = new BufferedReader(new FileReader("course_info.txt"));
        String courseLine;
        while ((courseLine = readCourse.readLine()) != null) {
            String[] attr = courseLine.split(",");
            if (attr[1].toLowerCase().equals(courseCode.toLowerCase())) {
                found = true;
            }
        }
        if (found) {
            System.out.println("The course code is not available Please Try Entering again");
            addCourse();
        }
        readCourse.close();
        int creditHour = 0;
        while (creditHour <= 0) {
            try {
                System.out.print("Enter valid course credit hours : ");
                creditHour = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                System.out.println(" Your input is invalid! error occured. ");
            }
        }

        System.out.print("Enter instructor's name: ");
        String instructor = input.nextLine();
        Course newCourse = new Course(courseCode, courseTitle, creditHour, instructor);
        BufferedWriter writer = new BufferedWriter(new FileWriter("course_info.txt", true));
        String course_info = newCourse.toString();
        writer.write(course_info);
        writer.close();
        System.out.println("A course has been successfully added");
        File tempFile = new File("temp.txt");
        File resultsFile = new File("results_info.txt");
        BufferedWriter writerTemp = new BufferedWriter(new FileWriter("temp.txt", true));
        BufferedReader readResult = new BufferedReader(new FileReader("results_info.txt"));
        String lineResult;
        while ((lineResult = readResult.readLine()) != null) {
            writerTemp.write(lineResult + "," + courseCode + " 0 F 0.0\n");
        }
        readResult.close();
        writerTemp.close();
        resultsFile.delete();
        tempFile.renameTo(resultsFile);
        System.out.println("Do you want to go back to the main menu: ( YES = 1 NO -> any key )");
        String yes = input.nextLine();
        if (yes.equals("1")) {
            StudentGradingSystem.main(new String[1]);
        } else {
            System.out.println(" Successfully exited! ");
        }
    }

    public static void removeCourse() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter course code: ");
        String courseCode = input.nextLine();
        boolean found = false;
        BufferedReader readCourse0 = new BufferedReader(new FileReader("course_info.txt"));
        String courseLine;
        while ((courseLine = readCourse0.readLine()) != null) {
            String[] attr = courseLine.split(",");
            if (attr[1].toLowerCase().equals(courseCode.toLowerCase())) {
                found = true;
            }
        }
        if (!found) {
            System.out.println("The course code does not exist. Try again!");
            removeCourse();
        }
        readCourse0.close();

        BufferedReader readCourse = new BufferedReader(new FileReader("course_info.txt"));
        File tempFile = new File("temp.txt");
        File courseInfo = new File("course_info.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt", true));
        String line;
        String removedCourse = "";
        while ((line = readCourse.readLine()) != null) {
            String[] attr = line.split(",");
            if (!courseCode.toLowerCase().equals(attr[1].toLowerCase())) {
                writer.write(line + " \n");
            } else {
                removedCourse = attr[0];
            }
        }
        writer.close();
        readCourse.close();
        if (courseInfo.delete()) {
            tempFile.renameTo(courseInfo);
            System.out.println(removedCourse + " has been removed successfully ");
        }

        System.out.println("Do you want to go back to the main menu: ( YES = 1 NO -> any key )");
        String yes = input.nextLine();
        if (yes.equals("1")) {
            StudentGradingSystem.main(new String[1]);
        } else {
            System.out.println(" Successfully exited! ");
        }
    }

    public static void enterMarks() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("1.To Enter marks by student Id: ");
        System.out.println("2.To enter marks by course: ");
        int choice = input.nextInt();
        input.nextLine();
        BufferedReader readStud = new BufferedReader(new FileReader("student_info.txt"));
        BufferedReader readCourse = new BufferedReader(new FileReader("course_info.txt"));
        BufferedReader readResult = new BufferedReader(new FileReader("results_info.txt"));
        File tempFile = new File("temp.txt");
        File results = new File("results_info.txt");
        BufferedWriter writeTemp = new BufferedWriter(new FileWriter("temp.txt", true));
        String studId;
        String courseCode;

        if (choice == 1) {
            System.out.println("Enter the student Id: ");
            studId = input.nextLine();
            String line;
            int lineNumber = 0;
            int total = 0;
            while ((line = readStud.readLine()) != null) {
                String[] attr = line.split(",");
                if (attr[1].equals(studId.toLowerCase().trim())) {
                    lineNumber = total;
                }
                total++;
            }
            readStud.close();
            for (int i = 0; i < total; i++) {
                if (i == lineNumber) {
                    String[] targetLine = (readResult.readLine()).split(",");
                    String courseLine;
                    int j = 0;
                    while ((courseLine = readCourse.readLine()) != null) {
                        String[] attr = courseLine.split(",");
                        System.out.println("Enter the mark for " + attr[0]);
                        float mark = input.nextFloat();
                        input.nextLine();
                        String letter = Grading.calculateLetterGrade(mark);
                        int chr = Integer.parseInt(attr[2]);
                        double points = Grading.getGradePoints(letter, chr);
                        targetLine[j] = attr[1] + " " + mark + " " + letter + " " + points;
                        j++;
                    }
                    String toTemp = String.join(",", targetLine);
                    writeTemp.write(toTemp + "\n");
                } else {
                    writeTemp.write(readResult.readLine() + "\n");
                }
            }
        } else if (choice == 2) {
            System.out.println("Enter the course code: ");
            courseCode = input.nextLine();
            String theCourse = "";
            String chrsString = "";
            String lineCourse;
            while ((lineCourse = readCourse.readLine()) != null) {
                String[] attrCourse = lineCourse.split(",");
                chrsString += attrCourse[2] + " ";
                if (attrCourse[1].toLowerCase().equals(courseCode.toLowerCase())) {
                    theCourse = attrCourse[0];
                }
            }
            String[] chrs = chrsString.split(" ");
            String lineStud;
            String lineResult;
            int total = 0;
            String str;
            BufferedReader counter = new BufferedReader(new FileReader("student_info.txt"));
            while ((str = counter.readLine()) != null) {
                total++;
            }
            String[] names = new String[total];

            int i = 0;
            while ((lineStud = readStud.readLine()) != null) {
                String[] attrStud = lineStud.split(",");
                names[i] = attrStud[0];
                i++;
            }
            int j = 0;
            while ((lineResult = readResult.readLine()) != null) {
                String[] attrResult = lineResult.split(",");
                System.out.println("Enter the mark of " + names[j] + " for the course " + theCourse);
                int mark = input.nextInt();
                input.nextLine();
                for (int ele = 0; ele < attrResult.length; ele++) {
                    String[] atoms = attrResult[ele].split(" ");
                    if (courseCode.toLowerCase().equals(atoms[0].toLowerCase())) {
                        String letter = Grading.calculateLetterGrade(mark);
                        int chr = Integer.parseInt(chrs[j]);
                        double points = Grading.getGradePoints(letter, chr);
                        attrResult[ele] = atoms[0] + " " + mark + " " + letter + " " + points;
                    }
                }
                String toTemp = String.join(",", attrResult);
                writeTemp.write(toTemp + "\n");
                j++;
            }
        }

        readResult.close();
        writeTemp.close();
        results.delete();
        tempFile.renameTo(results);
        System.out.println("++++++++++++++++++++++++++++marks entered correctly++++++++++++++++++++++++++");
        System.out.println("Do you want to go back to the main menu: ( YES = 1 NO -> any key )");
        String yes = input.nextLine();
        if (yes.equals("1")) {
            StudentGradingSystem.main(new String[1]);
        } else {
            System.out.println(" Successfully exited! ");
        }
        
    }

    public static void courseDetail() throws IOException {
        
        Scanner input = new Scanner(System.in);
        BufferedReader readCourse = new BufferedReader(new FileReader("course_info.txt"));
        System.out.println("Enter the course code of which you want to see the detail: ");
        String courseCode = input.nextLine();
        String lineCourse;
        while ((lineCourse = readCourse.readLine()) != null) {
            String[] atoms = lineCourse.split(",");
            if (courseCode.toLowerCase().equals(atoms[1].toLowerCase())) {
                System.out.println("\t\tCourse_Details_Below");
                System.out.println(" Course Title: " + atoms[0]);
                System.out.println(" Course Code: " + atoms[1]);
                System.out.println(" Course credit hour: " + atoms[2]);
                System.out.println(" Instructors name: " + atoms[3]);
                break;
            }
        }
        
        System.out.println("Do you want to go back to the main menu: ( YES = 1 NO -> any key )");
        String yes = input.nextLine();
        if (yes.equals("1")) {
            StudentGradingSystem.main(new String[1]);
        } else {
            System.out.println(" Successfully exited! ");
        }
    }

    public static void viewAll() throws IOException {
        System.out.println("\t\t\t Students info");
        BufferedReader readStudent = new BufferedReader(new FileReader("student_info.txt"));
//        BufferedReader readResult = new BufferedReader(new FileReader("results_info.txt"));
        String line;
        while ((line = readStudent.readLine()) != null) {
            String[] atoms = line.split(",");
            String studId = atoms[1];
            searchStudent(studId);
            System.out.println();
        }
        Scanner input = new Scanner(System.in);
        System.out.println("Do you want to go back to the main menu: ( YES = 1 NO -> any key )");
        String yes = input.nextLine();
        if (yes.equals("1")) {
            StudentGradingSystem.main(new String[1]);
        } else {
            System.out.println(" Successfully exited! ");
        }
    }

    public static void searchStudent(String studId) throws IOException {
        BufferedReader readCourses = new BufferedReader(new FileReader("course_info.txt"));
        String lineCourse;
        int chrs = 0;
        while ((lineCourse = readCourses.readLine()) != null) {
            String[] attrs = lineCourse.split(",");
            chrs += Integer.parseInt(attrs[2]);
        }
        BufferedReader readStud = new BufferedReader(new FileReader("student_info.txt"));
        BufferedReader readResults = new BufferedReader(new FileReader("results_info.txt"));
        String line;
        Student studentInfo = new Student();
        int lineCounter = 0;
        while ((line = readStud.readLine()) != null) {
            String[] atoms = line.split(",");
            if (studId.toLowerCase().equals(atoms[1].toLowerCase())) {
                studentInfo.setName(atoms[0]);
                studentInfo.setStudentId(atoms[1]);
                studentInfo.setGender(atoms[2]);
                studentInfo.setAge(Integer.parseInt(atoms[3]));
                break;
            }
            lineCounter++;
        }
        readStud.close();
        System.out.println("+++++++++++++++++++++++++++++++Student details++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Full Name: " + studentInfo.getName());
        System.out.println("Id number: " + studentInfo.getStudentId());
        System.out.println("Gender: " + studentInfo.getGender());
        System.out.println("Age: " + studentInfo.getStudentAge());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("________________________________Grade Report____________________________________________");
        System.out.println("________________________________________________________________________________________");
        System.out.println("course code \t mark \t letter Grade \t Grade points");
        System.out.println("________________________________________________________________________________________");

        String lineResult;
            double totalGP = 0.0;
            for (int i = 0; (lineResult = readResults.readLine()) != null; i++) {
                if (i == lineCounter) {
                    String[] courses = lineResult.split(",");
                    for (int j = 0; j < courses.length; j++) {
                        String[] attrs = courses[j].split(" ");
                        for (int l = 0; l < attrs.length; l++) {
                            if (l == 3) {
                                totalGP += Double.parseDouble(attrs[l]);
                            }
                            System.out.print(attrs[l] + "\t");
                        }
                        System.out.println();
                    }
                    break;
                }
            }
            System.out.println("Total Grade points is: " + totalGP);
            System.out.println("Total credit hours is: " + chrs);
            System.out.println("GPA: " + (totalGP / chrs));
        readResults.close();
    }
}
