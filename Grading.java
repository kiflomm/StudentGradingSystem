package studentgradingsystem;
import java.util.Scanner;
public class Grading {
    private String letterGrade;
    private static String[] letterGrades = {"A+", "A","A-", "B+", "B", "B-", "C+", "C", "C-", "D","F"};
    private static double[] gradePoints = {4.0, 4.0, 3.75, 3.5, 3, 2.75, 2.5, 2, 1.75, 1.0, 0.0};
    
    public static String calculateLetterGrade(float mark) {
        if (mark >= 90) {
            return "A+";
        } else if (mark >= 85) {
            return "A";
        } else if (mark >= 80) {
            return "A-";
        } else if (mark >= 75) {
            return "B+";
        } else if (mark >= 70) {
            return "B";
        } else if (mark >= 65) {
            return "B-";
        } else if (mark >= 60) {
            return "C+";
        } else if (mark >= 55){
            return "C";
        }else if (mark >= 50){
            return "C-";
        }else if (mark >= 45){
            return "D";
        }else {
            return "F";
        }
    }  
    
    public static double getGradePoints(String letter, int creditHour) {
        double gradePoint = 0.0; 
        for (int i = 0; i < letterGrades.length; i++) {
            if (letter.equals(letterGrades[i])) {
                gradePoint = gradePoints[i];
                break;
            }
        }

        return gradePoint * creditHour;
    } 
} 
