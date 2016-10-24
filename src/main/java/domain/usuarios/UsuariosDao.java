package domain.usuarios;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface UsuariosDao extends CrudRepository<Usuarios, Long> {
	Usuarios findByEmail(String email);
	Usuarios findByCedula(String cedula);
}