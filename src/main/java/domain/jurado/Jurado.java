package domain.jurado;

import java.io.Serializable;

import javax.persistence.*;

import domain.eleccion.Eleccion;

@Entity
@Table(name = "jurados")
public class Jurado implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	 @Id
	 @SequenceGenerator(name="jurados_seq", sequenceName="jurados_id_seq")
	 @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="jurados_seq")
	 @Column(name="id")
	 private Long id;
	 
	 @Column(name="cedula")
	 private String cedula;
	 
	 @Column(name="cedula_creador")
	 private String cedulaCreador;
	 
	 @OneToOne
	 @JoinColumn(name="id_elecciones")
	 private Eleccion idElecciones;
	 
	 public Jurado(){
		 
	 }
	 
	 public Jurado(Long id){
		 this.id = id;
	 }
	 
	 public Jurado(Jurado jurado){
		 this.id = jurado.id;
		 this.cedula = jurado.cedula;
		 this.cedulaCreador = jurado.cedulaCreador;
		 this.idElecciones = jurado.idElecciones;
	 }

	public Jurado(Long id, String cedula, String cedulaCreador, Eleccion idElecciones) {
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
