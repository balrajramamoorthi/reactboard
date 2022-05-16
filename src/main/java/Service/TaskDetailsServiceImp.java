package Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DAO.TaskDetailsDao;
import Model.TaskDetails;

@Service
@Transactional
public class TaskDetailsServiceImp implements TaskDetailsService {

	@Autowired
	private TaskDetailsDao dao;

	@Override
	public boolean save(TaskDetails student) {
		return dao.save(student);
	}

	@Override
	public boolean update(TaskDetails student) {
		return dao.update(student);
	}

	@Override
	public List<TaskDetails> getId(TaskDetails student) {
		return dao.getId(student);
	}

	@Override
	public List<Map<String,Object>> getList(String selectedId) {
		return dao.getList(selectedId);
	}

	@Override
	public boolean delete(int selectedId) {
		return dao.delete(selectedId);
	}

	@Override
	public boolean dragDropTask(int id, String status) {
		return dao.dragDropTask(id,status);
	}


}
