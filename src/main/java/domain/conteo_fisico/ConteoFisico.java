package domain.conteo_fisico;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import domain.candidato.Candidato;
import domain.eleccion.Eleccion;

@Entity
@Table(name = "conteo_fisico")
public class ConteoFisico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "cont_seq", sequenceName = "conteo_fisico_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cont_seq")
	@Column(name = "id")
	private Long id;

	@Column(name = "sumatoria")
	private int sumatoria;

	@OneToOne
	@JoinColumn(name = "id_elecciones")
	private Eleccion idElecciones;

	@OneToOne
	@JoinColumn(name = "id_candidatos")
	private Candidato idCandidatos;

	@Column(name = "fecha_subida")
	private Date fechaSubida;

	@Column(name = "cedula_subida")
	private String cedulaSubida;

	public ConteoFisico() {

	}

	public ConteoFisico(Long id) {
		this.id = id;
	}

	public ConteoFisico(ConteoFisico conteo) {
		this.id = conteo.id;
		this.sumatoria = conteo.sumatoria;
		this.idElecciones = conteo.idElecciones;
		this.idCandidatos = conteo.idCandidatos;
		this.fechaSubida = conteo.fechaSubida;
		this.cedulaSubida = conteo.cedulaSubida;
	}

	public ConteoFisico(Long id, int sumatoria, Eleccion id_elecciones, Candidato id_candidatos, Date fechaSubida,
			String cedulaSubida) {
		super();
		this.id = id;
		this.sumatoria = sumatoria;
		this.idElecciones = id_elecciones;
		this.idCandidatos = id_candidatos;
		this.fechaSubida = fechaSubida;
		this.cedulaSubida = cedulaSubida;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSumatoria() {
		return sumatoria;
	}

	public void setSumatoria(int sumatoria) {
		this.sumatoria = sumatoria;
	}

	public Eleccion getId_elecciones() {
		return idElecciones;
	}

	public void setId_elecciones(Eleccion id_elecciones) {
		this.idElecciones = id_elecciones;
	}

	public Candidato getId_candidatos() {
		return idCandidatos;
	}

	public void setId_candidatos(Candidato id_candidatos) {
		this.idCandidatos = id_candidatos;
	}
	
	public Eleccion getIdElecciones() {
		return idElecciones;
	}

	public void setIdElecciones(Eleccion idElecciones) {
		this.idElecciones = idElecciones;
	}

	public Candidato getIdCandidatos() {
		return idCandidatos;
	}

	public void setIdCandidatos(Candidato idCandidatos) {
		this.idCandidatos = idCandidatos;
	}

	public Date getFechaSubida() {
		return fechaSubida;
	}

	public void setFechaSubida(Date fechaSubida) {
		this.fechaSubida = fechaSubida;
	}

	public String getCedulaSubida() {
		return cedulaSubida;
	}

	public void setCedulaSubida(String cedulaSubida) {
		this.cedulaSubida = cedulaSubida;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}