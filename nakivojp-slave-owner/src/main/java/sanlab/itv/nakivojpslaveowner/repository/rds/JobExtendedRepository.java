package sanlab.itv.nakivojpslaveowner.repository.rds;

import sanlab.itv.nakivojpshared.constant.EJobStatus;

import java.util.UUID;

public interface JobExtendedRepository {

    void updateStatus(UUID id, EJobStatus status);

    void updateResult(UUID id, EJobStatus status, String errorMessage);

}
