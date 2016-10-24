package domain.jurado;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import domain.eleccion.Eleccion;

@Repository
public interface JuradoRepository extends CrudRepository<Jurado, Long> {
	Jurado findByCedula(String cedula);
	Jurado findByIdElecciones(Eleccion idEleccion);
}