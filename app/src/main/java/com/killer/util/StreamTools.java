package com.killer.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/9/9.
 */
public class StreamTools {

    /**
     * 读取输入流中数据并转换为字节数组
     * @param inputStream 输入流
     * @return 字节数组
     * @throws IOException
     */
    public static byte[] read(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len ;
        while ((len = inputStream.read(buffer)) != -1){
            outputStream.write(buffer , 0 , len);
        }
        inputStream.close();
        return outputStream.toByteArray();


    }
}
