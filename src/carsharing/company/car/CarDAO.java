package carsharing.company.car;

import java.util.List;

public interface CarDAO {
	public void addCar(String carName, int companyId, String companyName);
	public List<Car> getAllCars();
}
