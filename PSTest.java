
public class PSTest {

	public static void main(String[] args) {
		
		StudentsQueries queries=new StudentsQueries();
		queries.showAllStudents();
		queries.showStudentsByCourse(20109);
		queries.removeStudent(5151);
		queries.showAllStudents();
		queries.addStudent(5151, "Moshe", "Nachum", null, 5559999);
		queries.showAllStudents();
		

	}

}
