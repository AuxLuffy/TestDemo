package com.example.commonutils;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by yuandonghua on 2017/2/14.
 */

public class MacUtil {
    private static MacUtil instance;

    private MacUtil() {
    }

    public static MacUtil getInstance() {
        if (instance == null) {
            synchronized (MacUtil.class) {
                if (instance == null) {
                    instance = new MacUtil();
                }
            }
        }
        return instance;
    }

    private String mac;

//    public String getMac() {
//        if (TextUtils.isEmpty(mac)) {
//            String str = "";
//
//            try {
//                Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
//                InputStreamReader ir = new InputStreamReader(pp.getInputStream());
//                LineNumberReader input = new LineNumberReader(ir);
//
//                for (; null != str; ) {
//                    str = input.readLine();
//                    if (str != null) {
//                        mac = str.trim();// 去空格
//                        break;
//                    }
//                }
//
//                return mac;
//            } catch (IOException ex) {
//                // 赋予默认值
//                ex.printStackTrace();
//            }
//        }
//        return mac;
//    }

//    public String getMac(){
//        String str="";
//        String macSerial="";
//        try {
//            Process pp = Runtime.getRuntime().exec(
//                    "cat/sys/class/net/wlan0/address");
//            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
//            LineNumberReader input = new LineNumberReader(ir);
//            for (; null != str;) {
//                str = input.readLine();
//                if (str != null) {
//                    macSerial = str.trim();// 去空格
//                    break;
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        if (macSerial == null || "".equals(macSerial)) {
//            try {
//                return loadFileAsString("/sys/class/net/eth0/address")
//                        .toUpperCase().substring(0, 17);
//            } catch (Exception e) {
//                e.printStackTrace();
//            } }
//        return macSerial;
//    }
//    public static String loadFileAsString(String fileName) throws Exception {
//        FileReader reader = new FileReader(fileName);
//        String text = loadReaderAsString(reader);
//        reader.close();
//        return text;
//    }
//    public static String loadReaderAsString(Reader reader) throws Exception {
//        StringBuilder builder = new StringBuilder();
//        char[] buffer = new char[4096];
//        int readLength = reader.read(buffer);
//        while (readLength >= 0) {
//            builder.append(buffer, 0, readLength);
//            readLength = reader.read(buffer);
//        }
//        return builder.toString();
//    }

    /**
     * @description: 获取Mac地址
     * @author:袁东华
     * @time:2017/3/6 下午5:56
     */
    public String getMacAddressByNetworkInterface() {
        try {
            List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nis) {
                if (!ni.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = ni.getHardwareAddress();
                if (macBytes != null && macBytes.length > 0) {
                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02x:", b));
                    }
                    return res1.deleteCharAt(res1.length() - 1).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }
}
