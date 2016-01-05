package easyfastcode.library.utils.other;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by cy on 2016/1/5.
 */
public class AssetsUtils {

    /**
     * 获取asset中文件的文本内容
     */
    public static String getContent(Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            return IOUtils.inputStream2String(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
