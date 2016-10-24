package domain.voto;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import domain.eleccion.Eleccion;

@Transactional
public interface VotoDao extends CrudRepository<Voto, Long> {
	List<Voto> findByIdElecciones(Eleccion idEleccion);
}
