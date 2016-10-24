package domain.conteo_electronico;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import domain.candidato.Candidato;
import domain.eleccion.Eleccion;

@Transactional
public interface ConteoElectronicoDao extends CrudRepository<ConteoElectronico, Long> {
	List<ConteoElectronico> findByIdElecciones(Eleccion idEleccion);
	ConteoElectronico findByIdCandidatos(Candidato idCandidato);
}