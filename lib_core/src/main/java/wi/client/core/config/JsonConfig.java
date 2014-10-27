package wi.client.core.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import wi.client.core.util.FileUtil;

/**
 * 
 * @author hermeschang
 * 
 */
public class JsonConfig {

    private static final double VERSION = 1.0;
    private static final String DEFAULT_CHARSET = "UTF-8";

    @Since(1.0) @SerializedName("properties")
    public Map<Object, Object> propertiesMap = new HashMap<Object, Object>();

    protected void init() {
        // do nothing
    }

    public static JsonConfig loadFromJson(String json) {
        try {
            // TODO 這邊讀不過去, 要解決
            GsonBuilder builder = new GsonBuilder().setVersion(VERSION);
            Gson gson = builder.create();

            JsonConfig config = gson.fromJson(json, JsonConfig.class);

            return config;
        } catch (Exception ex) {
            return null;
        }
    }

    public static JsonConfig loadFromStream(InputStream in) {
        try {
            String json = FileUtil.readStreamAsString(in, Charset.forName(DEFAULT_CHARSET));
            return loadFromJson(json);
        } catch (Exception ex) {
            return null;
        }
    }

}
