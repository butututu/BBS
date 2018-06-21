package com.lzy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.lzy.action.*;
@WebServlet("/ServletForPOSTMethod")
public class ServletForPOSTMethod extends HttpServlet {
    private static final long serialVersionUID = 1L;       
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter out=response.getWriter();
    	String name="user2";
    	String pwd="lzy123344";
    	 name= request.getParameter("username");
         pwd= request.getParameter("password");
        IndexAction ia=new IndexAction();
        JsonArray ez=new JsonArray();
        ez=ia.login(name, pwd);
        if(ez.isJsonNull()){
        	
        }else{
        	out.print(ez);
        	 System.out.println(ez);  
        }
        out.flush();
		out.close();
    }
}