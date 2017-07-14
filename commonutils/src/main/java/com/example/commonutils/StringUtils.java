package com.example.commonutils;

/**
 * Created by chongyangyang on 2016/9/27.
 */

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 */
public class StringUtils {
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    private final static Pattern phone = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
//    private final static Pattern phone = Pattern
//            .compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
    public final static int MOBILE = 0;
    public final static int EMAIL = 1;
    public final static int ID_CARD = 2;
    public final static int NAME = 3;
    public final static int ADDR = 4;


    /**
     * @data 2016/1/5 9:40
     * @desc 指定时间之前的所有日期
     */
    public static List<Long> getDatesBetweenTwoDate(Date beginDate) {
        Date endDate = new Date();
        List<Date> lDate = new ArrayList<Date>();
        List<Long> returnlist = new ArrayList<Long>();
        lDate.add(beginDate);// 把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
//        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
//        for (int i = 0; i < lDate.size(); i++) {
//            returnlist.add(sdf.format(lDate.get(i)));
//        }
        for (int i =0;i<lDate.size();i++){
            returnlist.add(lDate.get(i).getTime()/1000L);
        }
        return returnlist;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 判断是不是一个合法的用户名信息:
     * 只限字母、数字和下划线
     */
    public static boolean isValidUser(String user){
        if (user == null || user.length() <= 0)
            return false;
//		Pattern p = Pattern.compile("\\w+");
        Pattern p = Pattern.compile("[a-zA-Z_0-9]+");
        Matcher m = p.matcher(user);
        return m.matches();
    }

    /**
     * 判断是不是一个合法的密码信息
     */
    public static boolean isValidPwd(String pwd){
        if (pwd == null || pwd.length() < 6 || pwd.length() > 20)
            return false;
        Pattern p = Pattern.compile("([a-zA-Z_0-9]|\\p{Punct})+");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }


    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 将一个InputStream流转换成字符串
     *
     * @param is
     * @return
     */
    public static String toConvertString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                res.append(line);
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                    isr.close();
                }
                if (null != read) {
                    read.close();
                    read = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }

    public static byte[] getBytesFromStream(InputStream is) throws IOException {
        int len;
        int size = 1024;
        byte[] buf;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        buf = new byte[size];
        while((len = is.read(buf, 0, size)) != -1) {
            bos.write(buf, 0, len);
        }
        buf = bos.toByteArray();
        return buf;
    }

    public static void saveBytesToFile(byte[] bytes, String path) {
        FileOutputStream fileOuputStream = null;
        try {
            fileOuputStream = new FileOutputStream(path);
            fileOuputStream.write(bytes);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } finally{
            if (fileOuputStream != null) {
                try {
                    fileOuputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
       * 检查是否存在sd卡
       */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 格式化float小数点保留两位小数
     * @param f
     * @return
     */
    public static String decimalFormat(float f){
        DecimalFormat fnum = new DecimalFormat("##0.00");  //保留小数点后两位
        return fnum.format(f);
    }

    /**
     * 根据时间戳转换日期
     *
     * @param mTimeStamp 时间戳
     * @return
     */
    public static String getLongTimeStamp(long mTimeStamp) {
        String strTimeDesc = getDate(mTimeStamp + "", "MM-dd HH:mm");
        return strTimeDesc;
    }

    public static String getLongTimeStamp2(long mTimeStamp) {
        String strTimeDesc = getDate(mTimeStamp + "", "MM月dd日 HH:mm");
        return strTimeDesc;
    }


    public static String getLongTimeStamp3(long mTimeStamp) {
        String strTimeDesc = getDate(mTimeStamp + "", "MM-dd HH:mm:ss");
        return strTimeDesc;
    }

    /**
     * 根据时间戳转换日期
     *
     * @param mTimeStamp 十位时间戳
     * @return
     */
    public static String getLongTimeDate(long mTimeStamp) {
        String strTimeDesc = getDate(mTimeStamp + "", "yyyy-MM-dd");
        return strTimeDesc;
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.CHINA);
        return df.format(new Date());
    }


    /**
     * 时间戳获取日期格式
     *
     * @param mTimeStamp 时间戳(以秒为单位的十位字符串)
     * @param pattern    格式化条件
     * @return
     */
    public static String getDate(String mTimeStamp, String pattern) {
        long changeTime = Long.parseLong(mTimeStamp);
        return getDate(changeTime,pattern);
    }

    public static String getDate(long timeStamp, String pattern){
        String strTimeDesc;
        SimpleDateFormat format = null;
        long messageTimeStamp = timeStamp * 1000;
        try {
            format = new SimpleDateFormat(pattern);
            strTimeDesc = format.format(messageTimeStamp);
        } catch (IllegalArgumentException exception) {
            strTimeDesc = "格式有误";
        }
        return strTimeDesc;
    }


    /**
     * 时间戳获取上下午时段
     *
     * @param mTimeStamp 时间戳( 以秒为单位的十位字符串)
     * @return
     */
    public static String getCourseNoon(String mTimeStamp) {
        String strTimeDesc = getDate(mTimeStamp, "HH:mm");
        if (Integer.parseInt(strTimeDesc.substring(0, 2)) < 12) {
            strTimeDesc = "上午";
        } else {
            strTimeDesc = "下午";
        }
        return strTimeDesc;
    }

    //导致TextView异常换行的原因：安卓默认半角字符不能为第一行以后每行的开头字符，因为数字、字母为半角字符
    //所以我们只需要将半角字符转换为全角字符即可，方法如下
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 判断是不是一个合法的手机号码
     */
    public static boolean isPhone(String phoneNum) {
        if (phoneNum == null || phoneNum.trim().length() == 0)
            return false;
        return phone.matcher(phoneNum).matches();
    }

    /**
     * 检查字符串是否匹配对应规则
     *
     * @param str      待匹配的字符串
     * @param typeCode 规则类型
     * @return boolean
     * @author cxy
     */
    public static boolean isMatch(String str, int typeCode) {
        boolean isMatch = false;
        switch (typeCode) {
            case MOBILE://是否符合手机号格式
                isMatch = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9]))\\d{8}$").matcher(str).matches();
                break;
            case EMAIL://是否符合Email格式
                isMatch = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*").matcher(str).matches();
                break;
            case ID_CARD://是否符合身份证号码格式（15或18位）
                isMatch = Pattern.compile("^(\\d{6})(((1[8,9])|(20))\\d{2})(\\d{2})(\\d{2})(\\d{3})([0-9]|x|X)$").matcher(str).matches()
                        || Pattern.compile("^(\\d{6})(\\d{2})((0[1,2,3,4,5,7,8,9])|(1[0,1,2]))(([0][1,2,3,4,5,7,8,9])|([1,2]/d)|(3[0,1]))(\\d{3})$").matcher(str).matches();
                break;
            case NAME://是否符合姓名格式（字母或汉字）
                isMatch = Pattern.compile("^[\\u4e00-\\u9fa5_a-zA-Z]+$").matcher(str).matches();
                break;
            case ADDR://是否符合地址格式（字母、汉字、数字、-）
                isMatch = Pattern.compile("^[\\u4e00-\\u9fa5a-zA-Z0-9\\-\\s]+$").matcher(str).matches();
                break;
            default:
                break;
        }
        return isMatch;
    }

    /**
     * 判断一串字符串中是否包含特殊字符
     * @param string
     * @return
     */
    public static boolean isConSpeCharacters(String string){
        String regex = "^[a-z0-9A-Z/-]+$";
        return !string.matches(regex);
    }



    public static boolean containsEmj(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i+1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c || hs == 0x2b1b || hs == 0x2b50|| hs == 0x231a ) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() -1) {
                    char ls = source.charAt(i+1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return  isEmoji;
    }

    /**
     * 检测是否有特殊字符
     * @param string
     * @return 一旦含有就抛出
     */
    public static boolean isConSpe(String string){
        String regex = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$";
        return !string.matches(regex);
    }


    public static boolean containsEmoji(String source) {
        if (source.isEmpty()) {
            return false;
        }

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }

        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }


    /**
     * 判断是否包含汉字
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * String转double
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static double toDouble(String obj) {
        try {
            return Double.parseDouble(obj);
        } catch (Exception e) {
        }
        return 0D;
    }

    /**
     * 判断一个字符串是不是数字
     */
    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获取AppKey
     */
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (NameNotFoundException e) {

        }
        return apiKey;
    }

    /**
     * 获取手机IMEI码
     */
    public static String getPhoneIMEI(Activity aty) {
        TelephonyManager tm = (TelephonyManager) aty
                .getSystemService(Activity.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * MD5加密
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * KJ加密
     */
    public static String KJencrypt(String str) {
        char[] cstr = str.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char c : cstr) {
            hex.append((char) (c + 5));
        }
        return hex.toString();
    }

    /**
     * KJ解密
     */
    public static String KJdecipher(String str) {
        char[] cstr = str.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char c : cstr) {
            hex.append((char) (c - 5));
        }
        return hex.toString();
    }

    /**
     * 随机生成长度为8-16个字符
     *
     * @return
     */
    public static String getRandomString() { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        int length = random.nextInt(8) + 8;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    /**
     * 判断是否为汉字
     *
     * @param str
     * @return
     */
    public static boolean vd(String str) {

        char[] chars = str.toCharArray();
        boolean isGB2312 = false;
        for (int i = 0; i < chars.length; i++) {
            byte[] bytes = ("" + chars[i]).getBytes();
            if (bytes.length == 2) {
                int[] ints = new int[2];
                ints[0] = bytes[0] & 0xff;
                ints[1] = bytes[1] & 0xff;

                if (ints[0] >= 0x81 && ints[0] <= 0xFE &&
                        ints[1] >= 0x40 && ints[1] <= 0xFE) {
                    isGB2312 = true;
                    break;
                }
            }
        }
        return isGB2312;
    }

    /**
     * 加减按钮计算方法
     *
     * @param max   最大值
     * @param isAdd true为+，false为-
     */
    public static int setCountResult(TextView add, TextView sub, TextView numTv, int max, boolean isAdd) {
        int count = Integer.parseInt(numTv.getText().toString());
        int newCount = isAdd ? count + 1 : count - 1;
        numTv.setText(newCount + "");
        if (newCount == 1) {
            sub.setEnabled(false);
        } else {
            sub.setEnabled(true);
        }
        if (newCount == max) {
            add.setEnabled(false);
        } else {
            add.setEnabled(true);
        }
        return newCount;
    }

    /**
     * 保留两位小数的格式化方法
     *
     * @param num
     */
    public static String format(double num) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(num);
    }

    /**
     * 有小数保留两位，没有取整
     *
     * @param num
     */
    public static String formatInt(double num) {
        DecimalFormat df = new DecimalFormat("#0.00");
        String format = df.format(num);
        String[] split = format.split("\\.");
        if (split[1].equals("00")) {
            return split[0];
        } else if (split[1].endsWith("0")) {
            return split[0] + "." + split[1].charAt(0);
        } else {
            return format;
        }
    }

    /**
     * 有小数则全部保留，如果没有小数则取整
     * @param num
     * @return
     */
    public static String formatDoubleToString(double num) {
        String str = String.valueOf(num);
        String[] split = str.split("\\.");
        if(split.length>1){
            if("0".equals(split[1])){
                return split[0];
            }else {
                return str;
            }
        }else {
            return str;
        }

    }

    /**
     * 获取首字符字体较小的SpannableString对象
     *
     * @param count
     * @return
     */
    public static SpannableString getSpannableText(String count) {
        SpannableString spanStr = new SpannableString(count);
        spanStr.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spanStr;
    }

    /**
     * 将String字符串转换成SpannableString
     * @param txt
     * @param start
     * @param end
     * @param size
     * @return
     */
    public static SpannableString stringFormatSpannable(String txt, int start, int end, float size){
        SpannableString sp = new SpannableString(txt);
        sp.setSpan(new RelativeSizeSpan(size),start,end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return sp;
    }

    public static SpannableString stringFromatForsize(String txt, int start, int end, int size){
        SpannableString sp = new SpannableString(txt);
        sp.setSpan(new AbsoluteSizeSpan(size, true),start,end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return sp;
    }



    public static String parserMoney(String money){
        if (money.endsWith(".00") || money.endsWith(".0")){
            money = money.split("\\.")[0];
        }
        return money;
    }

    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }
}
