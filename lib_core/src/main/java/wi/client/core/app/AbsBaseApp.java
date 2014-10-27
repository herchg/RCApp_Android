package wi.client.core.app;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import wi.client.core.config.AssetConfigLoader;
import wi.client.core.config.IConfigLoader;
import wi.client.core.config.JsonConfig;
import wi.client.core.context.IMyContext;
import wi.client.core.handler.MyHandler;
import wi.client.core.service.IService;

/**
 * 
 * @author hermeschang
 *
 *
 */
public abstract class AbsBaseApp extends Application implements IMyContext {

    public static enum State {
        None, InitApp, FiniApp
    }

    // define properties tag in json
    public static class Properties {

        public final static String PROP_SERVICES    = "Services";
        public final static String PROP_SERVICE_CLASSNAME = "ClassName";
        public final static String PROP_SERVICE_PARAMS = "Params";

        public static class Service {
            private static class Data {
                @Since(1.0) @SerializedName("ClassName")
                public String className;
                @Since(1.0) @SerializedName("Params")
                public Map<String, Object> params;
            }
        }

    }

    private static final String KEY_APP_CONFIG_NAME = "appConfig";

    private static final Logger logger = LoggerFactory.getLogger(AbsBaseApp.class);

    private static AbsBaseApp mInstance;

    private State mState = State.None;

    protected ApplicationInfo mApplicationInfo;
    private Bundle mAppBundle;

    protected JsonConfig mConfig;

    /* MyContext Variables */
    protected Map<String, IService> mServiceMap;
    protected Map<String, Object> mAppObjMap = new HashMap<String, Object>();

    protected Activity mCurrentActivity;
    protected MyHandler mMainHandler;
    protected IConfigLoader mConfigLoader;

    /* singleton instance */
    public static AbsBaseApp getApp() {
        return mInstance;
    }
    public static IMyContext getMyContext() {
        return mInstance;
    }

    /**
     * constructor
     */
    public AbsBaseApp() {
        mInstance = this;
    }

    @Override
    public void onCreate() {
        logger.debug("onCreate()");
        super.onCreate();
        try {
            mApplicationInfo = this.getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            mAppBundle = mApplicationInfo.metaData;
            String appConfig = (String) mAppBundle.get(KEY_APP_CONFIG_NAME);
            initApp(appConfig);
        } catch (Exception ex) {
            logger.error("onCreate()", ex);
        }

//        mMyViewAnimator = new MyViewAnimator(this.getApplicationContext());
    }

    @Override
    public void onLowMemory() {
        logger.debug("onLowMemory()");
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        logger.debug("onTerminate()");
        finiApp();
        super.onTerminate();
    }

    private void initApp(String config) {

        mConfigLoader = new AssetConfigLoader(this.getApplicationContext());
        mMainHandler = new MyHandler(Looper.getMainLooper());

        /*
         * load config
         */
        loadConfig(config);

        /*
         * init app
         *
         * init service(s)
         *
         * start service(s)
         */
        onInitApp();
        onInitService();
        beforeStartService();
        onStartService();
        afterStartService();

        mState = State.InitApp;
    }


    private void finiApp() {

        /*
         * stop service(s)
         * fini service(s)
         * fini app
         */
        beforeStopService();
        onStopService();
        afterStopService();
        onFiniService();
        onFiniApp();

        mState = State.FiniApp;
    }

    public State getState() {
        return this.mState;
    }

    protected void onInitApp() {

        // TODO init App
    }
    protected void onFiniApp() {

        // TODO fini App

    }

    protected void onInitService() {

    }

    protected void beforeStartService() {

    }

    @SuppressWarnings("unchecked")
    protected void onStartService() {
        /*
         * start services
         */
        Map<String, Properties.Service.Data> servicePropertiesDataMap = null;

        try {
            servicePropertiesDataMap = (Map<String, Properties.Service.Data>)mConfig.propertiesMap.get(Properties.PROP_SERVICES);
        } catch (Exception ex) { }

        if (servicePropertiesDataMap != null) {

            mServiceMap = new HashMap<String, IService>(servicePropertiesDataMap.size());
            for (String key : servicePropertiesDataMap.keySet()) {

                Map<String, Object> value = (Map<String, Object>)servicePropertiesDataMap.get(key);
                Properties.Service.Data serviceData = new Properties.Service.Data();
                serviceData.className = (String)value.get(Properties.PROP_SERVICE_CLASSNAME);
                serviceData.params = (Map<String, Object>)value.get(Properties.PROP_SERVICE_PARAMS);

                try {
                    Class cls = Class.forName(serviceData.className);
                    IService service = (IService)cls.newInstance();
                    // set params
                    for (String k: serviceData.params.keySet()) {
                        Object v = serviceData.params.get(k);
                        service.setValue(k, v);
                    }
                    logger.info("start service {}({}): {}", key, serviceData.className, service.startService());
                    mServiceMap.put(key, service);
                } catch (Exception ex) {
                    logger.info("invalid service: {}({})", key, serviceData.className);
                }
            }

        }
    }
    protected void afterStartService() {

    }

    protected void beforeStopService() {

    }
    protected void onStopService() {
        /*
         * Stop Services
         */
        if (mServiceMap != null) {
            for (String key : mServiceMap.keySet()) {
                IService service = mServiceMap.get(key);
                logger.info("stop service {}: {}", key, service.stopService());
            }
        }

    }
    protected void afterStopService() {

    }

    protected void onFiniService() {

    }

    public void setCurrentActivity(Activity activity) {
        this.mCurrentActivity = activity;
    }

    /* private method */
    private void loadConfig(String config) {
        /*
         * load config
         */
        mConfigLoader = new AssetConfigLoader(this.getApplicationContext());
        try {
            mConfig = mConfigLoader.getConfig(config);
        } catch (Exception ex) {
            logger.error("loadConfig(String)", ex);
        }
    }

    /* IMyContext methods */
    @Override
    public ApplicationInfo getApplicationInfo() {
        return mApplicationInfo;
    }

    @Override
    public IConfigLoader getConfigLoader() {
        return this.mConfigLoader;
    }

    @Override
    public Object getServiceObject(String serviceName) {
        return this.mServiceMap.get(serviceName);
    }

    @Override
    public Object getAppObject(String appObjName) {
        return mAppObjMap.get(appObjName);
    }

    @Override
    public MyHandler getMyUIHandler() {
        return this.mMainHandler;
    }

//    @Override
//    public MyViewAnimator getMyViewAnimator() {
//        return mMyViewAnimator;
//    }
}
