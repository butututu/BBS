package com.lzy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.lzy.action.*;

/**
 * Servlet implementation class ServletResign
 */
@WebServlet("/ServletResign")
public class ServletResign extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletResign() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		PrintWriter out=response.getWriter();
    	String rename= request.getParameter("username");
        String repwd= request.getParameter("password");
        String rephone= request.getParameter("phone");
        String renikoname= request.getParameter("nickname");
        IndexAction resign=new IndexAction();
        int ez=0;
        ez=resign.register(rename, repwd,rephone,renikoname);
        if(ez==0){
        	String c="resign error!";
        	String a="[{\"tip\":\""+ez+"\",\"tips\":\""+c+"\"}]";
        	out.write(a);
        }
        else if(ez==-1){
        	String c="resign succesful!";
        	String a="[{\"tip\":\""+ez+"\",\"tips\":\""+c+"\"}]";
        	out.write(a);
        }
        else if(ez==1){
        	String c="the same name!";
        	String a="[{\"tip\":\""+ez+"\",\"tips\":\""+c+"\"}]";
        	out.write(a);
        }
        else if(ez==2){
        	String c="the same nikoname!";
        	String a="[{\"tip\":\""+ez+"\",\"tips\":\""+c+"\"}]";
        	out.write(a);
        }
        else if(ez==3){
        	String c="the same name and nikoname!";
        	String a="[{\"tip\":\""+ez+"\",\"tips\":\""+c+"\"}]";
        	out.write(a);
        }
        else {
        	String c="error!";
        	String a="[{\"tip\":\""+ez+"\",\"tips\":\""+c+"\"}]";
        	out.write(a);
        }
        out.flush();
		out.close();
	}

}
