package infview.SQL_proxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLRequestManager {
	private static Connection sqlConnection;
	
	public enum transactionTypes{
		AVERAGE, COUNT, UPDATE, INSERT, DELETE, FETCH, SORT
	}
	
	
	public static void initialiseConnection(String target, String username, String password) {
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			sqlConnection = DriverManager.getConnection(target, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Object[][] executeTransaction(String request, transactionTypes type, Object[] statementParams){
		
		if (type == transactionTypes.AVERAGE) return AfetchRequest(request);
		if (type == transactionTypes.COUNT) return AfetchRequest(request);
		if (type == transactionTypes.UPDATE) return prepareRequest(request, statementParams);
		if (type == transactionTypes.INSERT) return prepareRequest(request, statementParams);
		if (type == transactionTypes.DELETE) return prepareRequest(request, statementParams);
		if (type == transactionTypes.FETCH) return AfetchRequest(request);
		
		return null;
	}
	
	private static Object[][] prepareRequest(String request, Object[] statementParams){
		
		
		
		return null;
		
	}
	
	
	private static Object[][] AfetchRequest(String request){
		try {
			Statement statement = sqlConnection.createStatement();
			ResultSet response = statement.executeQuery(request);
			ResultSetMetaData metaData = response.getMetaData();
			int columnNr = metaData.getColumnCount();
			ArrayList<String> data = new ArrayList<>();
			while (response.next()) {
				for (int i = 0; i<columnNr; i++) { 
					data.add(response.getString(i+1));
					
				}
			}
			String[][] dataToReturn = new String[data.size()/columnNr][columnNr];
			for (int i = 0; i<data.size()/columnNr; i++)
				for (int j = 0; j<columnNr; j++)
					dataToReturn[i][j] = data.get(j+ i*columnNr);
			response.close();
			statement.close();
			return dataToReturn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static ResultSet fetchRequest(String request) {
		try {
			Statement statement = sqlConnection.createStatement();
			ResultSet response = statement.executeQuery(request);
			return response;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static PreparedStatement prepareStatement(String query) {
		try {
			return sqlConnection.prepareStatement(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
