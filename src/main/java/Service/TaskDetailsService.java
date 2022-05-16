package Service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import Model.TaskDetails;

public interface TaskDetailsService {

	boolean save(TaskDetails SubCategory);

	List<TaskDetails> getId(TaskDetails SubCategory);

	boolean update(TaskDetails student);

	List<Map<String,Object>> getList(String selectedId);

	boolean delete(int selectedId);

	boolean dragDropTask(int id, String status);

}
