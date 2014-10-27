package wi.client.core.util;

import wi.client.core.pattern.IStringValuable;

/**
 * Created by hermeschang on 2014/4/8.
 * @author hermeschang
 */
public enum ResourceType implements IStringValuable {

    layout ("layout"), drawable ("drawable");

    protected String mValue;
    ResourceType(String value) {
        mValue = value;
    }

    @Override
    public String getValue() {
        return mValue;
    }
}
