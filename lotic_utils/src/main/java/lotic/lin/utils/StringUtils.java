package lotic.lin.utils;

import java.security.MessageDigest;

/**
 * Created by Lotic.lin on 8/26/2015.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{
    /**
     * MD5 密码加密
     * @param string 需要加密的字符串
     * @return md5 String
     */
    public static String md5Encoder(String string){
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = string.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            LogUtils.printLog(null, e);
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.print(md5Encoder("pass1234"));
    }

}
