package DAO;

import java.util.List;
import java.util.UUID;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Model.Mail;
import Model.MailOptions;
import Model.Users;
import Model.Utils;

@Repository
public class UsersDaoImp implements UsersDao {

	@Autowired
	private SessionFactory sessionFactory;

	public int adminLogin(String emailId, String password) {

		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();

			Query query = session.createQuery("from Users where emailId=:emailId and password=:password");
			query.setParameter("emailId", emailId);
			query.setParameter("password", password);
			List<Users> list = query.list();

			int size = list.size();
			if (size == 1) {
				return list.get(0).getuserid();
			} else {
				return -1;
			}
		} catch (Exception exception) {
			System.out.println("Excecption while saving admin Details : " + exception.getMessage());
			return 0;
		} finally {
			session.flush();
		}
	}

	@Override
	public boolean saveUsers(Users student) {
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
	public List<Users> getUsersByID(Users categoryDet) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Users> query = currentSession.createQuery("from Users where userid=:userid", Users.class);
		query.setParameter("userid", categoryDet.getuserid());
		List<Users> list = query.getResultList();
		System.out.print(categoryDet.getuserid());
		return list;
	}

	@Override
	public boolean updateUsers(Users student) {
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
	public List<Object[]> getUsersList() {
		Session currentSession = sessionFactory.getCurrentSession();
		String query = "select ROW_NUMBER() OVER(ORDER BY personname) AS sno,personname, emailid, mobileno, role, statusid,userid from users order by personname";
		List<Object[]> list = currentSession.createSQLQuery(query).list();
		return list;
	}

	@Override
	public List<Users> getUsersComboList() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Users> query = currentSession.createQuery("from Users", Users.class);
		List<Users> list = query.getResultList();
		return list;
	}

	@Override
	public List<Users> getSelectedUsersComboList(String userId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Users> query = currentSession.createQuery("from Users where UserId IN(" + userId + ")", Users.class);
		List<Users> list = query.getResultList();
		return list;
	}

	@Override
	public String getChartData() {
		Session currentSession = sessionFactory.getCurrentSession();
		StringBuffer sb = new StringBuffer("[");
		Query query = currentSession
				.createSQLQuery("select count(TicketId),CategoryName from ticketdetview group by CategoryId");
		List<Object[]> rows = query.list();
		String lables = "", values = "", retValues = "";
		int i = 0;
		for (Object[] row : rows) {
			/*
			 * sb.append("{\"label\":\"" + row[1] + "\","); sb.append("\"legendText\":\"" +
			 * row[1] + "\","); sb.append("\"y\":" + row[0] + ""); sb.append("},");
			 */
			if (row.length == i) {
				values += row[0];
				lables += row[1];
			} else {
				values += row[0] + ",";
				lables += row[1] + ",";
			}
			i++;
		}
		return lables + "^" + values;
	}

	@Override
	public String forgotpassword(String emailId) {
		String returnVal = "Your request has been accepted pls verify your email";
		try {
			String queryString = String.format("update Users set password = :password where emailId=:emailId", Users.class);
		    Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		    query.setParameter("password", UUID.randomUUID().toString());
		    query.setParameter("emailId", emailId);
		    int result=query.executeUpdate();
			if(result>0) {
			Mail sendMail = new Mail();
			MailOptions options = new MailOptions();
			sendMail.sendMail(null, emailId, "", "Forgot Password", getMailBody(emailId), options);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnVal;
	}

	public String getMailBody(String emailId) {
		StringBuilder builder = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Users> query = currentSession.createQuery("from Users where emailId='" + emailId + "'", Users.class);
			List<Users> list = query.getResultList();
			if(list.size()>0){
				builder = new StringBuilder(
						"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
				builder.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" >");
			builder = new StringBuilder();
			builder.append("<html><head>");
			builder.append("<title>Re: Forgot Password Notification</title>");
			builder.append("</head>");
			builder.append("<body>");
			builder.append("<br>");
			builder.append("Dear "+Utils.clearNull(list.get(0).getPersonName())+",<br><br>");
			builder.append("<div>Your password is	"+Utils.clearNull(list.get(0).getPassword())+" <br><br>");
			//builder.append("For login go to http://" + Utils.clearNull(Utils.getIpAddress())+ "<br><br>");
			//builder.append("Please change the password once you login.<br><br>");
			builder.append("<br><br><br><br><br>");
			builder.append("<b>Note: This is an electronic message. Please do not reply to this email.</b><br><br>");
			builder.append("Thank You, <br>");
			builder.append("<p>Warm Regards,<br>Accountant </p>");
			builder.append("</body>");
			builder.append("</html>");
			 }
			// dto=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
}
