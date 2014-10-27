package wi.client.core.service;

import wi.client.core.pattern.IStatable;

/**
 * 
 * @author hermeschang
 *
 */
public abstract class AbsService implements IService, IStatable<AbsService.State> {

    public enum State {
        Init, Start, Stop;
    }

    protected State mState;
	
	public AbsService() {
	    
		super();
		
		this.mState = State.Init;
	}
	
	@Override
	public boolean startService() {
		if (doStartService()) {
			this.mState = State.Start;
			return true;
		} 
		
		return false;
	}

	@Override
	public boolean stopService() {
		if (doStopService()) {
			this.mState = State.Stop;
			return true;
		} 
		
		return false;
	}
	
	protected abstract boolean doStartService();
	protected abstract boolean doStopService();

    @Override
	public State getState() {
		return mState;
	}

}
