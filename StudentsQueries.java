import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class StudentsQueries {

	private static final String DATABASE_URL="jdbc:derby:C:\\Users\\yehudit\\Documents\\מדעי המחשב - האוניברסיטה הפתוחה\\סדנה בתכנות מתקדם בשפת ג'אווה 20503\\JDBC Lecture\\students";
	private static final String USERNAME="kerido";
	private static final String PASSWORD="kerido";
	
	private Connection connection;
	private PreparedStatement selectAllStudents;
	private PreparedStatement selectByCourse;;
	private PreparedStatement insertNewStudent;
	private PreparedStatement deleteByID;
	
	
	public StudentsQueries()
	{
		try
		{
			connection=DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
			//create the first query
			selectAllStudents=connection.prepareStatement("SELECT * FROM students");
			//create second query
			selectByCourse=connection.prepareStatement("SELECT firstName, lastName, numberID FROM students INNER JOIN studentsGrades ON students.studentID=studentsGrades.studentID  WHERE numberID=?"); 
			//create third query
			insertNewStudent=connection.prepareStatement("INSERT INTO students "
					+ "(studentID, firstName, lastName, address) "
					+ "VALUES (?, ?, ?, ?)");
			//create forth query
			deleteByID=connection.prepareStatement("DELETE FROM students WHERE studentID=?");
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
			System.exit(1);
		}
	}
	
	//this method execute first query and presents her results
	public void showAllStudents()
	{
		ResultSet resultSet=null;
		try
		{
			resultSet=selectAllStudents.executeQuery();
			ResultSetMetaData metaData=resultSet.getMetaData();
			int numberOfColumns=metaData.getColumnCount();
			System.out.println("Students table of Students Database:\n");
			
			// display resultSet header
	         for (int i = 1; i <= numberOfColumns; i++)
	            System.out.printf("%-8s\t", metaData.getColumnName(i));
	         System.out.println();
	         
	         // display each row
	         while (resultSet.next()) 
	         {
	            for (int i = 1; i <= numberOfColumns; i++)
	               System.out.printf("%8s\t", resultSet.getObject(i));
	            System.out.println();
	         }
	         System.out.println();
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		finally
		{
			try
			{
				resultSet.close();
			}
			catch (SQLException sqlException)
			{
				sqlException.printStackTrace();
				close();
			}
		}
	}
	
	//this method execute second query and presents her results
	public void showStudentsByCourse(int number)
	{
		ResultSet resultSet=null;
		try
		{
			selectByCourse.setInt(1, number);
			resultSet=selectByCourse.executeQuery();
			ResultSetMetaData metaData=resultSet.getMetaData();
			int numberOfColumns=metaData.getColumnCount();
			System.out.println("Students which participants in course :" + number + "\n");
			
			// display resultSet header
	         for (int i = 1; i <= numberOfColumns; i++)
	            System.out.printf("%-8s\t", metaData.getColumnName(i));
	         System.out.println();
	         
	         // display each row
	         while (resultSet.next()) 
	         {
	            for (int i = 1; i <= numberOfColumns; i++)
	               System.out.printf("%8s\t", resultSet.getObject(i));
	            System.out.println();
	         }
	         System.out.println();
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		finally
		{
			try
			{
				resultSet.close();
			}
			catch (SQLException sqlException)
			{
				sqlException.printStackTrace();
				close();
			}
		}
	}
	
	//this method execute third query and presents her results
	public int addStudent(int ID, String privateName, String lastName, String address, int phone)
	{
		int result=0; 
		try
		{
			insertNewStudent.setInt(1, ID);
			insertNewStudent.setString(2, privateName);
			insertNewStudent.setString(3, lastName);
			insertNewStudent.setString(4, address);
			
			result=insertNewStudent.executeUpdate();
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
			close();
		}
		return result;
	}
	//this method execute third query and presents her results
	public int removeStudent (int ID)
	{
		int result=0; 
		try
		{
			deleteByID.setInt(1, ID);
			
			result=deleteByID.executeUpdate();
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
			close();
		}
		return result;
	}
	//close the database connection
			public void close()
			{
				try
				{
					connection.close();
				}
				catch (SQLException sqlException)
				{
					sqlException.printStackTrace();
				}
			}
}

