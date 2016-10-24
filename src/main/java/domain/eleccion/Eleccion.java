package domain.eleccion;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "elecciones")
public class Eleccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="eleccion_seq", sequenceName="elecciones_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="eleccion_seq")
	@Column(name = "id")
	private Long id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "cierre")
	private boolean cierre;

	@Column(name = "fecha_inicio")
	private Date fechaInicio;

	@Column(name = "fecha_fin")
	private Date fechaFin;
	
	@Column(name = "cedula_creador")
	private String cedulaCreador;
	
	@Column(name = "fecha_cierre")
	private Date fechaCierre;
	
	@Column(name = "cedula_cierre")
	private String cedulaCierre;
	
	@Column(name = "votos_fisicos")
	private boolean votosFisicos;

	public Eleccion() {

	}

	public Eleccion(Long eleccionId) {
		this.id = eleccionId;
	}

	public Eleccion(Eleccion eleccion) {
		this.id = eleccion.id;
		this.nombre = eleccion.nombre;
		this.descripcion = eleccion.descripcion;
		this.cierre = eleccion.cierre;
		this.fechaInicio = eleccion.fechaInicio;
		this.fechaFin = eleccion.fechaFin;
		this.cedulaCreador = eleccion.cedulaCreador;
		this.fechaCierre = eleccion.fechaCierre;
		this.cedulaCierre = eleccion.cedulaCierre;
		this.votosFisicos = eleccion.votosFisicos;
	}

	public Eleccion(Long eleccionId, String eleccionNombre, String eleccionDescripcion, boolean eleccionCierre,
			Date eleccionFechaInicio, Date eleccionFechaFin, String cedulaCreador, Date fechaCierre, String cedulaCierre) {
		this.id = eleccionId;
		this.nombre = eleccionNombre;
		this.descripcion = eleccionDescripcion;
		this.cierre = eleccionCierre;
		this.fechaInicio = eleccionFechaInicio;
		this.fechaFin = eleccionFechaFin;
		this.cedulaCreador = cedulaCreador;
		this.fechaCierre = fechaCierre;
		this.cedulaCierre = cedulaCierre;
		this.votosFisicos = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long eleccionId) {
		this.id = eleccionId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String eleccionNombre) {
		this.nombre = eleccionNombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String eleccionDescripcion) {
		this.descripcion = eleccionDescripcion;
	}

	public boolean getCierre() {
		return cierre;
	}

	public void setCierre(boolean eleccionCierre) {
		this.cierre = eleccionCierre;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date FechaInicio) {
		this.fechaInicio = FechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date eleccionFechaFin) {
		this.fechaFin = eleccionFechaFin;
	}
	
	public String getCedulaCreador() {
		return cedulaCreador;
	}

	public void setCedulaCreador(String cedulaCreador) {
		this.cedulaCreador = cedulaCreador;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getCedulaCierre() {
		return cedulaCierre;
	}

	public void setCedulaCierre(String cedulaCierre) {
		this.cedulaCierre = cedulaCierre;
	}

	public boolean isVotosFisicos() {
		return votosFisicos;
	}

	public void setVotosFisicos(boolean votosFisicos) {
		this.votosFisicos = votosFisicos;
	}
	
}