package Model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int userid;
	public String emailId;
	public String personName;
	public String password;
	public String mobileno;
	public int role;
	private String companyid;
	private int statusid;
	private int updatedby;
	private Date updateddatetime = new Date();

	public String getcompanyid() {
		return companyid;
	}

	public void setcompanyid(String companyid) {
		this.companyid = companyid;
	}

	public int getuserid() {
		return userid;
	}

	public void setuserid(int userid) {
		this.userid = userid;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String PersonName) {
		this.personName = PersonName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getstatus() {
		return statusid;
	}

	public void setstatus(int status) {
		this.statusid = status;
	}

	public int getupdatedby() {
		return updatedby;
	}

	public void setupdatedby(int updatedby) {
		this.updatedby = updatedby;
	}

	public Date getupdateddatetime() {
		return updateddatetime;
	}

	public void setupdateddatetime(Date updateddatetime) {
		this.updateddatetime = updateddatetime;
	}

	public String getmobileno() {
		return mobileno;
	}

	public void setmobileno(String mobileno) {
		this.mobileno = mobileno;
	}
}
