package com.yc.net.bean1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;


public class Test8UDPClient {

    public static void main(String[] args) throws IOException {
        DatagramSocket ds=null;
        boolean flag=true;
        Scanner sc=new Scanner( System.in);
        //使用本机上的哪一个端口发送
        ds = new DatagramSocket(5678);  //数据报套接字    发送端端口
        do {
            System.out.println("请输入要发送的文字:");
            String s = sc.nextLine();
            //注意:  DatagramPacket的构造方法参数    三个参: 最后一个参数表示另一台机器的地址和端口
            //UDP中的每一个包都必须指明要发送的地址
            DatagramPacket dp = new DatagramPacket(
                    s.getBytes(),
                    s.getBytes().length,
                    new InetSocketAddress("127.0.0.1", 3333));  //接收发的地址和端口
            ds.send(dp);
            if(   "bye".equalsIgnoreCase(s)){
                break;
            }
        }while( flag );
        System.out.println("客户端关闭...");
        ds.close();
    }

}
