package com.java.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import javax.sql.DataSource;

@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public StudentControllerServlet() {
        super();
    }
    private StudentDBUtil studentDBUtil;
    @Resource(name="jdbc/web_student_tracker")
    private DataSource dataSource;
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			studentDBUtil = new StudentDBUtil(dataSource);
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String command = request.getParameter("command");
		
		if(command==null) command="LIST";
		switch (command) {
		case "ADD": 
			try {
				addStudent(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "LIST":
			listStudents(request, response);
			break;
		case "LOAD":
			try {
				loadStudent(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "UPDATE":
			try {
				updateStudent(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "DELETE":
			try {
				deleteStudent(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			listStudents(request, response);
		}
			
			

	
	}
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("studentId"));
		studentDBUtil.deleteStudent(id);
		listStudents(request, response);
	}
	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("studentId"));
		String fname = request.getParameter("firstName");
		String lname = request.getParameter("lastName");
		String email = request.getParameter("email");
		Student student = new Student(id, fname, lname,email);
		studentDBUtil.updateStudent(student);
		listStudents(request, response);
		
	}
	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String studID = request.getParameter("studentId");
		
		Student student = studentDBUtil.getStudentByID(studID);
		request.setAttribute("THE_STUDENT", student);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
		
		
	}
	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String fname = request.getParameter("firstName");
		String lname = request.getParameter("lastName");
		String email = request.getParameter("email");
		Student nStudent = new Student(fname, lname, email);
		studentDBUtil.addStudent(nStudent);
		listStudents(request, response);
		
	}
	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Student> list = new ArrayList<>();
		try {
			 list = studentDBUtil.getStudents();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("student_list", list);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}		
		
	}
	
	

