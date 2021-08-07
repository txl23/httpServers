package com.yc.net.bean1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/*
采用更高级的API调用: 自动封装 http请求头，
   请注意观察输出的结果是没有   http的响应头信息了，只是响应的实体部分.

URL
URLConnection
HttpURLConnection

  以上三个类都是用来开发客户端.
 应用场景:    确定服务器为 web服务器
 */
public class Test7URLConnection {
    public static void main(String[] args) throws IOException {
        String urlstr;
        if (args.length == 1) {
            urlstr = args[0];
        } else {
            urlstr = "http://www.baidu.com/index.html";
            // http://www.baidu.com:80/index.html
        }
        URL u = new URL(urlstr);
        //url对象封装了更多的应用层协议的信息.
        System.out.println(u.getProtocol() + "\t" + u.getHost() + "\t" + u.getPort() + "\t" + u.getDefaultPort());
        //方案一: 获取URLConnection对象,  它自动将http信息封装 .
//        URLConnection con=u.openConnection();
//        System.out.println( con.getHeaderFields()+"\t"+  con.getContentEncoding()+"\t"+con.getContentLength() );
//        //响应的实体
//        InputStream iis=con.getInputStream();
//        byte[] bs=genByteArray( iis);
//        String str=new String(bs);
//        System.out.println(   str );

//        //方案二: 获取HttpURLConnection对象,更加针对http协议
        HttpURLConnection con2 = (HttpURLConnection) u.openConnection();   //针对http协议
        System.out.println("HttpURLConnection有更多针对http协议的信息:");
        System.out.println(con2.getContentEncoding() + "\t" + con2.getContentLength());
        System.out.println(con2.getResponseCode() + "\t" + con2.getResponseMessage());

        InputStream instream = con2.getInputStream();
        byte[] bs = genByteArray(instream);
        String str = new String(bs);
        System.out.println(str);
    }

    private static byte[] genByteArray(InputStream iis) {
        byte[] bs = new byte[1024];
        int length = -1;
        try (ByteArrayOutputStream boas = new ByteArrayOutputStream(); InputStream iis0 = iis) {
            while ((length = iis.read(bs, 0, bs.length)) != -1) {
                boas.write(bs, 0, length);
            }
            return boas.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

