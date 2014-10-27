package wi.client.core.pattern;

/**
 * Created by hermeschang on 2014/4/15.
 * @author hermeschang
 */
public interface IEventable<C, P, R> {
    public R onEvent(C cmd, P... params);
}
