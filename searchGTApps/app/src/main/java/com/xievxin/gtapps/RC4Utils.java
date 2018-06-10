package com.xievxin.gtapps;

public class RC4Utils {

    private static final String TAG = "RC4Utils";

    public static String getKey(String data) {
        return null;
    }

    public static byte[] dencrypt(byte[] data, String key) {
        return rc4(data, key.getBytes());
    }

    public static byte[] encrypt(byte[] data, String key) {
        return rc4(data, key.getBytes());
    }

    public static byte[] rc4(byte[] data, byte[] key) {
        if (!isKeyValid(key)) {
            throw new IllegalArgumentException("key is fail!");
        }

        if (data.length < 1) {
            throw new IllegalArgumentException("data is fail!");
        }

        int[] ints = new int[256];

        for (int i = 0; i < ints.length; i++) {
            ints[i] = i;
        }

        int j = 0;
        for (int i = 0; i < ints.length; i++) {
            j = (j + ints[i] + (key[i % key.length] & 0xFF)) % 256;
            swap(ints, i, j);
        }

        // PRGA
        int i = 0;
        j = 0;

        byte[] encodeData = new byte[data.length];

        for (int x = 0; x < encodeData.length; x++) {
            i = (i + 1) % 256;
            j = (j + ints[i]) % 256;
            swap(ints, i, j);
            int k = ints[(ints[i] + ints[j]) % 256];
            encodeData[x] = (byte) (data[x] ^ k);
        }
        return encodeData;
    }

    public static boolean isKeyValid(byte[] key) {
        int len = key.length;
        int num = 0;// 0x0E计数
        if (len > 0 && len <= 256) {
            for (int i = 0; i < len; i++) {
                if ((key[i] & 0xFF) == 0x0E) {
                    num++;
                    if (num > 3) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static void swap(int[] source, int a, int b) {
        int tmp = source[a];
        source[a] = source[b];
        source[b] = tmp;
    }
}
