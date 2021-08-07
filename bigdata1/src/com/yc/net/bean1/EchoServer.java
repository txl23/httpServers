package com.yc.net.bean1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


//制作一个服务器端，监听8189端口. 采用telnet来联接这台服务器.
public class EchoServer {

    public static void main(String[] args) throws IOException {
        Socket incoming=null;
        try {
            ServerSocket s=new ServerSocket(8189);
            incoming=s.accept();
            InputStream inStream=incoming.getInputStream();
            OutputStream outStream=incoming.getOutputStream();
            Scanner in=new Scanner(inStream);
            PrintWriter out=new PrintWriter(outStream,true);   //true 表明自动刷新
            out.println("hello! enter bye to exit");
            boolean done=false;
            while(!done&&in.hasNextLine()){
                String line=in.nextLine();
                out.println(line);
                if(line.trim().equals("bye")){
                    done=true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            incoming.close();
        }

    }

}

