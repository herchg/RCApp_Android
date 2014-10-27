package wi.client.core.service.task;

import java.util.concurrent.Future;

/**
 * Created by hermeschang on 2014/4/14.
 * @author hermeschang
 */
public interface ITaskService {

    public Future<?> execute(ITask task, ICallback callback);
    public boolean cancel(Future<?> future);
}
