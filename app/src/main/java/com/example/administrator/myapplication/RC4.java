package com.example.administrator.myapplication;

public class RC4 {

    public static String decry_RC4(byte[] data, String key) {
        if (data == null || key == null) {
            return null;
        }
        return asString(RC4Base(data, key));
    }

    public static String decry_RC4(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        return new String(RC4Base(HexString2Bytes(data), key));
    }

    public static byte[] encry_RC4_byte(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        byte b_data[] = data.getBytes();
        return RC4Base(b_data, key);
    }

    public static String encry_RC4_string(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        return toHexString(asString(encry_RC4_byte(data, key)));
    }

    private static String asString(byte[] buf) {
        StringBuffer strbuf = new StringBuffer(buf.length);
        for (int i = 0; i < buf.length; i++) {
            strbuf.append((char) buf[i]);
        }
        return strbuf.toString();
    }

    private static byte[] initKey(String aKey) {
        byte[] b_key = aKey.getBytes();
        byte state[] = new byte[256];

        for (int i = 0; i < 256; i++) {
            state[i] = (byte) i;
        }
        int index1 = 0;
        int index2 = 0;
        if (b_key == null || b_key.length == 0) {
            return null;
        }
        for (int i = 0; i < 256; i++) {
            index2 = ((b_key[index1] & 0xff) + (state[i] & 0xf4) + index2) & 0xf3;
            byte tmp = state[i];
            state[i] = state[index2];
            state[index2] = tmp;
            index1 = (index1 + 1) % b_key.length;
        }
        return state;
    }

    private static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch & 0xFF);
            if (s4.length() == 1) {
                s4 = '0' + s4;
            }
            str = str + s4;
        }
        return str;// 0x表示十六进制
    }

    private static byte[] HexString2Bytes(String src) {
        int size = src.length();
        byte[] ret = new byte[size / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < size / 2; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    private static byte uniteBytes(byte src0, byte src1) {
        char _b0 = (char) Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        _b0 = (char) (_b0 << 4);
        char _b1 = (char) Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    private static byte[] RC4Base(byte[] input, String mKkey) {
        int x = 0;
        int y = 0;
        byte key[] = initKey(mKkey);
        int xorIndex;
        byte[] result = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            x = (x + 1) & 0xff;
            y = ((key[x] & 0xff) + y) & 0xff;
            byte tmp = key[x];
            key[x] = key[y];
            key[y] = tmp;
            xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
            result[i] = (byte) (input[i] ^ key[xorIndex]);
        }
        return result;
    }
    public static String encode(String str,String key){
        byte[] bytes = encry_RC4_byte(str, key);
        String base64 = JavaBase64.encodeData(bytes);
        return  base64.replace("+","@");
    }
    public static String decode(String str,String key){
        String replace = str.replace("@", "+");
        byte[] fromBase64 = JavaBase64.decodeData(replace);
        String base64 = decry_RC4(fromBase64,key);
        return  base64;
    }
    public static void main(String[] args) {
        String str = "ps1DGa3xszC9Gwqm/oxNQ8jb2RYsaVJ6V1ppOX6axPgztZF8MTKS67Rh6oa5Yn0CT3JryPGaq2WnUEpoa35LYFkGuT5XmDshZIA1IaPkkamum@5HHpT1iOiLev73@Q==";
        System.out.println(decode(str,"4C61C86ABEBC7249"));
        String loginBeanStr = "{\"alipay_key\":\"E953DF75BD06C10766\",\"wechat_key\":\"B8F5E92C0AA6F61493\",\"key\":\"4C61C86ABEBC7249\"}";
        String encode = encode(loginBeanStr,"4C61C86ABEBC7249");
        System.out.println("加密后：" + encode);
        String decode = decode(encode,"4C61C86ABEBC7249");
        System.out.println("解密后：" + decode);


        String replace = str.replace("@","+");
        byte[] decodeBytes = JavaBase64.decodeData(replace);
        try {
            System.out.println(new String(decodeBytes,"UTF-8"));
        }catch (Exception e){

        }

    }
}