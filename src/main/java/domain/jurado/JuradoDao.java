package domain.jurado;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import domain.eleccion.Eleccion;
import domain.jurado.Jurado;

@Transactional
public interface JuradoDao extends CrudRepository<Jurado, Long> {
	List<Jurado> findByCedula(String cedula);
	List<Jurado> findByIdElecciones(Eleccion idEleccion);
}