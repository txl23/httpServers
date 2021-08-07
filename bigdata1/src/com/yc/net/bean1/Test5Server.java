package com.yc.net.bean1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Test5Server {

    public static void main(String[] args) throws IOException {
        //新建serversocket
        ServerSocket ss=new ServerSocket(8887);
        System.out.println("服务器"+ss.getLocalSocketAddress()+"启动了，监听"+ss.getLocalPort());
        boolean flag=true;
        while(  flag ) {
            System.out.println("服务器"+ss.getLocalSocketAddress()+"在等待客户端的联接");
            Socket socket = ss.accept();    //阻塞式方法, 获取一个socket
            System.out.println("一个新的客户端:"+socket.getRemoteSocketAddress()+"联接成功");
            //socket输入流
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //socket输出流
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            //键盘输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line=null;
            do{
                line=is.readLine();   //阻塞式,  从客户端取数据
                //从客户端输入
                System.out.println("客户端说:" +line );
                if( "bye".equalsIgnoreCase(line) ){
                    System.out.println("客户端主动断线，。。。");
                    break;
                }
                System.out.println("请输入你想回应客户端的话:");
                String response=br.readLine();  //阻塞式的方法
                pw.println(  response );
                pw.flush();
                if( "bye".equals(  response ) ){
                    System.out.println("服务器主动断开与客户端的联接");
                    break;
                }
            }while(   true );
            System.out.println("服务器"+socket.getLocalSocketAddress()+"断开与客户端"+socket.getRemoteSocketAddress()+"联接");

            pw.flush();
            pw.close();
            is.close();
            socket.close();
        }
        System.out.println("服务器关闭");
        ss.close();

    }

}

