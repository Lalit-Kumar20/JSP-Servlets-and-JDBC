package com.java.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.jasper.tagplugins.jstl.core.If;

import com.mysql.cj.x.protobuf.MysqlxConnection.Close;

public class StudentDBUtil {
private DataSource dataSource;
public StudentDBUtil(DataSource d) {
	this.dataSource = d;
}

public List<Student> getStudents() throws Exception{
	List<Student> students = new ArrayList<>();
	Connection myConnection = null;
	java.sql.Statement myStatement = null;
	ResultSet resultSet = null;
	
	try {
		myConnection = dataSource.getConnection();
		myStatement = myConnection.createStatement();
		String sql= "select * from student";
		resultSet = myStatement.executeQuery(sql);
		while(resultSet.next()) {
			String emailString = resultSet.getString("email");
			String firstName = resultSet.getString("first_name");
			String lastName = resultSet.getString("last_name");
			int id = resultSet.getInt("id");
			students.add(new Student(id, firstName, lastName, emailString));
		}
	}
	catch(Exception exc) {
		exc.printStackTrace();
	}
	return students;
	
}

public void addStudent(Student nStudent) throws Exception{
	Connection myConnection = null;
	PreparedStatement myStatement = null;
	try {
		myConnection = dataSource.getConnection();
		String sql = "insert into student "+
		              "(first_name, last_name, email) "+
				       "values(?, ?, ?)";
		myStatement = myConnection.prepareStatement(sql);
		myStatement.setString(1, nStudent.getFirstName());
		myStatement.setString(2, nStudent.getLastName());
		myStatement.setString(3, nStudent.getEmail());
		myStatement.execute();
	}
	finally {
		myConnection.close();
		myStatement.close();
	}
	
}

public Student getStudentByID(String studID)  throws Exception{

    Student student = null;
    Connection myConnection = null;
	PreparedStatement myStatement = null;
	ResultSet rSet = null;
	int studentID;
	try {
		studentID = Integer.parseInt(studID);
		myConnection = dataSource.getConnection();
		String sql = "select * from student where id=?";
		myStatement = myConnection.prepareStatement(sql);
		myStatement.setInt(1, studentID);
		rSet = myStatement.executeQuery();
		if(rSet.next()) {
			String f = rSet.getString("first_name");
			String l = rSet.getString("last_name");
			String e = rSet.getString("email");
			
			student = new Student(studentID,f, l, e);

		}
		else {
			throw new Exception("Could not find student id");
		}
	}
	finally {
		myConnection.close();
		myStatement.close();
	}
	return student;
}

public void updateStudent(Student student) throws Exception{
	Connection myConnection = null;
	PreparedStatement myStatement = null;
	try {
		myConnection = dataSource.getConnection();
		String sql = "update student "+
		             "set first_name=?, last_name=?, email=?"+
				     "where id=?";
		myStatement = myConnection.prepareStatement(sql);
		myStatement.setString(1, student.getFirstName());
		myStatement.setString(2, student.getLastName());
		myStatement.setString(3, student.getEmail());
		myStatement.setInt(4, student.id);
		myStatement.execute();
		
		}
	finally {
		myConnection.close();
		myStatement.close();
	}
	
	
}


public void deleteStudent(int id) throws Exception{
	Connection myConnection = null;
	PreparedStatement myStatement = null;
	try {
		myConnection = dataSource.getConnection();
		String sql = "delete from student "+
		             "where id=?";
		myStatement = myConnection.prepareStatement(sql);
		myStatement.setInt(1, id);
		myStatement.execute();
		
		}
	finally {
		myConnection.close();
		myStatement.close();
	}
}


}
