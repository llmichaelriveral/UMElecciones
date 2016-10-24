package domain.eleccion;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;


@Transactional
public interface EleccionDao extends CrudRepository<Eleccion, Long> {
	List<Eleccion> findByCierre(boolean cierre);
	List<Eleccion> findByCedulaCreador(String cedula);
}