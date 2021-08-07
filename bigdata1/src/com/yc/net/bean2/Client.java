package com.yc.net.bean2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args)  {
		System.out.println("客户端启动");
		InputStreamReader isr = new InputStreamReader(System.in);   //System.in:系统标准输入字节流
		BufferedReader br = new BufferedReader(isr);   //BufferedReader: 缓冲字符流
		try(Socket s = new Socket(InetAddress.getLocalHost(), 8887);
			OutputStream os = s.getOutputStream();//输出字节流
			PrintWriter pw = new PrintWriter(os);//输出字符流      一个字节-> 一个字符 -> 一行字符
			//输入流，用来监控服务器发出的话
			InputStream iss = s.getInputStream();
			BufferedReader bris = new BufferedReader(new InputStreamReader(iss));   // BufferedReader
		){
			System.out.println("客户端联接" + s.getRemoteSocketAddress() + "成功");
			boolean flag=true;
			do {
				System.out.println("请输入你想对服务器说的话:");
				String line = br.readLine();
				pw.println(line);   //按行发
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
			pw.flush();
			System.out.println(  "客户端主动断开与服务器的联接成功..."  );
		}catch( Exception ex){
			ex.printStackTrace();
		}
	}

}
