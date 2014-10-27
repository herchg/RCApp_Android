package wi.client.core.config;

import wi.client.core.misc.Lang;

/**
 * Created by hermeschang on 2014/3/25.
 *
 * @author hermeschang
 */
public interface IConfigLoader {

    public JsonConfig getConfig(String fileName);
    public JsonConfig getConfig(Lang lang, String fileName);
}
