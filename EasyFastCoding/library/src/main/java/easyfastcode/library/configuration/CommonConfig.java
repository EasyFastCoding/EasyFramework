package easyfastcode.library.configuration;
import easyfastcode.library.utils.other.FileUtils;

/**
 * Created by 杨强彪 on 2015/11/10.
 *
 * @描述： 公用常量配置管理类
 */
public interface CommonConfig {

    int PAGER_SIZE = 20;                                   // 分页，每页的数量
    int DEBUGLEVEL = 7;                                    // 日志过滤级别

    // 获取内存缓存的大小
    int MEMORY_CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 1024);
    int DISK_CACHE_SIZE = 50 * 1024 * 1024;                // 图片磁盘缓存50M
    String DISK_CACHE_PATH = FileUtils.getCacheDir();      // 图片磁盘缓存路径

    /**----------------------------- 网络请求的url部分 ------------------------------begin */
    String DEMO_STRING_REQUEST_URL = "????????????????????";
    String IMAGE_URL = "http://b.hiphotos.baidu.com/image/pic/item/b21bb051f81986188ff38b8448ed2e738bd4e67e.jpg";


    /**------------------------------- 网络请求的url部分 ------------------------------end */



    /**------------------------------- Toolbar部分 ----------------------------------begin */
    String NETWORK_DEMO_TITLE = "网络请求demo";
    String GLIDE_DEMO_TITLE = "GLIDE_DEMO_TITLE";


    /**------------------------------- Toolbar部分 ----------------------------------end */
}
