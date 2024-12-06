package vn.iotstar.entity;

import java.io.Serializable;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "username", length = 50, nullable = false)
	private String username;
	
	@Column(name = "password", length = 100, nullable = false)
	private String password;
	
	@Column(name = "fullname", length = 50, nullable = false)
	private String fullname;
	
	@Column(name = "email", length = 50, nullable = false)
	private String email;
	
	@Column(name = "phone", length = 50, nullable = false)
	private String phone;
	
	@Column(name = "code", length = 50)
	private String code;
	
	@Column(name = "status")
	private int status;
	
	@ManyToOne
	@JoinColumn(name = "roleid")
	private Role role;

	public User(String username, String email, String code) {
		super();
		this.username = username;
		this.email = email;
		this.code = code;
	}
	
	
	
}
