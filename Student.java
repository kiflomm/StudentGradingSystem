package studentgradingsystem;
import java.time.*;
import java.time.format.DateTimeFormatter;
public class Student {
    protected String id;
    protected String name; 
    private String gender;
    private int age; 
    
    public Student(String id, String name, String gender,String bd) {
        this.id = id;
        this.name = name; 
        this.gender = gender; 
        setAge(bd);
    }
    public Student(){
        this.id ="";
        this.name = "";
        this.gender = "";
        this.age = 0;
    }
    public String getStudentId() {
        return id;
    }

    public void setStudentId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    } 

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public int getStudentAge() {
        return age;
    }

    public void setAge(String dateStr) { 
        LocalDate birthDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
        LocalDate currentDate = LocalDate.now();
        int calculatedAge = currentDate.getYear() - birthDate.getYear(); 
        if (currentDate.getDayOfYear() < birthDate.getDayOfYear()) {
            calculatedAge--;  
        } 
        this.age = calculatedAge;
    }
    public void setAge(int age){
        this.age = age;
    }
    @Override
    public String toString(){
        return this.name  + ","+ this.id + "," + this.gender + ","+this.age+"\n";
    }
}