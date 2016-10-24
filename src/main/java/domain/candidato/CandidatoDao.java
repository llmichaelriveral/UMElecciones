package domain.candidato;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import domain.eleccion.Eleccion;


@Transactional
public interface CandidatoDao extends CrudRepository<Candidato, Long> {
	List<Candidato> findByIdElecciones(Eleccion eleccion);
	Candidato findByNumeroTarjeton(int parseInt);
}