package com.lzy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.lzy.action.IndexAction;

/**
 * Servlet implementation class ServeletGetTuiJianTieZi
 */
@WebServlet("/ServeletGetTuiJianTieZi")
public class ServeletGetTuiJianTieZi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServeletGetTuiJianTieZi() {
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
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out=response.getWriter();
    	//String tuijian= request.getParameter("tuijian");
		
        IndexAction ia=new IndexAction();
        JsonArray ez=new JsonArray();
        ez=ia.gettuijiantiezi();
        if(ez.isJsonNull()){
        	
        }else{
        	out.print(ez);
        	 System.out.println(ez);  
        }
        out.flush();
		out.close();
	}

}
