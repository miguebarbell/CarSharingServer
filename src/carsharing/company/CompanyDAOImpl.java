package carsharing.company;

import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {
	private List<Company> companies = new ArrayList<>();
	private int currentId = 0;

	@Override
	public void addCompany(String companyName) {
		companies.add(new Company(currentId, companyName));
		currentId++;
	}

	@Override
	public List<Company> getAllCompanies() {

		return companies;
	}

	@Override
	public Company getCompanyById(int companyId) {
		return companies.get(companyId);
	}

	@Override
	public void updateCompany(int companyId, String companyName) {
		companies.get(companyId).setCompanyName(companyName);
		System.out.println("Company " + companyId + " name has been updated to " + companyName);
	}

	@Override
	public void deleteCompany(int companyId) {
		companies.remove(companyId);
    System.out.println("Company " + companyId + " has been deleted");
	}
}
