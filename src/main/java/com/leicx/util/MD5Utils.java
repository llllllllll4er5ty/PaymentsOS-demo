package com.leicx.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Create by mabaobao-web-archetype
 */
public class MD5Utils {
    private static Logger LOG = LoggerFactory.getLogger("debugAppender");
    private static Logger DATABASE = LoggerFactory.getLogger("DATABASE");

    /**
     * md5
     *
     * @param src
     * @return
     */

    public static String md5(byte src[]) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src);
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();// 32位的加密
        } catch (NoSuchAlgorithmException e) {
            LOG.error("md5 fail：", e);
            DATABASE.error("md5 fail：", e);
        }
        return result;
    }

    public static String md5(String plainText) {
        return md5(plainText.getBytes());
    }

    /**
     * 对给定的字符串进行加密
     *
     * @param s
     * @return 加密后的16进制的字符串
     */
    public final static String EncoderByMd5(String s) {
        if (LOG.isInfoEnabled()) {
            LOG.info("encrypt md5：" + s);
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            // 使用MD5创建MessageDigest对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            LOG.error("encrypt md5 fail：" + s, e);
            DATABASE.error("encrypt md5 fail：" + s, e);
            return null;
        }
    }

    /**
     * chck password
     *
     * @param newStr    no encrypt password
     * @param oldMD5Str
     * @return
     */
    public final static boolean checkMD5(String newStr, String oldMD5Str) {
        String temp = EncoderByMd5(newStr);
        if (temp != null && temp.equals(oldMD5Str)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        //cae310b97d74f3b905da3229550f4bfa,salt=agz&&M,
        String str = "lcx";
        String md5 = md5(str);
        String md5_16 = EncoderByMd5(str);
        System.out.println(md5);
        System.out.println(md5_16);
    }
}
