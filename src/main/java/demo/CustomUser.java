package demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CustomUser {

	@GeneratedValue
	@Id
	private Long id;

	private String username;

	private String password;

	public CustomUser() {}

	public CustomUser(CustomUser u) {
		id = u.getId();
		username = u.getUsername();
		password = u.getPassword();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
