package com.yc.net.bean1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class Test9UDPServer {
    public static void main(String args[]) throws IOException{
        byte buf[]=new byte[1024];//字节数组，用于缓冲数据
        DatagramSocket ds=new DatagramSocket(3333);   //构建一个数据包socket,占用UDP的9999端口
        boolean flag=true;
        while(flag){
            DatagramPacket dp=new DatagramPacket(buf,buf.length);  //构建一个数据包对象
            //阻塞式方法
            ds.receive(dp);   //用这个9999端口来接收数据，存在dp数据包对象中的buf缓冲空间中
            System.out.println(   dp.getLength() );

            System.out.println(new String(buf,0,dp.getLength())); //将字节数组转成字符串输出
        }
        ds.close();
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

