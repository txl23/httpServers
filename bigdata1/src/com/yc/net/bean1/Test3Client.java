package com.yc.net.bean1;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Test3Client {

    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket s=new Socket(InetAddress.getLocalHost(),9999);
        InputStream is=s.getInputStream();
        DataInputStream dis=new DataInputStream(is);

        String ss=dis.readUTF();
        System.out.println(  "服务器"+ s.getRemoteSocketAddress() +"对客户端"+s.getLocalSocketAddress()+"说:"+ ss);
        dis.close();
        is.close();
    }

}
