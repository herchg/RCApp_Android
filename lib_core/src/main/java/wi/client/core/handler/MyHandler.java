package wi.client.core.handler;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by hermeschang on 2014/4/18.
 * @author hermeschang
 */
public class MyHandler extends Handler {

    public interface ITask {
        public void executeTask(Object token);
    }

    public MyHandler(Looper looper) {
        super(looper);
    }

    public void postDelayed(ITask task, Object token, long delayMillis) {

        this.postDelayed(new MyRunnable(task, token), delayMillis);
    }

    public void post(ITask task, Object token) {

        this.post(new MyRunnable(task, token));
    }

    private class MyRunnable implements Runnable {

        ITask task;
        Object token;

        private MyRunnable(ITask task, Object token) {
            this.task = task;
            this.token = token;
        }

        @Override
        public void run() {
            task.executeTask(token);
        }
    }


}
