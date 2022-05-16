package Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DAO.UsersDao;
import Model.Users;

@Service
@Transactional
public class UsersServiceImp implements UsersService {

	@Autowired
	private UsersDao catDao;

	@Override
	public boolean saveUsers(Users student) {
		return catDao.saveUsers(student);
	}

	@Override
	public boolean updateUsers(Users student) {
		return catDao.updateUsers(student);
	}

	@Override
	public List<Users> getUsersByID(Users student) {
		return catDao.getUsersByID(student);
	}

	@Override
	public List<Object[]> getUsersList() {
		return catDao.getUsersList();
	}

	@Override
	public List<Users> getUsersComboList() {

		return catDao.getUsersComboList();
	}
	@Override
	public List<Users> getSelectedUsersComboList(String userId) {
		
		return catDao.getSelectedUsersComboList(userId);
	}
	@Transactional
	public int adminLogin(String emailId, String password) {
		return catDao.adminLogin(emailId, password);
	}

	@Override
	public String getChartData() {
		return catDao.getChartData();
	}

	@Override
	public String forgotpassword(String dto) {
		return catDao.forgotpassword(dto);
	}
}
