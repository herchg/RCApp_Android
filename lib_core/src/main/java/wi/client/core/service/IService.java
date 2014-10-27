package wi.client.core.service;

/**
 * @author hermeschang
 * 
 */
public interface IService {

    public void setValue(String key, Object value);
    public Object getValue(String key);
	public boolean startService();
	public boolean stopService();


}
