package sanlab.itv.nakivojpslaveowner.repository.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import sanlab.itv.nakivojpslaveowner.model.Job;

import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID>, JobExtendedRepository {

}
