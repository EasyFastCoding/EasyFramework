package easyfastcode.library.module;

import android.view.View;

/**
 * Created by 杨强彪 on 2015/10/26.
 *
 * @描述：基于MVC思想设计的模块基类
 */
public abstract class BaseModule<T>
{

    protected View mRootView;
    protected T		mData;

    public BaseModule() {
        mRootView = initView();
    }

    /**
     * 初始化view,交给实现类去具体实现
     *
     * @return
     */
    protected abstract View initView();

    protected abstract void refreshUI(T data);

    public View getRootView()
    {
        return mRootView;
    }

    public void setData(T data)
    {
        this.mData = data;
        // 根据数据改变UI
        refreshUI(data);
    }

}
