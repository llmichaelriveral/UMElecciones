package domain.conteo_fisico;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import domain.candidato.Candidato;
import domain.eleccion.Eleccion;

@Transactional
public interface ConteoFisicoDao extends CrudRepository<ConteoFisico, Long> {
	List<ConteoFisico> findByIdElecciones(Eleccion idEleccion);
	ConteoFisico findByIdCandidatos(Candidato idCandidato);
}