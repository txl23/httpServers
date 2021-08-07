package com.yc.net.bean4;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServerManager {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket s = new Socket("localhost", 9999);

		PrintWriter pw = new PrintWriter(s.getOutputStream());

		pw.println("STOP");
		pw.flush();

		s.close();
		System.out.println("客户端断开与服务器的联接....");
	}

}

