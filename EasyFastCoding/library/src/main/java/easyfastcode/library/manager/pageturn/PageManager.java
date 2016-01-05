package easyfastcode.library.manager.pageturn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by cy on 2016/1/4.
 */
public class PageManager {

    private static Activity currentActivity;

    public static void setCurrentActivity(Activity activity) {
        currentActivity = activity;
    }

    //前进

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

    //回退
    public static void goBack(Activity activity) {
        activity.finish();
    }

    public static void goBack2SpePage(Class targeClass) {
        Intent intent = new Intent(currentActivity, targeClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        toPage(intent);
    }


    public static abstract class AppCompatBasePage extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            PageManager.setCurrentActivity(this);
        }
    }


}
