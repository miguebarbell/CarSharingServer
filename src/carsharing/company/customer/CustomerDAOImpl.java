package carsharing.company.customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
	private List<Customer> customers = new ArrayList<>();
	private int currentId = 0;
	@Override
	public void addCustomer(String customerName) {
		customers.add(new Customer(currentId, customerName));
		currentId++;
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customers;
	}
}
