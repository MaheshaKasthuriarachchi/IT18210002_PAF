package com;

import java.sql.*;

public class Researcher {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/researcher_management", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String readResearchers() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Researcher Fname</th><th>Researcher Lname</th><th>Researcher Email</th>"
					+ "<th>Researcher PhoneNo</th><th>Researcher Specialization</th><th>Research Title</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from researchers";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
// iterate through the rows in the result set
			while (rs.next()) {
				String researcherID = Integer.toString(rs.getInt("researcherID"));
				String researcherFname = rs.getString("researcherFname");
				String researcherLname = rs.getString("researcherLname");
				String researcherEmail = rs.getString("researcherEmail");
				String researcherPhoneNo = Integer.toString(rs.getInt("researcherPhoneNo"));
				String researcherSpecialization = rs.getString("researcherSpecialization");
				String researchTitle = rs.getString("researchTitle");
// Add into the html table
				/*output += "<tr><td><input id='hidItemIDUpdate'
name='hidItemIDUpdate'
type='hidden' value='" + itemID + "'>"*/
				
				output += "<tr><td><input id='hidResearcherIDUpdate'name='hidResearcherIDUpdate'type='hidden' value='" + researcherID
						+ "'>" + researcherFname + "</td>";
				output += "<td>" + researcherLname + "</td>";
				output += "<td>" + researcherEmail + "</td>";
				output += "<td>" + researcherPhoneNo + "</td>";
				output += "<td>" + researcherSpecialization+ "</td>";
				output += "<td>" + researchTitle + "</td>";
// buttons
				output += "<td><input name='btnUpdate'type='button' value='Update'class=' btnUpdate btn btn-secondary'></td>
<td><form method='post' action='Researchers.jsp'>
<input name='btnRemove' type='submit'
value='Remove' class='btn btn-danger'>
<input name='hidItemIDDelete' type='hidden'
value='" + researcherID + "'>" + "</form></td></tr>";
				
				/*output += "<td><input name='btnUpdate'type='button' value='Update'class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove'type='button' value='Remove'class='btnRemove btn btn-danger'data-researcherID='"
						+ researcherID + "'>" + "</td></tr>";*/
			}
			con.close();
// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the researchers.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String insertResearchers(String researcherFname, String researcherLname, String researcherEmail, String researcherPhoneNo, String researcherSpecialization, String researchTitle) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
// create a prepared statement
			String query = " insert into researchers(`researcherID`, `researcherFname`, `researcherLname`, `researcherEmail`, `researcherPhoneNo`, `researcherSpecialization`, `researchTitle`)"
					+ " values (?, ?, ?, ?,?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2,researcherFname);
			preparedStmt.setString(3, researcherLname);
			preparedStmt.setString(4,researcherEmail);
			preparedStmt.setInt(5, Integer.parseInt(researcherPhoneNo));
			preparedStmt.setString(6, researcherSpecialization);			
			preparedStmt.setString(7, researchTitle);
// execute the statement
			preparedStmt.execute();
			con.close();
			String newResearchers = readResearchers();
			output = "{\"status\":\"success\", \"data\": \"" + newResearchers + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the researcher.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateResearchers(String researcherID, String researcherFname, String researcherLname, String researcherEmail, String researcherPhoneNo, String researcherSpecialization, String researchTitle) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
// create a prepared statement
			String query = "UPDATE researchers SETresearcherFname=?,researcherLname=?,researcherEmail=?,researcherPhoneNo=?,researcherSpecialization=?,researchTitle=? WHERE researcherID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
//binding values
			preparedStmt.setString(1,researcherFname);
			preparedStmt.setString(2, researcherLname);
			preparedStmt.setString(3,researcherEmail);
			preparedStmt.setInt(4, Integer.parseInt(researcherPhoneNo));
			preparedStmt.setString(5, researcherSpecialization);			
			preparedStmt.setString(6, researchTitle);
			preparedStmt.setInt(7, Integer.parseInt(researcherID));
//execute the statement
			preparedStmt.execute();
			con.close();
			String newResearchers = readResearchers();
			output = "{\"status\":\"success\", \"data\": \"" + newResearchers + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the researcher.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteResearcher(String researcherID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
//create a prepared statement
			String query = "delete from items where researcherID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
//binding values
			preparedStmt.setInt(1, Integer.parseInt(researcherID));
//execute the statement
			preparedStmt.execute();
			con.close();
			String newResearchers = readResearchers();
			output = "{\"status\":\"success\", \"data\": \"" + newResearchers + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the researcher.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}