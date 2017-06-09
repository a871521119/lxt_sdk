package com.lxt.sdk.http;


import android.text.TextUtils;

/**
 *token签名
 */
 class NewToken {

    public static final String DEFAULT_TOKEN = "weiyuyan666";

    private static Integer[][] hashs = new Integer[][]{
            {0,5,9,15,22,28},
            {2,8,19,25,30,31},
            {20,25,31,3,4,8},
            {25,31,0,9,13,17},
            {29,2,11,17,21,26},
            {10,15,18,29,2,3},
            {5,10,15,17,18,22},
            {8,20,22,27,19,21}};

    /**
     * 获取token
     * @param token
     * @param whetherSignature
     * @return
     */
    public static String getToken(String token,boolean whetherSignature){
        if (whetherSignature && !TextUtils.isEmpty(token)){
            return getNewToken(token);
        }
        return DEFAULT_TOKEN;
    }

    /**
     * 签名新Token
     * @param token
     * @return
     */
    private static String getNewToken(String token){
        StringBuffer newToken  = new StringBuffer();
        newToken.append(token.substring(2,3));
        newToken.append(token.substring(5,6));
        newToken.append(token.substring(8,9));
        int code = Integer.parseInt(toD(newToken.toString().trim(),16));
        int arraySubScript = code%8;
        Integer[] arr = hashs[arraySubScript];
        newToken.delete(0,newToken.length());
        for(int i=0;i<arr.length;i++){
            int possion = arr[i];
            int po = possion+1;
            newToken.append(token.substring(possion,po));
        }

        return newToken.toString().trim();
    }

    /**
     * 转换10进制
     * */
    private static String toD(String a, int b) {
        int r = 0;
        for (int i = 0; i < a.length(); i++) {
            r = (int) (r + formatting(a.substring(i, i + 1))
                    * Math.pow(b, a.length() - i - 1));
        }
        return String.valueOf(r);
    }

    private static int formatting(String a) {
        int i = 0;
        for (int u = 0; u < 10; u++) {
            if (a.equals(String.valueOf(u))) {
                i = u;
            }
        }
        if (a.equals("a")) {
            i = 10;
        }
        if (a.equals("b")) {
            i = 11;
        }
        if (a.equals("c")) {
            i = 12;
        }
        if (a.equals("d")) {
            i = 13;
        }
        if (a.equals("e")) {
            i = 14;
        }
        if (a.equals("f")) {
            i = 15;
        }
        return i;
    }
}
