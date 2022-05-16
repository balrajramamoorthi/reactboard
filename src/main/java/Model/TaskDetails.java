package Model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "taskdetails")
@Table(name = "taskdetails")
public class TaskDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int id;
	public String title;
	public String status;
	public String frequency;
	private int updatedby;
	private Date updateddatetime = new Date();

	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}

	public String gettitle() {
		return title;
	}

	public void settitle(String title) {
		this.title = title;
	}

	public String getstatus() {
		return status;
	}

	public void setstatus(String status) {
		this.status = status;
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
	
	public String getfrequency() {
		return frequency;
	}

	public void setfrequency(String frequency) {
		this.frequency = frequency;
	}
}
