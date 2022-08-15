package carsharing.company.car;

public class Car {


	private int id;
	private int companyId;

	public String getCompanyName() {
		return companyName;
	}

	private String companyName;

	public String getCarName() {
		return carName;
	}

	private final String carName;

	public Car(int id, String carName, int companyId, String companyName) {
		this.id = id;
    this.carName = carName;
		this.companyId = companyId; // this is new
		this.companyName = companyName;
	}

}
