package com.lxt.util;

import java.security.MessageDigest;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/28 14:25
 * @description :
 */
public class MD5 {
    /**
     * MD5加密
     * @param source
     * @return
     * @throws Exception
     */
    public static String MD5(String source) throws Exception {
        String resultHash = null;
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] result = new byte[md5.getDigestLength()];
        md5.reset();
        md5.update(source.getBytes("UTF-8"));
        result = md5.digest();
        StringBuffer buf = new StringBuffer(result.length * 2);
        for (int i = 0; i < result.length; i++) {
            int intVal = result[i] & 0xff;
            if (intVal < 0x10) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(intVal));
        }
        resultHash = buf.toString();
        return resultHash.toString();
    }
}
