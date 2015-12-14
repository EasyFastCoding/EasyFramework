package easyfastcode.library.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 杨强彪 on 2015/11/10.
 *
 * @描述： 关于日期的工具类
 */
public class DateUtil {

    /**
     * 格式：yyyyMMddHHmmss
     */
    public static final String FORMAT_ONE = "yyyyMMddHHmmss";

    /**
     * 格式：yyyy/MM/dd HH:mm
     */
    public static final String FORMAT_FOUR = "yyyy/MM/dd HH:mm";

    /**
     * 格式：yy/MM/dd
     */
    public static final String FORMAT_FIVE = "yy/MM/dd";

    /**
     * 格式：MM/dd yyyy
     */
    public static final String FORMAT_SIX = "MM/dd yyyy";

    /**
     * 把符合日期格式的字符串转换为日期类型
     *
     * @param dateStr 符合日期格式的字符串
     * @param format  日期格式
     * @return 日期类型
     */
    public synchronized static java.util.Date stringtoDate(String dateStr, String format) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e) {
            d = null;
        }
        return d;
    }

    public synchronized static long getTime(String dateString) {
        long x = 0;
        try {
            SimpleDateFormat YYYY_MM_DD_HH_MM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = YYYY_MM_DD_HH_MM.parse(dateString);
            x = date.getTime() / 1000;

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return x;
    }

    public synchronized static long getTime1(String dateString) {
        long x = 0;
        try {
            SimpleDateFormat SHORT_DATE_FORMAT_ONE = new SimpleDateFormat("HH:mm");
            Date date = SHORT_DATE_FORMAT_ONE.parse(dateString);
            x = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return x;
    }

    public synchronized static Date getDate(String dateString) {
        Date date = null;
        try {
            SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
            date = YYYY_MM_DD.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    public synchronized static Date getDate1(String dateString) {
        Date date = null;
        try {
            if (dateString.length() == 19) {
                SimpleDateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = YYYY_MM_DD_HH_MM_SS.parse(dateString);
            } else {
                SimpleDateFormat YYYY_MM_DD_HH_MM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                date = YYYY_MM_DD_HH_MM.parse(dateString);
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    public synchronized static Date getDate2(String dateString) {
        Date date = null;
        try {
            SimpleDateFormat SHORT_DATE_FORMAT_ONE = new SimpleDateFormat("HH:mm");
            date = SHORT_DATE_FORMAT_ONE.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将长整型的时间转换成"时:分:秒"格式的字符串的时间
     *
     * @param diff 长整型的时间
     * @return "时:分:秒"格式的字符串
     */
    public synchronized static String diff(long diff) {
        int hh = 0;
        int mm = 0;
        int ss = (int) (diff % Integer.valueOf("60"));
        diff = diff / Integer.valueOf("60");
        mm = (int) (diff % Integer.valueOf("60"));
        hh = (int) (diff / Integer.valueOf("60"));
        String result = intToString(hh) + ":" + intToString(mm) + ":" + intToString(ss);
        return result;
    }

    /**
     * 将整形的时间转换成字符串格式的时间
     *
     * @param i 整形的时间
     * @return 字符串格式的时间
     */
    private synchronized static String intToString(int i) {
        String result = "";
        if (i < Integer.valueOf("10")) {
            result += "0" + i;
        } else {
            result += "" + i;
        }
        return result;
    }

    /**
     * 把日期转换为指定格式的字符串
     *
     * @param date   日期
     * @param format 指定格式
     * @return 指定格式的字符串
     */
    public synchronized static String dateToString(Date date, String format) {
        String result = "";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            result = formater.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 把日期转换为指定格式的字符串
     *
     * @return 指定格式的字符串
     */
    public synchronized static String dateToString1(long d) {
        String result = "";
        Date date = new Date(d * 1000);
        try {
            SimpleDateFormat SHORT_DATE_FORMAT_ONE_CX_1 = new SimpleDateFormat("MM月dd日");
            result = SHORT_DATE_FORMAT_ONE_CX_1.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public synchronized static String dateInterval(long startTime, long endTime) {
        StringBuffer result = new StringBuffer();
        Date startDate = new Date(startTime * 1000);
        Date endDate = new Date(endTime * 1000);
        SimpleDateFormat SHORT_DATE_FORMAT_ONE_CX = new SimpleDateFormat("yy-MM-dd");
        String start = SHORT_DATE_FORMAT_ONE_CX.format(startDate);
        String end = SHORT_DATE_FORMAT_ONE_CX.format(endDate);
        if (!isTheSameDay(startDate, endDate)) {
            result.append("有效时间: ").append(start).append(" 至  ").append(end);
        } else {
            result.append("有效时间: ").append(start);
        }
        return result.toString();
    }

    public synchronized static boolean isTheSameDay(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                && (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 把日期转换为指定格式的字符串
     *
     * @param date         日期
     * @param firstFormat  格式一
     * @param secondFormat 格式二
     * @return 指定格式的字符串
     */
    public synchronized static String dateString(String date, String firstFormat, String secondFormat) {
        String result = "19700101000000";
        Date d = stringtoDate(date, firstFormat);
        if (d != null) {
            result = dateToString(d, secondFormat);
        }
        return result;
    }

    /**
     * 两个日期的秒差
     *
     * @param firstTime 小日期
     * @param secTime   大日期
     * @return 两个日期的秒差
     */
    public synchronized static long timeSub(String firstTime, String secTime) {
        try {
            long first = stringtoDate(firstTime, FORMAT_ONE).getTime();
            long second = stringtoDate(secTime, FORMAT_ONE).getTime();
            return (second - first) / Integer.valueOf("1000");
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 两个日期的分钟差
     *
     * @param date1 小日期
     * @param date2 大日期
     * @return 两个日期的分钟差
     */
    public static long minuteDiff(Date date1, Date date2) {
        return (date2.getTime() - date1.getTime()) / (Integer.valueOf("1000") * Integer.valueOf("60"));
    }

    /**
     * 两个日期的毫秒差
     *
     * @param date1 小日期
     * @param date2 大日期
     * @return 两个日期的毫秒差
     */
    public static long millDiff(Date date1, Date date2) {
        return date2.getTime() - date1.getTime();
    }

    /**
     * 返回这种(H:M:S)个格式的字符串
     *
     * @param millSeconds 总时间
     * @return %02d:%02d:%02d
     * @throws
     */
    public static String getHMS(int millSeconds) {
        String tempString = "";
        long minute = millSeconds / 60;
        long hour = minute / 60;
        long second = millSeconds % 60;
        minute %= 60;
        tempString = String.format("%02d:%02d:%02d", hour, minute, second);
        return tempString;
    }

    public static void main(String[] args) {
        // 获得日历对象
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        // 显示当前的日期和各个分量
        display(c);
        // 设置日期和时间
        c.set(2012, 11, 23);
        c.set(Calendar.HOUR, 10);
        c.set(Calendar.MINUTE, 4);
        c.set(Calendar.SECOND, 54);
        System.out.println("更新后的时间：");
        display(c);
        // 调整日期和时间
        c.add(Calendar.DATE, 10); // 意思是把当前日期加上10天
        c.add(Calendar.HOUR_OF_DAY, 10);// 意思是把当前时间加上10个小时

    }

    public static void display(Calendar c) {
        String month[] = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
        String days[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        System.out.print("日期：");
        System.out.print(c.get(Calendar.YEAR) + "年");
        System.out.print(month[c.get(Calendar.MONTH)]);
        System.out.print(c.get(Calendar.DATE) + "日 ");
        System.out.println(days[c.get(Calendar.DAY_OF_WEEK) - 1]);
        System.out.print("时间：");
        System.out.print(c.get(Calendar.HOUR) + ":");
        System.out.print(c.get(Calendar.MINUTE) + ":");
        System.out.println(c.get(Calendar.SECOND));
    }

    public static int daysBetween(Date now, Date returnDate) {
        Calendar cNow = Calendar.getInstance();
        Calendar cReturnDate = Calendar.getInstance();
        cNow.setTime(now);
        cReturnDate.setTime(returnDate);
        setTimeToMidnight(cNow);
        setTimeToMidnight(cReturnDate);
        long todayMs = cNow.getTimeInMillis();
        long returnMs = cReturnDate.getTimeInMillis();
        long intervalMs = todayMs - returnMs;
        return millisecondsToDays(intervalMs);
    }

    private static int millisecondsToDays(long intervalMs) {
        return (int) (intervalMs / (1000 * 86400));
    }

    private static void setTimeToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    public static Date getSpercilDay() {
        Calendar theCa = Calendar.getInstance();
        theCa.setTime(new Date());
        theCa.add(Calendar.DATE, -30);
        Date date = theCa.getTime();
        return date;
    }

    public static String getDateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static long getDistanceDays(String str1, String str2) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long days = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    public static long getDistanceDays(Date one, Date two) {
        long days = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff = time2 - time1;
        days = diff / (1000 * 60 * 60 * 24);
        return days;
    }

    public static String getDate(Date time) {
        if (time == null) {
            return "";
        }
        int date = time.getDate();
        int month = time.getMonth() + 1;
        long year = time.getYear() + 1900;
        String dateString = year + "年" + month + "月" + date + "日";
        System.out.println(dateString);
        return dateString;
    }

    public static String getTime(long milliseconds) {
        Date date = new Date(milliseconds);
        boolean sameYear = false;
        String todySDF = "HH:mm";
        String yesterDaySDF = "昨天";
        String beforYesterDaySDF = "前天";
        String otherSDF = "MM-dd";
        String otherYearSDF = "yyyy-MM-dd";
        SimpleDateFormat sfd = null;
        String time = "";
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Date now = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(now);
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        if (dateCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)) {
            sameYear = true;
        } else {
            sameYear = false;
        }
        if (dateCalendar.after(todayCalendar)) {// 判断是不是今天
            sfd = new SimpleDateFormat(todySDF);
            time = sfd.format(date);
            return "今天" + time;
        } else {
            todayCalendar.add(Calendar.DATE, -1);
            if (dateCalendar.after(todayCalendar)) {// 判断是不是昨天
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");// 初始化Formatter的转换格式。
                String hms = formatter.format(milliseconds);
                time = yesterDaySDF + hms;
                return time;
            }
            todayCalendar.add(Calendar.DATE, -2);
            if (dateCalendar.after(todayCalendar)) {// 判断是不是前天
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");// 初始化Formatter的转换格式。
                String hms = formatter.format(milliseconds);
                time = beforYesterDaySDF + hms;
                return time;
            }
        }

        if (sameYear) {
            sfd = new SimpleDateFormat(otherSDF);
            time = sfd.format(date);
        } else {
            sfd = new SimpleDateFormat(otherYearSDF);
            time = sfd.format(date);
        }

        return time;
    }

    public static String getTime1(long milliseconds) {
        Date date = new Date(milliseconds);
        boolean sameYear = false;
        String otherSDF = "MM/dd HH:mm";
        String otherYearSDF = "yyyy/MM/dd HH:mm";
        SimpleDateFormat sfd = null;
        String time = "";
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Date now = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(now);
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        if (dateCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)) {
            sameYear = true;
        } else {
            sameYear = false;
        }
        if (sameYear) {
            sfd = new SimpleDateFormat(otherSDF);
            time = sfd.format(date);
        } else {
            sfd = new SimpleDateFormat(otherYearSDF);
            time = sfd.format(date);
        }

        return time;
    }

    public static String dateTime2SimpleDate(Date dateStr) {
        try {
            SimpleDateFormat dateSDFSimple = new SimpleDateFormat("MM-dd");
            String str = dateSDFSimple.format(dateStr);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr.toString();
    }

    public static String dateTime2Date(String dateStr) {
        try {
            SimpleDateFormat dateTimeSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateTimeSDF.parse(dateStr);
            SimpleDateFormat dateSDF = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return dateSDF.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public static CharSequence getListTime(String created_at) {
        Date date = null;
        SimpleDateFormat srcDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z", Locale.US);
        SimpleDateFormat dstDateFormat = new SimpleDateFormat("MMMM dd yyyy", Locale.US);
        try {
            date = srcDateFormat.parse(created_at);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dstDateFormat.format(date);
    }

    public static String getRecentDate(Date creatTime) {
        return getRecentDate(creatTime.getTime());
    }

    public static String getRecentDate(long tempTime) {
        long intervalTime = System.currentTimeMillis() - tempTime;
        // 一分钟之内
        if (intervalTime < 60 * 1000) {
            return (intervalTime / 1000 <= 0 ? 1 : intervalTime / 1000) + "秒前";
        } else if (intervalTime >= 60 * 1000 && intervalTime < 60 * 1000 * 60) {
            // 一小时之内
            return intervalTime / 1000 / 60 + "分钟前";
        } else if (intervalTime >= 60 * 1000 * 60 && intervalTime < 60 * 1000 * 60 * 24) {
            return intervalTime / 1000 / 60 / 60 + "小时前";
        } else if (intervalTime >= 60 * 1000 * 60 * 24 && intervalTime < 60 * 1000 * 60 * 48) {
            return "一天前";
        } else if (intervalTime >= 60 * 1000 * 60 * 48 && intervalTime < 60 * 1000 * 60 * 72) {
            return "两天前";
        } else if (intervalTime >= 60 * 1000 * 60 * 72 && intervalTime < 60 * 1000 * 60 * 168) {
            return "三天前";
        } else {
            return getTime(tempTime);
        }
    }

    public static double differ(Date date1, Date date2) {
        return (double) date2.getTime() / 86400000 - (double) date1.getTime() / 86400000; // 用立即数，减少乘法计算的开销
    }

    public static String getTime2(long milliseconds) {
        Date date = new Date(milliseconds);
        boolean sameYear = false;
        String otherSDF = "MM/dd HH:mm";
        String otherYearSDF = "yyyy/MM/dd HH:mm";
        SimpleDateFormat sfd = null;
        String time = "";
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Date now = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(now);
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        if (dateCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)) {
            sameYear = true;
        } else {
            sameYear = false;
        }
        if (sameYear) {
            sfd = new SimpleDateFormat(otherSDF);
            time = sfd.format(date);
        } else {
            sfd = new SimpleDateFormat(otherYearSDF);
            time = sfd.format(date);
        }

        return time;
    }

    public static String getData(Date data) {
        SimpleDateFormat sd = new SimpleDateFormat("MM-dd");
        String dateStr = sd.format(data);
        return dateStr;
    }
}

