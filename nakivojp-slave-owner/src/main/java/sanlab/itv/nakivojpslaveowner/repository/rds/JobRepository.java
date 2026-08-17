package sanlab.itv.nakivojpslaveowner.repository.rds;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sanlab.itv.nakivojpslaveowner.model.Job;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID>, JobExtendedRepository {

    @Query(value = """
        SELECT j FROM Job j
        WHERE :status IS NULL OR :status = '' OR j.status = :status
        ORDER BY j.createdAt DESC
    """, countQuery = """
        SELECT COUNT(DISTINCT j) FROM Job j
        WHERE :status IS NULL OR :status = '' OR j.status = :status
    """)
    Page<Job> getAllByStatus(@Param("status") String status, Pageable pageable);

    Optional<Job> findFirstById(UUID id);

    @Query("""
        SELECT j FROM Job j WHERE j.status IN ('PENDING', 'FAILED')
    """)
    List<Job> getAllByProcessableStatuses();

    @Query(value = """
        SELECT j.id
        FROM job j
        WHERE j.status IN ('PROCESSING', 'PENDING') AND j.request_hash = :requestHash
        LIMIT 1
    """, nativeQuery = true)
    Optional<UUID> getWaitingJobByHash(@Param("requestHash") String requestHash);

}
