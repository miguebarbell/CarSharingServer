package carsharing.company;

import java.util.List;

public interface CompanyDAO {
	public void addCompany(String companyName);
	public List<Company> getAllCompanies();
	public Company getCompanyById(int companyId);
	public void updateCompany(int companyId, String companyName);
	public void deleteCompany(int companyId);
}
