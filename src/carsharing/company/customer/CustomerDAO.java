package carsharing.company.customer;

import java.util.List;

public interface CustomerDAO {
	public void addCustomer(String customerName);
	public List<Customer> getAllCustomers();
}
