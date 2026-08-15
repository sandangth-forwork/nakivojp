package sanlab.itv.nakivojpslaveowner.repository.queue;

import org.springframework.stereotype.Component;
import sanlab.itv.nakivojpshared.repository.queue.JobQueueRepository;

@Component
public class ProcessVideoQueueRepository implements JobQueueRepository {
    @Override
    public void enqueue() {

    }
}
