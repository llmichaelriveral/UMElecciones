package domain.medio_voto;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "medio_voto")
public class MedioVoto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="medio_seq", sequenceName="medio_voto_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="medio_seq")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nombre")
	private String nombre;

	public MedioVoto(){
		
	}
	
	public MedioVoto(MedioVoto medioVoto){
		this.id = medioVoto.id;
		this.nombre = medioVoto.nombre;
	}
	
	public MedioVoto(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}