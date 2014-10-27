package wi.client.core.misc;

import wi.client.core.pattern.IStringValuable;

/**
 * Created by hermeschang on 2014/3/17.
 * @author hermeschang
 */
public enum Lang implements IStringValuable {

    Tw ("tw-ZH"), En ("en-us"), Cn ("cn-zh");

    private String mValue;

    private Lang(String value) {
        this.mValue = value;
    }

    @Override
    public String getValue() {
        return mValue;
    }
}
