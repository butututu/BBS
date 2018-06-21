package com.lzy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lzy.action.IndexAction;

/**
 * Servlet implementation class ServletTieZiAdd
 */
@WebServlet("/ServletTieZiAdd")
public class ServletTieZiAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletTieZiAdd() {
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
    	String content= request.getParameter("content");
        String title= request.getParameter("title");
        String userid= request.getParameter("userid");
        String bankuaiid= request.getParameter("bankuaiid");
        System.out.println("content:"+content+"\n"+"title:"+title+"\n"+"userid:"+userid+"\n"+"bankuaiid:"+bankuaiid+"\n");
        IndexAction ia=new IndexAction();
        int ez=0;
        ez=ia.tieziadd(content, title,userid,bankuaiid);
        if(ez==0){
        	String c="addtiezi error!";
        	String a="[{\"tip\":\""+ez+"\",\"tips\":\""+c+"\"}]";
        	
        	out.write(a);
        }else{
        	String b="addtiezi succesful!";
        	String d="[{\"tip\":\""+ez+"\",\"tips\":\""+b+"\"}]";
        	out.write(d);
        }
        out.flush();
		out.close();
    }
	
	

}
