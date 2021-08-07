package com.yc.net.bean1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class Test4Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server=new ServerSocket(9997);
        System.out.println("服务器"+server.getLocalSocketAddress()+"启动了，监听"+server.getLocalPort());
        while(true){
            System.out.println("服务器"+server.getLocalSocketAddress()+"在等待客户端的联接");
            Socket s=server.accept();
            System.out.println("一个新的客户端:"+s.getRemoteSocketAddress()+"联接成功");
            OutputStream os=s.getOutputStream();
            DataOutputStream dos=new DataOutputStream(os);
            DataInputStream dis=new DataInputStream(s.getInputStream());
            //服务器要先接收
            String ss=null;
            if((ss=dis.readUTF())!=null){
                System.out.println("客户端"+s.getRemoteSocketAddress()+"的问候为:"+ss);
            }
            dos.writeUTF(ss+    new Date());
            dos.flush();
            os.flush();
            System.out.println("服务器回应成功");
            dos.close();
            os.close();
            dis.close();
            s.close();
        }

    }

}
