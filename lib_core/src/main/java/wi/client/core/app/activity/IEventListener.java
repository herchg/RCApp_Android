package wi.client.core.app.activity;


import wi.client.core.pattern.IEventable;

/**
 * Created by hermeschang on 2014/4/15.
 * @author hermeschang
 */
public interface IEventListener extends IEventable<IEventListener.Command, Object, IEventListener.Result> {

    /**
     * Created by hermeschang on 2014/4/15.
     */
    enum Command {
        CloseMe;
    }

    /**
     * Created by hermeschang on 2014/4/15.
     */
    enum Result {
        None, Success, Failure;
    }
}
