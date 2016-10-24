package domain.conteo_electronico;

import java.io.Serializable;

import javax.persistence.*;

import domain.candidato.Candidato;
import domain.eleccion.Eleccion;

@Entity
@Table(name = "conteo_electronico")
public class ConteoElectronico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="cont_seq", sequenceName="conteo_electronico_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cont_seq")
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

	public ConteoElectronico() {

	}

	public ConteoElectronico(Long id) {
		this.id = id;
	}
	
	public ConteoElectronico(ConteoElectronico conteo) {
		this.id = conteo.id;
		this.sumatoria = conteo.sumatoria;
		this.idElecciones = conteo.idElecciones;
		this.idCandidatos = conteo.idCandidatos;
	}

	public ConteoElectronico(Long id, int sumatoria, Eleccion id_elecciones, Candidato id_candidatos) {
		super();
		this.id = id;
		this.sumatoria = sumatoria;
		this.idElecciones = id_elecciones;
		this.idCandidatos = id_candidatos;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}