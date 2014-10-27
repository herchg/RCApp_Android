package wi.client.core.util;

import android.content.Context;

import java.io.InputStream;

import wi.client.core.misc.Lang;

/**
 * Created by hermeschang on 2014/3/25.
 *
 * @author hermeschang
 */
public class AssetFileLoader {

    private final static String ASSET_CONFIG_ROOT = "config";
    private final static String SLASH = "/";

    protected Context mContext;

    public AssetFileLoader(Context context) {
        mContext = context;
    }

    public byte[] getFile(String fileName) {
        return readAssets(getPath(fileName));
    }

    public byte[] getFile(Lang lang, String fileName) {
        return readAssets(getPath(getLangFolder(lang), fileName));
    }

    private String getPath(String fileName) {
        return ASSET_CONFIG_ROOT + SLASH + fileName;
    }

    private String getPath(String folder, String fileName) {
        return ASSET_CONFIG_ROOT + SLASH + folder + SLASH + fileName;
    }

    /**
     * Return the subfolder associated with the specified language
     *
     * @param lang
     * @return
     */
    private String getLangFolder(Lang lang) {
        return lang.getValue();
    }

    private byte[] readAssets(String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = this.mContext.getAssets().open(fileName);
            return FileUtil.readStreamAsBytes(inputStream);
        } catch (Exception ex) {
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception ex) {

                }
            }
        }
    }
}
