/**
 * 
 */
package wi.client.core.service.task;

import java.util.concurrent.Future;

/**
 * @author hermeschang
 *
 */
public interface IFutureSetter {

	public void setFuture(Future<?> future, int ticket);
}
