package domain.usuarios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends CrudRepository<Usuarios, Long> {
	Usuarios findByEmail(String email);
	Usuarios findByCedula(String cedula);
}
