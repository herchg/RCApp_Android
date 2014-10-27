package wi.client.core.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wi.client.core.app.activity.IEventListener;
import wi.client.core.config.JsonConfig;
import wi.client.core.context.IMyContext;

/**
 * 
 * @author hermeschang
 *
 * TODO
 */
public abstract class AbsBaseActivity extends FragmentActivity implements IEventListener {

    public static class Propserties {

    }

    private static final Logger logger = LoggerFactory.getLogger(AbsBaseActivity.class);

    protected JsonConfig mConfig;

    protected AbsBaseApp mApp = AbsBaseApp.getApp();
    protected IMyContext mMyContext = AbsBaseApp.getMyContext();

    public AbsBaseActivity() {
        super();

        init();
    }

    protected void init() {
        // do nothing
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        logger.debug("onCreate()");

        super.onCreate(savedInstanceState);

        /*
         * load config(s) from AndroidManifest.xml
         */
        try {
            String activityConfig = getConfigName();
            loadConfig(activityConfig);
        } catch (Exception ex) {
            logger.error("onCreate()", ex);
        }

        /*
         * inflateLayout
         */
        inflateLayout();
    }

    protected void inflateLayout() {
        // default do nothing
        setContentView(wi.client.core.R.layout.view_no_data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        logger.debug("onCreateOptionsMenu(Menu)");

        boolean ret = inflateMenu(menu);

        if (!ret)
            ret = super.onCreateOptionsMenu(menu);

        return ret;
    }

    protected boolean inflateMenu(Menu menu) {
        // default is nothing
        return false;
    }

    protected String getConfigName() {
        return this.getLocalClassName() + ".json";
    }

    @Override
    protected void onDestroy() {
        logger.debug("onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        logger.debug("onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        logger.debug("onPause()");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        logger.debug("onRestart()");
        super.onRestart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        logger.debug("onRestoreInstanceState()");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        logger.debug("onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        logger.debug("onStart()");
        super.onStart();

//        if (mOnStartCount == 1) {
//            this.mOnStartCount++;
//        }
    }

    @Override
    protected void onStop() {
        logger.debug("onStop()");
        super.onStop();
    }

    /*
     * IEventListener implements
     */
    @Override
    public Result onEvent(Command cmd, Object... params) {
        logger.debug("onEvent() cmd: {}", cmd.name());
        // child have to override this method
        return Result.None;
    }


    /*
     * private methods
     */
    private void loadConfig(String config) {
        try {
            mConfig = mMyContext.getConfigLoader().getConfig(config);
        } catch (Exception ex) {
            logger.error("loadConfig()", ex);
        }
    }

}
