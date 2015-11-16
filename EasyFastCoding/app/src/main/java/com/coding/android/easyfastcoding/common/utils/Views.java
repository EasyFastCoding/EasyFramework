package com.coding.android.easyfastcoding.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cy on 2015/11/4.
 */
public final class Views {

    /** find by id */

    public static <T extends View> T find(View parent, int viewId) {
        return (T) parent.findViewById(viewId);
    }

    public static <T extends View> T find(Activity activity, int viewId) {
        return (T) activity.findViewById(viewId);
    }

    /** get params */

    public static <T extends ViewGroup.LayoutParams> T getParams(View view) {
        return (T) view.getLayoutParams();
    }

    public static ViewGroup.MarginLayoutParams getMarginParams(View view) {
        return (ViewGroup.MarginLayoutParams) getParams(view);
    }

    public static android.widget.FrameLayout.LayoutParams getFrameParams(View view) {
        return (android.widget.FrameLayout.LayoutParams) getParams(view);
    }

    public static android.widget.RelativeLayout.LayoutParams getRelativeParams(View view) {
        return (android.widget.RelativeLayout.LayoutParams) getParams(view);
    }

    public static android.widget.LinearLayout.LayoutParams getLinearParams(View view) {
        return (android.widget.LinearLayout.LayoutParams) getParams(view);
    }

    /** inflater */

    public static <T extends View> T inflate(Context context, int layoutId) {
        return (T) LayoutInflater.from(context).inflate(layoutId, (ViewGroup) null, false);
    }

    public static <T extends View> T inflate(View root, int layoutId) {
        return inflateInternal(root, layoutId, false);
    }

    public static <T extends View> T inflateAndAttach(View root, int layoutId) {
        return inflateInternal(root, layoutId, true);
    }

    private static <T extends View> T inflateInternal(View root, int layoutId, boolean attach) {
        if (root == null) {
            throw new NullPointerException("Root view cannot be null");
        } else {
            return (T) LayoutInflater.from(root.getContext()).inflate(layoutId, (ViewGroup) root, attach);
        }
    }

    private Views() {
    }
}
