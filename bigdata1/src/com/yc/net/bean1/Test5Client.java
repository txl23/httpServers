package com.yc.net.bean1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*
实现单线程的服务器与客户端的通讯.
 */
public class Test5Client {

    public static void main(String[] args) throws UnknownHostException, IOException {
        System.out.println("客户端启动");
        Socket s = new Socket(InetAddress.getLocalHost(), 8887);
        System.out.println("客户端联接服务器" + s.getRemoteSocketAddress() + "成功");

        InputStreamReader isr = new InputStreamReader(System.in);   //System.in:系统标准输入字节流
        // InputStreamReader: 将字节流转字符流
        BufferedReader br = new BufferedReader(isr);   //BufferedReader: 缓冲字符流
        // 装饰模式 :    一个字节-> 一个字符 -> 一行字符
        //从socket套接字中取的流
        OutputStream os = s.getOutputStream();//输出字节流
        PrintWriter pw = new PrintWriter(os);//输出字符流      输出一个字节-> 输出一个字符 -> 输出一行字符
        //从socket套接字中取的输入流，用来监控服务器发出的话
        InputStream iss = s.getInputStream();
        BufferedReader bris = new BufferedReader(new InputStreamReader(iss));   // BufferedReader
        boolean flag=true;
        do {
            System.out.println("请输入你想对服务器说的话:");
            String line = br.readLine();   //按行从键盘输入
            pw.println(line);   //按行发给服务器
            pw.flush();
            System.out.println("客户端发送成功:" + line);
            if(  "bye".equalsIgnoreCase(line)  ){
                System.out.println(  "客户端主动断开与服务器的联接..."  );
                break;
            }
            //接收服务器的回应
            String serverwords = bris.readLine();
            System.out.println("服务器说:" + serverwords);
            if ("bye".equalsIgnoreCase(serverwords)) {
                System.out.println("服务器要求断开与客户端的联接");
                break;
            }
        }while(  flag );
        System.out.println(  "客户端主动断开与服务器的联接成功..."  );
        pw.flush();
        pw.close();
        bris.close();
        s.close();
    }

}

