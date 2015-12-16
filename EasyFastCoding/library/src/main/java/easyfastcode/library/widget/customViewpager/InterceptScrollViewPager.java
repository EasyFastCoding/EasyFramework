package easyfastcode.library.widget.customViewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import easyfastcode.library.utils.LogUtils;

/**
 * Created by 杨强彪 on 2015/12/15.
 *
 * @描述：  viewPager跟上级父控件滑动事件的，判断处理逻辑(滑到边界让出触摸事件，未到边界自己掌握事件)
 */
public class InterceptScrollViewPager extends ViewPager {
    private float	downX;
    private float	downY;

    public InterceptScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public InterceptScrollViewPager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // true 申请父控件不拦截我的touch事件,false 默认父类先拦截事件

        //事件完全由自己处理
        //如果在第一个页面，并且是从左往右滑动,让父控件拦截我
        //如果在最后一个页面,并且是从右往左滑动，父控件拦截
        //否则都不让父类拦截
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
                getParent().requestDisallowInterceptTouchEvent(true);
                //记录下点的位置
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE://移动
                //获取移动的位置坐标
                float moveX = ev.getX();
                float moveY = ev.getY();

                float dx = moveX  - downX;
                float dy = moveY  - downY;

                //横向移动
                if (Math.abs(dx) > Math.abs(dy)) {
                    //如果在第一个页面，并且是从左往右滑动,让父控件拦截我
                    if (getCurrentItem() == 0 && dx > 0) {
                        //由父控件处理该事件
                        LogUtils.i("让父控件处理----------------");
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else if (getCurrentItem() == getAdapter().getCount() - 1 && dx < 0){//如果在最后一个页面,并且是从右往左滑动，父控件拦截
                        //由父控件处理该事件
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        //否则都不让父类拦截
                    }
                } else {
                    //让父控件拦截
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;

            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }


}
