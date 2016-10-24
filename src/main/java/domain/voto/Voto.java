package domain.voto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import domain.candidato.Candidato;
import domain.eleccion.Eleccion;
import domain.medio_voto.MedioVoto;
import domain.user.User;

@Entity
@Table(name = "votos")
public class Voto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="voto_seq", sequenceName="votos_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="voto_seq")
	@Column(name = "id")
	private Long id;

	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "direccion")
	private String direccion;
	
	@OneToOne
	@JoinColumn(name = "id_elecciones")
	private Eleccion idElecciones;
	
	@OneToOne
	@JoinColumn(name = "id_candidatos")
	private Candidato idCandidatos;
	
	@OneToOne
	@JoinColumn(name = "id_usuarios_aptos")
	private User idUsuariosAptos;
	
	@OneToOne
	@JoinColumn(name = "id_medio")
	private MedioVoto idMedio;

	public Voto() {

	}

	public Voto(Long id) {
		this.id = id;
	}
	
	public Voto(Voto voto) {
		this.id = voto.id;
		this.fecha = voto.fecha;
		this.direccion = voto.direccion;
		this.idElecciones = voto.idElecciones;
		this.idCandidatos = voto.idCandidatos;
		this.idUsuariosAptos = voto.idUsuariosAptos;
		this.idMedio = voto.idMedio;
	}

	public Voto(Long id, Date fecha, String direccion, Eleccion id_elecciones, Candidato id_candidatos,
			User id_usuarios_aptos, MedioVoto id_medio) {
		this.id = id;
		this.fecha = fecha;
		this.direccion = direccion;
		this.idElecciones = id_elecciones;
		this.idCandidatos = id_candidatos;
		this.idUsuariosAptos = id_usuarios_aptos;
		this.idMedio = id_medio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public User getId_usuarios_aptos() {
		return idUsuariosAptos;
	}

	public void setId_usuarios_aptos(User id_usuarios_aptos) {
		this.idUsuariosAptos = id_usuarios_aptos;
	}

	public MedioVoto getId_medio() {
		return idMedio;
	}

	public void setId_medio(MedioVoto id_medio) {
		this.idMedio = id_medio;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}