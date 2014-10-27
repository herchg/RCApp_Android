package wi.client.core.context;

import android.content.pm.ApplicationInfo;

import wi.client.core.config.IConfigLoader;
import wi.client.core.handler.MyHandler;

/**
 * Created by hermeschang on 2014/3/25.
 *
 * @author hermeschang
 */
public interface IMyContext {

    /**
     * Application Info
     * @return
     */
    public ApplicationInfo getApplicationInfo();

    /**
     * Get Config loader
     * @return ConfigLoader
     */
    public IConfigLoader getConfigLoader();

    /**
     * 要自己cast成自己要用的class
     * @param serviceName
     * @return Service Instance
     */
    public Object getServiceObject(String serviceName);

    /**
     * 要自己cast成自己要用的class
     * get App Object
     */
    public Object getAppObject(String appObjName);

    /**
     * 取得Main Looper Handler
     * @return
     */
    public MyHandler getMyUIHandler();

//    public MyViewAnimator getMyViewAnimator();

}
