package domain.medio_voto;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedioVotoRepository extends CrudRepository<MedioVoto, Long> {
    MedioVoto findByNombre(String nombre);
}