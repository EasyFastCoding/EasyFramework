package easyfastcode.library.imageload.glide.common;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import easyfastcode.library.imageload.glide.transformations.BlurTransformation;
import easyfastcode.library.imageload.glide.transformations.CropCircleTransformation;
import easyfastcode.library.imageload.glide.transformations.RoundedCornersTransformation;

/**
 * Created by cy on 2015/11/10.
 */
public class GlideImageLoader {

    /**
     * 展示图片
     *
     * @param context
     * @param uri       图片网络连接，图片资源id
     * @param imageView
     * @param <T>
     */
    public static <T> void display(Context context, T uri, ImageView imageView) {
        Glide.with(context).load(uri).centerCrop().into(imageView);
    }

    public static <T> void display(Context context, T uri, ImageView imageView, int holderResourceId) {
        Glide.with(context)
                .load(uri)
                .placeholder(holderResourceId)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 圆形图片
     *
     * @param context
     * @param uri
     * @param imageView
     * @param <T>
     */
    public static <T> void circleImage(Context context, T uri, ImageView imageView) {
        Glide.with(context)
                .load(uri)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    /**
     * 对图片进行模糊处理并显示
     *
     * @param context
     * @param uri
     * @param imageView
     * @param radius    模糊范围，取25比较合适
     * @param <T>
     */
    public static <T> void blurImage(Context context, T uri, ImageView imageView, int radius) {
        Glide.with(context)
                .load(uri)
                .bitmapTransform(new BlurTransformation(context, radius, 1))
                .into(imageView);
    }

    /**
     * 对图片进行圆角处理并显示
     *
     * @param context
     * @param uri
     * @param imageView
     * @param radius    圆角半径
     * @param <T>
     */
    public static <T> void roundedCornersImage(Context context, T uri, ImageView imageView, int radius) {
        Glide.with(context)
                .load(uri)
                .bitmapTransform(new RoundedCornersTransformation(context, 100, 0))
                .into(imageView);
    }

}
