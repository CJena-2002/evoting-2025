<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" %>

<%!
    Connection con=null;
    Statement st=null;
%>

<%
    try{
    	Class.forName("com.mysql.cj.jdbc.Driver");
        //database_name --> evoting
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/evoting", "root", "mysql");
        st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
       
    }catch(Exception e){
        out.print("Error in DB Connection : "+e);
    }
%>
