package com;

import java.sql.*;

public class funding {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String readItems() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connectingto the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Name</th><th>Description</th><th>Date</th>"
					+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from funding";

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String fundingID = Integer.toString(rs.getInt("fundingID"));
				String Name = rs.getString("Name");
				String Desc = rs.getString("Desc");
				String date = rs.getString("date");

				// Add into the html table
				output += "<tr><td><input id='hidItemIDUpdate'name='hidItemIDUpdate'type='hidden' value='" + fundingID
						+ "'>" + Name + "</td>";
				output += "<td>" + Desc + "</td>";
				output += "<td>" + date + "</td>";

				// buttons
				output += "<td><input name='btnUpdate'type='button' value='Update'class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove'type='button' value='Remove'class='btnRemove btn btn-danger'data-itemid='"
						+ fundingID + "'>" + "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String insertItem(String name, String desc, String date) {

		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connectingto the database for inserting.";
			}

			// create a prepared statement
			String query = " insert into funding(`fundingID`,`Name`,`Desc`,`date`)" + " values (?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, desc);
			preparedStmt.setString(4, date);

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateItem(String ID, String name, String desc, String date) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connectingto the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE funding SET Name=?,Desc=?,date=?, WHERE fundingID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, desc);
			preparedStmt.setString(3, date);
			preparedStmt.setInt(4, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteItem(String fundingID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connectingto the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from funding where fundingID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(fundingID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}