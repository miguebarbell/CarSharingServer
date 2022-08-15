package carsharing;

import carsharing.company.CompanyDAOImpl;
import carsharing.company.customer.CustomerDAO;
import carsharing.company.customer.CustomerDAOImpl;

import java.sql.*;

public class DatabaseConnector {
//	public CompanyDAOImpl companydao = new CompanyDAOImpl();
//	public CustomerDAO customerdao = new CustomerDAOImpl();
//	private String dropAllTables = "DROP TABLE IF EXISTS COMPANY, CAR, CUSTOMER";
	private String companyTable =
			"Create table IF NOT EXISTS COMPANY (ID int PRIMARY KEY AUTO_INCREMENT, NAME varchar(50) UNIQUE NOT NULL)";
	private String carTable = "Create table IF NOT EXISTS CAR (" +
			"ID int PRIMARY KEY AUTO_INCREMENT, " +
			"NAME varchar UNIQUE NOT NULL, " +
			"COMPANY_ID int NOT NULL, " +
			"FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID))";
	private String customerTable = "Create table IF NOT EXISTS CUSTOMER (" +
			"ID int PRIMARY KEY AUTO_INCREMENT, " +
			"NAME varchar UNIQUE NOT NULL, " +
			"RENTED_CAR_ID int, " +
			"FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID))";
	private String JDBC_DRIVER = "org.h2.Driver";
	private String JDBC_URL = "jdbc:h2:file:./src/carsharing/db/anything";

	private Connection connection = null;
	private Statement statement = null;
	public DatabaseConnector(String[] args) {
		if (args.length >=2 && args[0].equals("-databaseFileName")) {
			this.JDBC_URL = "jdbc:h2:file:./src/carsharing/db/" + args[1];
		}
		connect();
//		try {
//			Class.forName(JDBC_DRIVER);
//			connection = DriverManager.getConnection(JDBC_URL);
//			System.out.println("Connected to H2 in-memory database.");
//			Statement statement = connection.createStatement();
//			statement.executeUpdate(sql);
//			connection.setAutoCommit(true);
//			statement.close();
//			connection.close();
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException(e);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}

	public Statement getStatement() {
		return statement;
	}

	void connect() {
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(JDBC_URL);
//			System.out.println("Connected to H2 in-memory database.");
			statement = connection.createStatement();
			connection.setAutoCommit(true);
			executeUpdate(this.companyTable);
			executeUpdate(this.carTable);
			executeUpdate(this.customerTable);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	void disconnect() throws SQLException {
//		System.out.println("Disconnecting...");
		statement.close();
		connection.close();
//		System.out.println("Disconnected from database.");
	}
	void executeUpdate(String sql) throws SQLException {
		statement.executeUpdate(sql);
	}
	ResultSet executeQuery(String sql) throws SQLException {
		return statement.executeQuery(sql);
	}



}
