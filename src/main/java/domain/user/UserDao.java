package domain.user;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface UserDao extends CrudRepository<User, Long> {
	List<User> findByCedula(String cedula);
}
