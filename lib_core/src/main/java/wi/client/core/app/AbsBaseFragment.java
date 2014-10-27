package wi.client.core.app;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wi.client.core.config.JsonConfig;
import wi.client.core.context.IMyContext;

/**
 * Created by hermeschang on 2014/3/17.
 *
 * @author hermeschang
 */
public abstract class AbsBaseFragment extends Fragment {

    public static class Properties {
        public final static String PROP_COMMENT    = "Comment";

    }

    private static final Logger logger = LoggerFactory.getLogger(AbsBaseFragment.class);

    protected JsonConfig mConfig;

    protected AbsBaseApp mApp = AbsBaseApp.getApp();
    protected IMyContext mMyContext = AbsBaseApp.getMyContext();
    protected wi.client.core.app.activity.IEventListener mActivityEventListener;// = (my.core.app.activity.IEventListener)this.getActivity();

    protected Object mComment;

    public AbsBaseFragment() {
        super();

        init();
    }

    protected void init() {
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);

        TypedArray a = activity.obtainStyledAttributes(attrs, wi.client.core.R.styleable.AbsBaseFragment);
        CharSequence configName = a.getText(wi.client.core.R.styleable.AbsBaseFragment_configName);
        if (configName != null) {
            loadConfig(configName.toString());
        }
        a.recycle();

    }

    public void loadConfig(String config) {

        try {
            mConfig = mMyContext.getConfigLoader().getConfig(config);
        } catch (Exception ex) {
            logger.error("loadConfig()", ex);
        }

        mComment = mConfig.propertiesMap.get(Properties.PROP_COMMENT);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        logger.debug("onCreate()");
        super.onCreate(savedInstanceState);
        mActivityEventListener = (my.core.app.activity.IEventListener)this.getActivity();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        logger.debug("onCreateOptionsMenu()");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        logger.debug("onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onStart() {
        logger.debug("onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        logger.debug("onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        logger.debug("onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        logger.debug("onStop()");
        super.onStop();
    }

}
