package Service;

import java.util.List;

import Model.Users;

public interface UsersService {
	
	boolean saveUsers(Users SubCategory);
	List<Users> getUsersByID(Users SubCategory);
	boolean updateUsers(Users student);
	List<Object[]> getUsersList();
	List<Users> getUsersComboList();
	List<Users> getSelectedUsersComboList(String userId);
	int adminLogin(String emailId, String password);
	String getChartData();
	String forgotpassword(String users);
}
