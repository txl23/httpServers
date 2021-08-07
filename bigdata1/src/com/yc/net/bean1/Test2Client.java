package com.yc.net.bean1;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test2Client {
    public static int port=9999;

    public static void main(String[] args)  {
        Socket client=null;
        OutputStream os=null;
        DataOutputStream dos=null;
        try {
            client = new Socket("localhost", port);   // 客户端联接服务器 localhost:9999，并随机选用一个端口做为发送端口。
            System.out.println(client.getLocalSocketAddress() + "联接上了服务器" + client.getRemoteSocketAddress());

//        Thread.sleep(30000);
             os = client.getOutputStream();   //输出字节流
             dos = new DataOutputStream(os);   //转成数据输出流(  处理基本的数据类型 )
            dos.writeUTF("hello server  abc");
            dos.flush();
            os.flush();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

