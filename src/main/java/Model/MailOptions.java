package Model;

public class MailOptions {
	private String host;
	private String port;
	private boolean authenticate;
	private String mailUser;
	private boolean ssl;
	private String mailPassword;

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	private boolean mailDebug;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public boolean getAuthenticate() {
		return authenticate;
	}

	public void setAuthenticate(boolean authenticate) {
		this.authenticate = authenticate;
	}

	public String getMailUser() {
		return mailUser;
	}

	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}

	public boolean getSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public boolean isMailDebug() {
		return mailDebug;
	}

	public void setMailDebug(boolean mailDebug) {
		this.mailDebug = mailDebug;
	}

	public MailOptions() {
		this.host = "smtp.gmail.com";
		this.port = "465";
		this.authenticate = true;
		this.ssl = true;
		this.mailDebug = false;
		this.mailPassword = "rytways@12345";
		this.mailUser = "rytwayssoftwaretechnologies@gmail.com";

	}

}
