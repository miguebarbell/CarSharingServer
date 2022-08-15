package carsharing.company.car;

import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO{
	private List<Car> cars = new ArrayList<>();
	private int currentId = 0;
	@Override
	public void addCar(String carName, int companyId, String companyName) {
		cars.add(new Car(currentId, carName, companyId, companyName));
		currentId++;
	}

	@Override
	public List<Car> getAllCars() {
		return cars;
	}
}
