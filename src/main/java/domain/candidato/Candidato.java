package domain.candidato;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import domain.eleccion.Eleccion;

@Entity
@Table(name = "candidatos")
public class Candidato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="candidatos_seq", sequenceName="candidatos_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="candidatos_seq")
	@Column(name = "id")
	private Long id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "numero_tarjeton")
	private int numeroTarjeton;

	@Column(name = "imagen")
	private String imagen;

	@OneToOne
	@JoinColumn(name = "id_elecciones")
	private Eleccion idElecciones;

	public Candidato() {

	}

	public Candidato(Long candidatoId) {
		this.id = candidatoId;
	}

	public Candidato(Candidato candidato) {
		this.id = candidato.id;
		this.nombre = candidato.nombre;
		this.numeroTarjeton = candidato.numeroTarjeton;
		this.imagen = candidato.imagen;
		this.idElecciones = candidato.idElecciones;
	}

	public Candidato(Long id, String nombre, int numeroTarjeton, String imagen, Eleccion idElecciones) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.numeroTarjeton = numeroTarjeton;
		this.imagen = imagen;
		this.idElecciones = idElecciones;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumeroTarjeton() {
		return numeroTarjeton;
	}

	public void setNumeroTarjeton(int numeroTarjeton) {
		this.numeroTarjeton = numeroTarjeton;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
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