package wi.client.core.config;

import android.content.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wi.client.core.misc.Charset;
import wi.client.core.misc.Lang;
import wi.client.core.util.AssetFileLoader;

/**
 * Created by hermeschang on 2014/3/17.
 *
 * @author hermeschang
 */
public class AssetConfigLoader extends AssetFileLoader implements IConfigLoader {

    private static final Logger logger = LoggerFactory.getLogger(AssetConfigLoader.class);

    public AssetConfigLoader(Context context) {
        super(context);
    }

    @Override
    public JsonConfig getConfig(String fileName) {
        try {
            return JsonConfig.loadFromJson(new String(this.getFile(fileName), Charset.UTF_8.getValue()));
        } catch (Exception ex) {
            logger.error("getConfig(String) exception: {}", fileName);
            return null;
        }
    }

    @Override
    public JsonConfig getConfig(Lang lang, String fileName) {
        try {
            return JsonConfig.loadFromJson(new String(this.getFile(lang, fileName), Charset.UTF_8.getValue()));
        } catch (Exception ex) {
            logger.error("getConfig(Lang, String) exception: {}, {}", lang.getValue(), fileName);
            return null;
        }
    }
}
