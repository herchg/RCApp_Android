package wi.client.core.misc;

import wi.client.core.pattern.IStringValuable;

/**
 * Created by hermeschang on 2014/3/25.
 *
 * @author hermeschang
 */
public enum Charset implements IStringValuable {

    UTF_8 ("utf-8");

    private String mValue;

    private Charset (String value) {
        this.mValue = value;
    }

    @Override
    public String getValue() {
        return mValue;
    }
}
