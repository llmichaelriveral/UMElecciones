package domain.usuarios;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "usuarios_registrados")
public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "usuarios_seq", sequenceName = "usuarios_registrados_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_seq")
	@Column(name = "id")
	private Long id;

	@Column(name = "cedula")
	private String cedula;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "contraseña")
	private String password;
	
	@Column(name = "correo")
	private String email;

	public Usuarios() {

	}

	public Usuarios(Long id) {
		this.id = id;
	}

	public Usuarios(Usuarios usuarios) {
		this.id = usuarios.id;
		this.cedula = usuarios.cedula;
		this.nombre = usuarios.nombre;
		this.email = usuarios.email;
	}

	public Usuarios(Long id, String Cedula, String Nombre, String Contraseña, String Email) {
		this.id = id;
		this.cedula = Cedula;
		this.nombre = Nombre;
		this.password = Contraseña;
		this.email = Email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long Id) {
		this.id = Id;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String Cedula) {
		this.cedula = Cedula;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String Email) {
		this.email = Email;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}