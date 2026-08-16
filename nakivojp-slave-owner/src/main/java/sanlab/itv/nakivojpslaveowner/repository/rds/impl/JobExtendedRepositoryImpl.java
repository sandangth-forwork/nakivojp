package sanlab.itv.nakivojpslaveowner.repository.rds.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import sanlab.itv.nakivojpshared.constant.EJobStatus;
import sanlab.itv.nakivojpslaveowner.model.Job;
import sanlab.itv.nakivojpslaveowner.repository.rds.JobExtendedRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class JobExtendedRepositoryImpl implements JobExtendedRepository {

    private final EntityManager entityManager;

    @Override
    @Transactional
    public void updateStatus(UUID id, EJobStatus status) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Job> criteriaUpdate = cb.createCriteriaUpdate(Job.class);
        Root<Job> root = criteriaUpdate.from(Job.class);
        criteriaUpdate.set(root.get("status"), status.name());
        criteriaUpdate.where(cb.equal(root.get("id"), id));
        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    @Override
    @Transactional
    public void updateResult(UUID id, EJobStatus status, String errorMessage) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Job> update = cb.createCriteriaUpdate(Job.class);
        Root<Job> root = update.from(Job.class);
        update.set(root.get("status"), status.name());
        Optional.ofNullable(errorMessage).ifPresent(inner -> update.set(root.get("errorMessage"), inner));
        if (EJobStatus.FAILED.equals(status)) {
            update.<Long>set(root.get("retryCount"), cb.<Long>sum(root.get("retryCount"), 1L));
        }
        update.where(cb.equal(root.get("id"), id));
        entityManager.createQuery(update).executeUpdate();

    }
}
