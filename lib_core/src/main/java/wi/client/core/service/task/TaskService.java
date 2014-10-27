package wi.client.core.service.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import wi.client.core.service.AbsService;

/**
 * 
 * @author hermeschang
 * 
 */
public class TaskService extends AbsService implements ITaskService {

    public enum ParamKey {
        Size;
    }

	protected final static boolean MAY_INTERRUPT_IF_RUNNING = true;
	
	private static Logger logger = LoggerFactory.getLogger(TaskService.class);
	
	protected ExecutorService mExecutorService;
	protected double mSize;

    public TaskService() {
        super();
    }

    public TaskService(int size) {
        this((double)size);
    }

    public TaskService(double size) {
        this();
        this.mSize = size;
    }

    @Override
    public void setValue(String key, Object value) {
        switch (ParamKey.valueOf(key)) {
            case Size:
                if (value instanceof Double) {
                    this.mSize = (Double) value;
                } else if (value instanceof Integer) {
                    this.mSize = (Integer)value;
                }
                break;
        }
    }

    @Override
    public Object getValue(String key) {
        switch (ParamKey.valueOf(key)) {
            case Size:
                return this.mSize;
        }
        return null;
    }

    @Override
	public synchronized Future<?> execute(ITask task, ICallback callback) {

		try {
			if (!mExecutorService.isShutdown()) {
				return mExecutorService.submit(new Runner(task, callback));
			}
		} catch (Exception ex) {
			logger.error("executeTask()", ex);
		}
		
		return null;
	}

	/**
	 * 
	 * @param future
	 * @return false if the task could not be cancelled, typically because it has already completed normally; true otherwise 
	 */
    @Override
	public boolean cancel(Future<?> future) {

		if (future != null) {
			return future.cancel(MAY_INTERRUPT_IF_RUNNING);
		}

		return false;
	}

    @Override
    protected boolean doStartService() {

        try {
            mExecutorService = Executors.newFixedThreadPool((int) mSize);
            return true;
        } catch (Exception ex) {
            logger.error("doStartService()", ex);
        }

        return false;
    }

    @Override
    protected boolean doStopService() {

        try {
            mExecutorService.shutdownNow();
            return true;
        } catch (Exception ex) {
            logger.error("doStopService()", ex);
        }
        return false;
    }

    /**
     *
     * @author hermeschang
     *
     */
    public static class Runner implements Runnable {

        private static Logger logger = LoggerFactory.getLogger(Runner.class);

        protected ITask mTask;
        protected ICallback mCallback;

        public Runner(ITask task, ICallback callback) {
            mTask = task;
            mCallback = callback;
        }

        @Override
        public void run() {

            /*
             * 1. do preTask
             * 2. doTask and callback
             * 3. do postTask
             */
            if (mTask != null) {

                try {
                    /* pre task */
                    mTask.doPreTask();
                } catch (Exception ex) {
                    logger.error("doPreTask(): " + ex.getMessage());
                }

                try {

                    /* do task */
                    if (mCallback != null) {
                        mCallback.callback(mTask.doTask());
                    } else {
                        mTask.doTask();
                    }
                } catch (Exception ex) {
                    logger.error("doTask() & callback(): " + ex.getMessage());
                }

                try {
                    /* post task */
                    mTask.doPostTask();
                } catch (Exception ex) {
                    logger.error("doPostTask(): " + ex.getMessage());
                }
            }

        }

    }

    /**
     * @author hermeschang
     *
     */
    public static class Command {

        public enum CommandCode {
            DoTask;
        }

        protected CommandCode mCommandCode;
        protected ITask mTask;
        protected ICallback mCallback;
        protected IFutureSetter mFutureSetter;
        protected int mTicket;

        /**
         * Constructor
         *
         */
        public Command(CommandCode commandCode, ITask task, ICallback callback, IFutureSetter futureSetter, int ticket) {
            this.mCommandCode = commandCode;
            this.mTask = task;
            this.mCallback = callback;
            this.mFutureSetter = futureSetter;
            this.mTicket = ticket;
        }

        public CommandCode getCommandCode() {
            return mCommandCode;
        }

        public ITask getTask() {
            return mTask;
        }

        public ICallback getCallback() {
            return mCallback;
        }

        public IFutureSetter getFutureSetter() {
            return mFutureSetter;
        }

        public int getTicket() {
            return mTicket;
        }


    }
}
