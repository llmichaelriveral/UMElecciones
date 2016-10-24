package domain.admin;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface AdminDao extends CrudRepository<Admin, Long> {
	List<Admin> findByCedula(String cedula);
}