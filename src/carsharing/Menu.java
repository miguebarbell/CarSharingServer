package carsharing;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {
	Scanner in = new Scanner(System.in);
	DatabaseConnector database;

	public Menu(DatabaseConnector databaseConnector) throws SQLException {
		database = databaseConnector;

	}

	public void mainMenu() throws SQLException {
		System.out.println("\n1. Log in as a manager\n2. Log in as a customer\n3. Create a customer\n0. Exit");
		int next = in.nextInt();
		switch (next) {
			case 1 -> managerMenu();
			case 2 -> loginAsCustomer();
			case 3 -> createCustomer();
			case 0 -> database.disconnect();
		}
	}


	public void managerMenu() throws SQLException {
//		Customer customer = null;
		System.out.println("\n1. Company list\n2. Create a company\n0. Back");
		int next = in.nextInt();
		switch (next) {
			case 1 -> companyList(0);
			case 2 -> createCompany();
			case 0 -> mainMenu();
		}
	}
	public void companyList(int customer) throws SQLException {
		ResultSet resultSet = database.executeQuery("SELECT ID, NAME FROM COMPANY ORDER BY ID ASC");
		int index = 1;
		Map<Integer, Integer> map = new HashMap<>();
		// see the actual records in the database
		if (!resultSet.next()) {
			System.out.println("\nThe company list is empty!");
			managerMenu();
		}
		else {
//			if (customer != 0) System.out.println("\nChoose a company:"); else System.out.println("\n");
			System.out.println("\nChoose a company:");
		do {
				String companyName = resultSet.getString("NAME");
				int id = resultSet.getInt("ID");
				System.out.println(index + ". " + companyName);
				map.put(index, id);
				index++;
			}	while (resultSet.next());
		}
		System.out.println("0. Back");
		int chosenCompanyId = in.nextInt();
		if (chosenCompanyId == 0) managerMenu();
		else {
//			resultSet = database.executeQuery("SELECT ID, NAME FROM COMPANY WHERE ID=" + chosenCompanyId);
			carOptions(map.get(chosenCompanyId), customer);
		}
		managerMenu();
	}
	public void carOptions(int companyId, int customer) throws SQLException {
		int next;
		if (customer == 0) {
			System.out.println("\n1. Car list\n2. Create a car\n0. Back");
			next = in.nextInt();
		} else next = 1;
		switch (next) {
			case 0 -> managerMenu();
			case 1 -> carList(companyId, customer);
			case 2 -> createCar(companyId);
		}
	}

	private void carList(int companyId, int customer) throws SQLException {
		ResultSet rentedcars = database.executeQuery("SELECT * FROM CUSTOMER WHERE RENTED_CAR_ID IS NOT NULL");
		StringBuilder rentedCarIds = new StringBuilder();
		if (rentedcars.next()) rentedCarIds.append(rentedcars.getInt("RENTED_CAR_ID"));
		while (rentedcars.next()) {
			rentedCarIds.append(", ").append(rentedcars.getInt("RENTED_CAR_ID"));
		}
//		System.out.println("rentedCarIds = " + rentedCarIds);
		ResultSet resultSet = database.executeQuery("SELECT * FROM CAR WHERE COMPANY_ID=" + companyId + " AND ID NOT IN " +
				"(" + rentedCarIds + ")");
		int index = 1;
		Map<Integer, Integer> map = new HashMap<>();
		if (!resultSet.next()) {
			System.out.println("\nThe car list is empty!");
			carOptions(companyId, customer);
		}
		else {
			if (customer != 0) {
				System.out.println("\nChoose a car:");
			} else {
				System.out.println("\nCar list:");
			}
			do {
				String carName = resultSet.getString("NAME");
				int carId = resultSet.getInt("ID");
				System.out.println(index + ". " + carName);
				map.put(index, carId);
				index++;
			}
			while (resultSet.next());
		}
		if (customer != 0) {
			System.out.println("0. Back");
			int carIndex = in.nextInt();
			if (carIndex == 0) managerMenu();
			else {
				database.executeUpdate("UPDATE CUSTOMER SET RENTED_CAR_ID=" +  map.get(carIndex) + " WHERE ID="+ customer);
				resultSet = database.executeQuery("SELECT * FROM CAR WHERE ID="+ map.get(carIndex));
				resultSet.next();
				System.out.println("\nYou rented '" + resultSet.getString("NAME") + "'");
				customerLoggedMenu(customer);
			}
		} else {
			carOptions(companyId, customer);
		}
	}

	public void loginAsCustomer() throws SQLException {
		// list the customers
		ResultSet resultSet = database.executeQuery("SELECT * FROM customer");
		if (!resultSet.next()) {
			System.out.println("\nThe customer list is empty!");
			mainMenu();
		}
		else {
			System.out.println("\nCustomer list:");
			do {
				String customerName = resultSet.getString("NAME");
				int customerId = resultSet.getInt("ID");
				System.out.println(customerId + ". " + customerName);
			} while (resultSet.next());
		}

//		List<Customer> customers = database.customerdao.getAllCustomers();
//		if (customers.size() == 0) {
//			System.out.println("\nThe customer list is empty!");
//			mainMenu();
//		}
//		else {
//			System.out.println("\nCustomer list:");
//			int index = 1;
//			for (Customer customer : customers) {
//				System.out.println(index + ". " + customer.getCustomerName());
//				index++;
//			}
			System.out.println("0. Back");
			int customerIndex = in.nextInt();
			if (customerIndex == 0) mainMenu();
			else customerLoggedMenu(customerIndex);
	}
	public void customerLoggedMenu(int customer) throws SQLException {
		System.out.println("\n1. Rent a car\n2. Return a rented car\n3. My rented car\n0. Back");
		int next = in.nextInt();
		switch (next) {
			case 0 -> mainMenu();
			case 1 -> rentACar(customer);
			case 2 -> returnARentedCar(customer);
			case 3 -> checkRentedCar(customer);
		}
	}
	public void checkRentedCar(int customer) throws SQLException {
		ResultSet resultSet = database.executeQuery("SELECT * FROM CUSTOMER WHERE ID=" + customer);
		resultSet.next();
		int rentedCarId = resultSet.getInt("RENTED_CAR_ID");
		if (rentedCarId != 0){
			resultSet = database.executeQuery("SELECT * FROM CAR WHERE ID=" + rentedCarId);
			resultSet.next();
			String rentedCarName = resultSet.getString("NAME");
			resultSet = database.executeQuery("SELECT * FROM COMPANY WHERE ID=" + resultSet.getInt("COMPANY_ID"));
			resultSet.next();
			String companyRentedCar = resultSet.getString("NAME");
			System.out.println("\nYour rented car:\n" + rentedCarName + "\nCompany:\n" + companyRentedCar);
		} else System.out.println("\nYou didn't rent a car!");
		customerLoggedMenu(customer);
	}
	public void rentACar(int customer) throws SQLException {
		ResultSet resultSet = database.executeQuery("SELECT * FROM customer WHERE id=" + customer);
		resultSet.next();
//		System.out.println(resultSet.getString("NAME"));
//		System.out.println(resultSet.getInt("RENTED_CAR_ID"));
		int rentedCar = resultSet.getInt("RENTED_CAR_ID");
		if (rentedCar != 0) {
			System.out.println("\nYou've already rented a car!");
			customerLoggedMenu(customer);
		} else {
			companyList(customer);
		}

	}
	public void returnARentedCar(int customer) throws SQLException {
		// check if the customer has a rented car
		ResultSet resultSet = database.executeQuery("SELECT * FROM CUSTOMER WHERE ID=" + customer);
		resultSet.next();
		if (resultSet.getInt("RENTED_CAR_ID") == 0) System.out.println("\nYou didn't rent a car!");
		else {
			database.executeUpdate("UPDATE CUSTOMER SET RENTED_CAR_ID=NULL WHERE ID=" + customer);
			System.out.println("You've returned a rented car!");
		}
		customerLoggedMenu(customer);
	}
	public void createCar(int companyId) throws SQLException {
		System.out.println("\nEnter the car name:");
		in.nextLine();
		String newCarName = in.nextLine();
		String sql =
				"INSERT INTO CAR (NAME, COMPANY_ID) VALUES ('" + newCarName + "', (SELECT ID FROM COMPANY WHERE ID='" + companyId +
						"'));";
		database.executeUpdate(sql);
		System.out.println("\nThe car was added!");
		carOptions(companyId, 0);
	}
	public void createCompany() throws SQLException {
		System.out.println("\nEnter the company name: ");
		in.nextLine();
		String newCompanyName = in.nextLine();
//		database.companydao.addCompany(newCompanyName); // this doesn't matter
		String sql = "INSERT INTO COMPANY (NAME) VALUES ('" + newCompanyName + "');";
		database.executeUpdate(sql);
		System.out.println("\nThe company was created!");
		managerMenu();
	}
	public void createCustomer() throws SQLException {
		System.out.println("\nEnter the customer name:");
		in.nextLine();
		String newCustomerName = in.nextLine();
		String sql = "INSERT INTO CUSTOMER (NAME) VALUES ('" + newCustomerName + "');";
		database.executeUpdate(sql);
		System.out.println("\nThe customer was added!");
		mainMenu();
	}
}
