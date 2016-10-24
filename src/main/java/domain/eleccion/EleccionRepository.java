package domain.eleccion;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EleccionRepository extends CrudRepository<Eleccion, Long> {
	List<Eleccion> findByCierre(boolean cierre);
}