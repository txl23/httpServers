package com.yc.net.bean1;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Test2Server {

    public static void main(String[] args) throws IOException {
        ServerSocket server=new ServerSocket(9999);   //服务器临听的端口为9999
        System.out.println("服务器"+server.getLocalSocketAddress()+"启动了，监听"+server.getLocalPort());
        while(true){
            System.out.println("服务器"+server.getLocalSocketAddress()+"在等待客户端的联接");
            Socket s=server.accept();     //阻塞式方法   ->  Thread  : 耗时，阻塞式的操作
            System.out.println("一个新的客户端:"+s.getRemoteSocketAddress()+"联接成功");
            DataInputStream dis=new DataInputStream(      s.getInputStream()    );
            String response=dis.readUTF();
            System.out.println(    response  );
            dis.close();
            s.close();
            System.out.println(  "客户端:"+   s.getRemoteSocketAddress()+"联接完毕，掉线");
        }
    }

}
