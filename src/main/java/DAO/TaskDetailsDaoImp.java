package DAO;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import Model.TaskDetails;

@Repository
public class TaskDetailsDaoImp implements TaskDetailsDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean save(TaskDetails student) {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().save(student);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public List<TaskDetails> getId(TaskDetails categoryDet) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<TaskDetails> query = currentSession.createQuery("from taskdetails where id=:id", TaskDetails.class);
		query.setParameter("id", categoryDet.getid());
		List<TaskDetails> list = query.getResultList();
		System.out.print(categoryDet.getid());
		return list;
	}

	@Override
	public boolean update(TaskDetails student) {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().update(student);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public List<Map<String, Object>> getList(String selectedId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createSQLQuery("select id as _id,status,title,frequency from taskdetails where frequency='"+selectedId+"' order by id");
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String, Object>> list = query.list();

		return list;
	}

	@Override
	public boolean delete(int selectedId) {
		boolean status = false;
		try {
			SQLQuery sqlQuery = sessionFactory.getCurrentSession()
					.createSQLQuery("DELETE FROM taskdetails WHERE id=" + selectedId + "");
			sqlQuery.executeUpdate();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean dragDropTask(int id, String status) {
		boolean result = false;
		try {
			SQLQuery sqlQuery = sessionFactory.getCurrentSession()
					.createSQLQuery("Update taskdetails SET Status='" + status + "' WHERE id=" + id + "");
			sqlQuery.executeUpdate();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

}
