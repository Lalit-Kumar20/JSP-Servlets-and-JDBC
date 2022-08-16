package com.java.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.*;
import com.mysql.cj.xdevapi.Result;
import com.mysql.cj.xdevapi.RowResult;
import com.mysql.cj.xdevapi.SelectStatement;
import com.mysql.cj.xdevapi.Statement;

@WebServlet("/TestDatabase")
public class TestDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	public TestDatabase() {
        super();
    }
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
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
				out.println(emailString);
			}
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		
	
	
	}

}
