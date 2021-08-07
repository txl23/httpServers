package com.yc.net.bean1;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Test6WebGetClient {

    /**
     * 本程序是一个客户端，用来建立到一个web主机的连接，服务器地址从args中取得
     * @throws IOException
     * @throws UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        String host;        //主机地址
        String resource;    //资源名
        if (args.length == 2) {
            host = args[0];
            resource = args[1];
        } else {
            host = "baidu.com";
            resource = "/index.html";
        }
        //打开套接字
        Socket s = new Socket(host, 80);
        System.out.println("联接baidu服务器"+ s.getRemoteSocketAddress()+"成功");
        InputStream instream = s.getInputStream();
        OutputStream outstream = s.getOutputStream();
        //将输出的字节流转为了输出的打印字符流, 一个字节 -> 一个字符 -> 一行字符.
        PrintWriter out = new PrintWriter(outstream);
        //应用层: http的请求
        String command = "GET " + resource + " HTTP/1.0\n\n";
        out.println(command);
        out.flush();
         //方案一: 不是好方案,这种方案  会有乱码
//        byte[] bs=new byte[1024];
//        int length=-1;
//        while(  (length=instream.read(bs,0,bs.length))!=-1){
//            //System.out.println(  length );
//            String str=new String( bs, 0,length);
//            System.out.println( str );
//        }
//        instream.close();
        //方案二:  利用内存先将数据一次性的存到内存，再从内存取出，一次性的转成String
        //获取结果
        byte[] bs = genByteArray(instream);
        String str = new String(bs, "gbk");
        System.out.println(str);
        s.close();
    }

    private static byte[] genByteArray(InputStream iis) {
        byte[] bs = new byte[1024];
        int length = -1;
        //写法一: 老的try...catch...finally...
//        ByteArrayOutputStream boas=new ByteArrayOutputStream();  //字节数组输出流
//        try {
//            while ((length = iis.read(bs, 0, bs.length)) != -1) {
//                boas.write(bs, 0, length);
//            }
//        }catch( Exception ex){
//            ex.printStackTrace();
//        }finally{
//            try {
//                iis.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return boas.toByteArray();
        //写法二: 语法糖   try...catch新写法
        try (ByteArrayOutputStream boas = new ByteArrayOutputStream();
             InputStream iis0 = iis) {  //只有写在try()中的流才会自动关闭.
            while ((length = iis.read(bs, 0, bs.length)) != -1) {
                //System.out.println( "读取到字节数:"+  length );
                boas.write(bs, 0, length);
            }
            return boas.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}

