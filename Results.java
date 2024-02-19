 package studentgradingsystem; 
public class Results extends Student {
    private String[] courseNames;
    private int[] Scores;
    private String course;
    public Results(Student s){
        this.name = s.getName();
        this.id = s.getStudentId();
    }
   public void setScore(int score){
       
   }
}
