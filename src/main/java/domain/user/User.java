package domain.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import domain.eleccion.Eleccion;

@Entity
@Table(name = "usuarios_aptos")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "user_seq", sequenceName = "usuarios_aptos_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	@Column(name = "id")
	private Long id;

	@Column(name = "cedula")
	private String cedula;

	@Column(name = "email")
	private String email;

	@Column(name = "concepto")
	private String concepto;

	@Column(name = "fecha_creacion")
	private Date fechaCreacion;

	@Column(name = "cedula_creador")
	private String cedulaCreador;
	
	@OneToOne
	@JoinColumn(name = "id_elecciones")
	private Eleccion id_elecciones;

	@Column(name = "nombres_apellidos")
	private String nombresApellidos;
	
	public User() {

	}

	public User(Long id) {
		this.id = id;
	}

	public User(User user) {
		this.id = user.id;
		this.cedula = user.cedula;
		this.email = user.email;
		this.id_elecciones = user.id_elecciones;
		this.concepto = user.concepto;
		this.fechaCreacion = user.fechaCreacion;
		this.cedulaCreador = user.cedulaCreador;
		this.nombresApellidos = user.nombresApellidos;
	}

	public User(Long id, String Cedula, String Email, Eleccion id_elecciones, String concepto, Date fechaCreacion,
			String cedulaCreador, String nombresApellidos) {
		this.id = id;
		this.cedula = Cedula;
		this.email = Email;
		this.id_elecciones = id_elecciones;
		this.concepto = concepto;
		this.fechaCreacion = fechaCreacion;
		this.cedulaCreador = cedulaCreador;
		this.nombresApellidos = nombresApellidos;
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

	public Eleccion getId_elecciones() {
		return id_elecciones;
	}

	public void setId_elecciones(Eleccion id_elecciones) {
		this.id_elecciones = id_elecciones;
	}
	
	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getCedulaCreador() {
		return cedulaCreador;
	}

	public void setCedulaCreador(String cedulaCreador) {
		this.cedulaCreador = cedulaCreador;
	}
	
	public String getNombresApellidos() {
		return nombresApellidos;
	}

	public void setNombresApellidos(String nombresApellidos) {
		this.nombresApellidos = nombresApellidos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}