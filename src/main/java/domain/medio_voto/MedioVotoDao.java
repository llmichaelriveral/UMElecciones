package domain.medio_voto;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface MedioVotoDao extends CrudRepository<MedioVoto, Long> {
	MedioVoto findByNombre(String nombre);
}
