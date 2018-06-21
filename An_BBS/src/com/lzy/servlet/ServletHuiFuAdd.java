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
 * Servlet implementation class ServletHuiFuAdd
 */
@WebServlet("/ServletHuiFuAdd")
public class ServletHuiFuAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletHuiFuAdd() {
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
        String tieziid= request.getParameter("tieziid");
        String userid= request.getParameter("userid");
        IndexAction ia=new IndexAction();
        int ez=0;
        ez=ia.huifuadd(content, tieziid,userid);
        if(ez==0){
        	String c="addhuifu error!";
        	String a="[{\"tip\":\""+ez+"\",\"tips\":\""+c+"\"}]";
        	
        	out.write(a);
        }else{
        	String b="addhuifu succesful!";
        	String d="[{\"tip\":\""+ez+"\",\"tips\":\""+b+"\"}]";
        	out.write(d);
        }
        out.flush();
		out.close();
	}

}
