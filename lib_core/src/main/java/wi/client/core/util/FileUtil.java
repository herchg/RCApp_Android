package wi.client.core.util;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author hermeschang
 *
 * provide file-level helper API
 */
public class FileUtil {
    /**
     * Read the content of an input stream and concatenate it to a String
     * @param in
     * @param charSet
     * @return
     * @throws Exception
     */
    public static String readStreamAsString(InputStream in, Charset charSet) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, charSet));
        while (true) {
            String line = reader.readLine();
            if (line == null)
                break;
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * Read the content of an input stream as a byte array
     * @param in
     * @return
     * @throws Exception
     */
    public static byte[] readStreamAsBytes(InputStream in) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16 * 1024];

        while ((nRead = in.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        return buffer.toByteArray();
    }

    /**
     * Check if an external file exists.
     * @param filePathName This is the full path name, e.g. "/xqma/myFile.txt". The file should be located under external Storage folder root. Typically "/sdcard"
     * @return
     */
    public static boolean isExternalFileExist(String filePathName) {
        if (!isExternalStorageReadable())
            return false;

        try {
            File file = new File(Environment.getExternalStorageDirectory(), filePathName);
            return file.exists();
        }
        catch(Exception ex) {
            return false;
        }
    }

    /**
     * Read the content of an external file as a String with the specified charSet.
     * @param filePathName This is the full path name, e.g. "/xqma/myFile.txt". The file should be located under external Storage folder root. Typically "/sdcard"
     * @return null if file is not opened correctly
     */
    public static String readExternalFileAsString(String filePathName, Charset charset) {
        if (!isExternalStorageReadable())
            return null;

        InputStream istream = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory(), filePathName);
            istream = new FileInputStream(file);
            return readStreamAsString(istream, charset);
        }
        catch(Exception ex) {
            return null;
        }
        finally {
            if (istream != null) {
                try {
                    istream.close();
                }
                catch(Exception ex) {
                }
            }
        }
    }

    /**
     * Write content to an external file.
     * @param filePathName
     * @param content
     * @throws Exception
     */
    public static void writeExternalFile(String filePathName, byte[] content) throws Exception {
        File file = new File(Environment.getExternalStorageDirectory(), filePathName);
        FileOutputStream stream = new FileOutputStream(file);
        stream.write(content);
        stream.close();
    }

    /**
     * @return 回傳是否可以從external storage讀取檔案.
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
