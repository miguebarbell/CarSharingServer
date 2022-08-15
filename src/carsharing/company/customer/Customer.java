package carsharing.company.customer;

import static java.sql.Types.NULL;

public class Customer {
	private int id;

	public void setCarId(int carId) {
		this.carId = carId;
	}

	private int carId;

	public int getCarId() {
		return carId;
	}

	public String getCustomerName() {
		return customerName;
	}

	private String customerName;

	public Customer(int id, String customerName) {
		this.id = id;
		this.customerName = customerName;
	}
}
