package carsharing.company;

import carsharing.company.car.Car;
import carsharing.company.car.CarDAOImpl;

import java.util.List;

public class Company {
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	private String companyName;
	private CarDAOImpl cars = new CarDAOImpl();

	public String getCompanyName() {
		return companyName;
	}

	public int getID() {
		return ID;
	}

	private int ID;

	public Company(int ID, String companyName) {
		this.companyName = companyName;
		this.ID = ID;
	}
	public Company(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "Company{" +
				"companyName='" + companyName + '\'' +
				", ID=" + ID +
				'}';
	}
	public List<Car> getCars() {
		return cars.getAllCars();
	}
	public void addCar(String name) {
		cars.addCar(name, ID, companyName);
	}
}
