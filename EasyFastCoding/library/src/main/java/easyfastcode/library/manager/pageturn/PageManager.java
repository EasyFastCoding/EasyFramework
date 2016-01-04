package easyfastcode.library.manager.pageturn;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by cy on 2016/1/4.
 */
public class PageManager {

    private static Activity currentActivity;

    public static void setCurrentActivity(Activity activity) {
        currentActivity = activity;
    }

    private static void toPage(Intent intent) {
        if (null == currentActivity) {
            throw new RuntimeException("先调用setCurrentActivity方法！");
        }
        currentActivity.startActivity(intent);
    }

    public static void toPage(Class targetClass) {
        Intent intent = new Intent(currentActivity, targetClass);
        toPage(intent);
        currentActivity.finish();
    }

    public static void toPageKeepCurrent(Class targetClass) {
        Intent intent = new Intent(currentActivity, targetClass);
        toPage(intent);
    }

}
