package domain.admin;

import java.io.Serializable;

import javax.persistence.*;

import domain.eleccion.Eleccion;

@Entity
@Table(name = "admins")
public class Admin implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	 @Id
	 @SequenceGenerator(name="admins_seq", sequenceName="admins_id_seq")
	 @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="admins_seq")
	 @Column(name="id")
	 private Long id;
	 
	 @Column(name="cedula")
	 private String cedula;
	 
	 @Column(name="cedula_creador")
	 private String cedulaCreador;
	 
	 @OneToOne
	 @JoinColumn(name="id_elecciones")
	 private Eleccion idElecciones;
	 
	 public Admin(){
		 
	 }
	 
	 public Admin(Long id){
		 this.id = id;
	 }
	 
	 public Admin(Admin admin){
		 this.id = admin.id;
		 this.cedula = admin.cedula;
		 this.cedulaCreador = admin.cedulaCreador;
		 this.idElecciones = admin.idElecciones;
	 }

	public Admin(Long id, String cedula, String cedulaCreador, Eleccion idElecciones) {
		this.id = id;
		this.cedula = cedula;
		this.cedulaCreador = cedulaCreador;
		this.idElecciones = idElecciones;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getCedulaCreador() {
		return cedulaCreador;
	}

	public void setCedulaCreador(String cedulaCreador) {
		this.cedulaCreador = cedulaCreador;
	}

	public Eleccion getIdElecciones() {
		return idElecciones;
	}

	public void setIdElecciones(Eleccion idElecciones) {
		this.idElecciones = idElecciones;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
