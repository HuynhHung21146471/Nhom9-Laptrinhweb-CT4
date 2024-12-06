package vn.iotstar.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserModel implements Serializable {

	private static final long serialVersionUID = 1L; 
	private String username;
	private String password;
	private String email;
	private String code;

	private Boolean isForgot = false;

	public UserModel(String username, String password, String code) {
		super();
		this.username = username;
		this.password = password;
		this.code = code;
	}

	
}
