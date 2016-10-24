package domain.conteo_fisico;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import domain.candidato.Candidato;
import domain.eleccion.Eleccion;

@Repository
public interface ConteoFisicoRepository extends CrudRepository<ConteoFisico, Long> {
	List<ConteoFisico> findByIdElecciones(Eleccion idEleccion);
	ConteoFisico findByIdCandidatos(Candidato idCandidato);
}