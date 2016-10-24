package domain.candidato;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import domain.eleccion.Eleccion;

@Repository
public interface CandidatoRepository extends CrudRepository<Candidato, Long> {
	List<Candidato> findByIdElecciones(Eleccion eleccion);
	Candidato findByNumeroTarjeton(int parseInt);
}