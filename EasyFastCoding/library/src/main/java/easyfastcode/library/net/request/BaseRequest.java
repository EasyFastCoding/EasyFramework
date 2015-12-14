package easyfastcode.library.net.request;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 杨强彪 on 2015/11/11.
 *
 * @描述： 请求体的基类
 */
public class BaseRequest implements Parcelable {

    public BaseRequest() {
    }

    protected BaseRequest(Parcel in) {
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static final Parcelable.Creator<BaseRequest> CREATOR = new Parcelable.Creator<BaseRequest>() {
        public BaseRequest createFromParcel(Parcel source) {
            return new BaseRequest(source);
        }

        public BaseRequest[] newArray(int size) {
            return new BaseRequest[size];
        }
    };
}
