package com.zhph.commonlibrary.utils;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class StringUtil {

    public static String Decode64(String string) throws UnsupportedEncodingException {
        String encode64String = Base64.encodeToString(string.getBytes("utf-8"), 0);
        String head3 = encode64String.substring(0, 3);
        String end3 = encode64String.substring(encode64String.length() - 3, encode64String.length());
        String center = encode64String.substring(3, encode64String.length() - 3);
        StringBuffer stringBuffer = new StringBuffer().append(end3);
        stringBuffer.append(center);
        stringBuffer.append(head3);
        return stringBuffer.toString();
    }

    /**
     * 字符串的压缩
     *
     * @param str 待压缩的字符串
     * @return 返回压缩后的字符串
     * @throws IOException
     */
    public static String compress(String str) throws IOException {
        if (null == str || str.length() <= 0) {
            return str;
        }
        // 创建一个新的 byte 数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 使用默认缓冲区大小创建新的输出流
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        // 将 b.length 个字节写入此输出流
        gzip.write(str.getBytes());
        gzip.close();
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toString("ISO-8859-1");
    }

    /**
     * 字符串的解压
     *
     * @param str 对字符串解压
     * @return 返回解压缩后的字符串
     * @throws IOException
     */
    public static String unCompress(String str) throws IOException {
        if (null == str || str.length() <= 0) {
            return str;
        }
        // 创建一个新的 byte 数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
        // 使用默认缓冲区大小创建新的输入流
        GZIPInputStream gzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n = 0;
        while ((n = gzip.read(buffer)) >= 0) {// 将未压缩数据读入字节数组
            // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte数组输出流
            out.write(buffer, 0, n);
        }
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toString("GBK");
    }

    public static boolean isBlankOrNull(String str) {
        if (null == str) return true;
        //return str.length()==0?true:false;
        return str.length() == 0;
    }

    /**
     * 清除数字和空格
     */
    public static String cleanBlankOrDigit(String str) {
        if (isBlankOrNull(str)) return "null";
        return Pattern.compile("\\d|\\s|,").matcher(str).replaceAll("");
    }
}
