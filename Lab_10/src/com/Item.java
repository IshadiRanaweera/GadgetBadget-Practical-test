package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Item {
	
	public Connection connect()
	{
	 Connection con = null;

	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");
	 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gadgetbadget", "root", "");
	 
	 //For testing
	// System.out.print("Successfully connected");
	 }
	 catch(Exception e)
	 {
	 e.printStackTrace();
	 }

	 return con;
	}
	
	public String readProducts()
	{ 
	 String output = ""; 
	try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database for reading."; 
	 } 
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>Product Code</th>" 
	 + "<th>Product Name</th><th>Product Price</th>"
	 + "<th>Product Description</th>" 
	 + "<th>Product Group</th>"
	 + "<th>Update</th><th>Remove</th></tr>"; 
	 String query = "select * from products"; 
	 Statement stmt = con.createStatement(); 
	 ResultSet rs = stmt.executeQuery(query); 
	 // iterate through the rows in the result set
	 while (rs.next()) 
	 { 
	 String productID = Integer.toString(rs.getInt("productID")); 
	 String productCode = rs.getString("productCode"); 
	 String productName = rs.getString("productName"); 
	 String productPrice = Float.toString(rs.getFloat("productPrice")); 
	 String productDes = rs.getString("productDes"); 
	 String productGroup = rs.getString("productGroup");
	// Add into the html table
	 output += "<tr><td>" + productID + "</td>"; 
	 output += "<td>" + productName + "</td>"; 
	 output += "<td>" +productPrice  + "</td>"; 
	 output += "<td>" + productDes + "</td>"; 
	 output += "<td>" + productGroup + "</td>";
	// buttons
	output += "<td><input name='btnUpdate' type='button' value='Update' "
	+ "class='btnUpdate btn btn-secondary' data-itemid='" +productID  + "'></td>"
	+ "<td><input name='btnRemove' type='button' value='Remove' "
	+ "class='btnRemove btn btn-danger' data-itemid='" + productID + "'></td></tr>"; 
	 } 
	 con.close(); 
	 // Complete the html table
	 output += "</table>"; 
	 } 
	catch (Exception e) 
	 { 
	 output = "Error while reading the products."; 
	 System.err.println(e.getMessage()); 
	 } 
	return output; 
	}

	public String insertProducts(String code, String name, String price, String des , String group) 
			 { 
			 String output = ""; 
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting  to the database for inserting."; 
			 } 
			 // create a prepared statement
			 String query = " insert into products(`productID`,`productCode`,`productName`,`productPrice`,`productDes`,`productGroup`)"	+ " values (?, ?, ?, ?, ?, ?)";
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, code); 
			 preparedStmt.setString(3, name); 
			 preparedStmt.setFloat(4, Float.parseFloat(price)); 
			 preparedStmt.setString(5, des); 
			 preparedStmt.setString(6, group);
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newItems = readProducts(); 
			 output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
			 output = "{\"status\":\"error\", \"data\": \"Error while inserting the products.\"}"; 
			 System.err.println(e.getMessage()); 
			 } 
			 return output; 
			 } 
	
			public String updateProducts(String ID, String code, String name,String price, String des , String group) 
			 { 
			 String output = ""; 
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for updating."; 
			 } 
			 // create a prepared statement
			 String query = "UPDATE products SET productCode=?,productName=?,productPrice=?,productDes=?,productGroup=? WHERE productID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setString(1, code); 
			 preparedStmt.setString(2, name); 
			 preparedStmt.setFloat(3, Float.parseFloat(price)); 
			 preparedStmt.setString(4, des); 
			 preparedStmt.setString(5, group);
			 preparedStmt.setInt(6, Integer.parseInt(ID)); 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newItems = readProducts(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
			 output = "{\"status\":\"error\", \"data\": \"Error while updating the products.\"}"; 
			 System.err.println(e.getMessage()); 
			 } 
			 return output; 
			 } 
			public String deleteProduct(String productID) 
			 { 
			 String output = ""; 
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for deleting."; 
			 } 
			 // create a prepared statement
			 String query = "delete from products where productID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(productID)); 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newItems = readProducts(); 
			 output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
			 output = "{\"status\":\"error\", \"data\": \"Error while deleting the product.\"}"; 
			 System.err.println(e.getMessage()); 
			 } 
			 return output; 
			 } 


}
