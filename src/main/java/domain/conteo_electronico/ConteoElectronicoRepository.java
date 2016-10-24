package domain.conteo_electronico;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import domain.candidato.Candidato;
import domain.eleccion.Eleccion;

@Repository
public interface ConteoElectronicoRepository extends CrudRepository<ConteoElectronico, Long> {
	List<ConteoElectronico> findByIdElecciones(Eleccion idEleccion);
	ConteoElectronico findByIdCandidatos(Candidato idCandidato);
}