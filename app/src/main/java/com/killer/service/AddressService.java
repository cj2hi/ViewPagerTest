package com.killer.service;

import android.content.Context;
import android.util.Xml;

import com.killer.util.StreamTools;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**根据手机号查询其归属地
 * Created by Administrator on 2016/9/9.
 */
public class AddressService {

    /**
     * 查询归属地
     * @param context 传送的context
     * @param mobile 查询的手机号
     * @return 返回查询到的归属地信息
     * @throws Exception
     */
     public static String getAddress(Context context,String mobile) throws Exception {
         InputStream is = context.getResources().getAssets().open("mobile.xml");
         // 调用工具类中读取字节流字符数组

         byte[] date = StreamTools.read(is);
         String soap = new String(date);


         soap = soap.replaceAll("\\$mobile", mobile); // 将占位字符串替换成手机号

         byte[] entity = soap.getBytes();


         // 路径是返回查询结果的网站地址
         String path = "http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx";
         HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
         conn.setConnectTimeout(5000);
         conn.setRequestMethod("POST");
         conn.setDoOutput(true);
         conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
         conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
         conn.getOutputStream().write(entity);
         if(conn.getResponseCode() == 200){// 如果返回结果成功解析XML
             return parseSOAP(conn.getInputStream());
         }

         return null;
     }

    /**
     * 解析XML文件中的手机归属地信息,下面是XML文件模板
     * <?xml version="1.0" encoding="utf-8"?>
     *<soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
     *<soap12:Body>
     *<getMobileCodeInfoResponse xmlns="http://WebXml.com.cn/">
     *<getMobileCodeInfoResult>string</getMobileCodeInfoResult>
     *</getMobileCodeInfoResponse>
     *</soap12:Body>
     *</soap12:Envelope>
     *
     * @param inputStream XML文件输入流
     * @return 取得的string值
     */
    private static String parseSOAP(InputStream inputStream) throws XmlPullParserException, IOException {


        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(inputStream,"UTF-8");
        int event = pullParser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_TAG:
                    if ("getMobileCodeInfoResult".equals(pullParser.getName())) {
                        return pullParser.nextText();
                    }
                    break;
            }
            event = pullParser.next(); //切换到下一个
        }
        return null;

    }
}
