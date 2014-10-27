package wi.client.core.service.task;

/**
 * 
 * @author hermeschang
 *
 */
public interface IResult {

	public boolean isException();
	
	/**
	 * 
	 * @return if <isException> return a DJException IService, otherwise return the result.
	 */
	public Object getResult();
}
