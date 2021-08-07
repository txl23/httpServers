package com.yc.net.bean3;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	public static void main(String[] args)  {
		System.out.println("客户端启动");
		InputStreamReader isr = new InputStreamReader(System.in);   //System.in:系统标准输入字节流
		BufferedReader br = new BufferedReader(isr);   //BufferedReader: 缓冲字符流
		try(Socket s = new Socket(InetAddress.getLocalHost(), 8887);
			OutputStream os = s.getOutputStream();//输出字节流
			//PrintWriter pw = new PrintWriter(os);//输出字符流      一个字节-> 一个字符 -> 一行字符
			//输入流，用来监控服务器发出的话
			InputStream iss = s.getInputStream();
			BufferedReader bris = new BufferedReader(new InputStreamReader(iss));   // BufferedReader
		){
			System.out.println("客户端联接" + s.getRemoteSocketAddress() + "成功");
			boolean flag=true;
			String command=null;
			String line=null;
			byte[] bs=null;
			do {
				if(  "SNAPSHOT".equals(command)){
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					System.out.println("The width and the height of the screen are " + screenSize.getWidth() + " x " + screenSize.getHeight());
					// 截取屏幕
				    BufferedImage image = new Robot().createScreenCapture(new Rectangle(screenSize));
					ImageIO.write(image,"png",os);
				}else{
					System.out.println("请输入你想对服务器说的话:");
					line = br.readLine();
					bs=line.getBytes();
					os.write(   bs );   //按行发
					os.flush();
					System.out.println("客户端发送成功:" + line);
					if(  "bye".equalsIgnoreCase(line)  ){
						System.out.println(  "客户端主动断开与服务器的联接..."  );
						break;
					}
				}
				//接收服务器的回应
				String serverwords = bris.readLine();
				System.out.println("服务器说:" + serverwords);
				if ("bye".equalsIgnoreCase(serverwords)) {
					System.out.println("服务器要求断开与客户端的联接");
					break;
				}else if("SHUTDOWN".equals(serverwords)){
					// 一个java程序 与它的运行环境之间唯一的一个接口
					//     单例:  它不能new,只能通过 static 方法获取唯一的实例
					//  er汉单例
					Runtime r = Runtime.getRuntime();
					//r.exec("shutdown -s -t -f 300");//300秒后关机
					r.exec("shutdown -s -t -f 3"); //取消关机
				}else if( "SNAPSHOT".equals( serverwords)){
					command=serverwords;
				}
			}while(  flag );
			os.flush();
			System.out.println(  "客户端主动断开与服务器的联接成功..."  );
		}catch( Exception ex){
			ex.printStackTrace();
		}
	}

}
