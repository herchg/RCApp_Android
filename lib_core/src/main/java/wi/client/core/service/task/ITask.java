package wi.client.core.service.task;

/**
 * <pre>
 * 
task.doPreTask();
...
callback.callback(task.doTask());
...
task.doPostTask();
...
 * 
 * </pre>
 * @author hermeschang
 *
 */
public interface ITask {
	
	public void doPreTask();
	public IResult doTask();
	public void doPostTask();
}
