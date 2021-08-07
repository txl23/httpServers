package com.yc.net.bean2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author 张影 QQ:1069595532  WX:zycqzrx1
 * @date Apr 3, 2020
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8887);
        System.out.println("服务器启动了...监听:" + ss.getLocalPort() + "端口");
        while (true) {
            Socket s = ss.accept();
            Thread t = new Thread(new TalkTask(s));
            t.start();
        }
    }
}
//任务类
class TalkTask implements Runnable {
    private Socket s;
    private Scanner sc = new Scanner(System.in);   //键盘输入    相当于前面的   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public TalkTask(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        //socket输入流   is
        //socket输出流   pw
        //键盘输入流  br
        try (Socket s = this.s;
             BufferedReader is = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter pw = new PrintWriter(s.getOutputStream());
        ) {
			//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("一个新的客户端:" + s.getRemoteSocketAddress() + "联接成功");
            String line = null;
            do {
                line = is.readLine();   //阻塞式
                //从客户端输入
                System.out.println("客户端说:" + line);
                if ("bye".equalsIgnoreCase(line)) {
                    System.out.println("客户端主动断线，。。。");
                    break;
                }
                System.out.println("请输入你想回应客户端的话:");
                String response = sc.nextLine();
                pw.println(response);
                pw.flush();
                if ("bye".equals(response)) {
                    System.out.println("服务器主动断开与客户端的联接");
                    break;
                }
            } while (true);
            System.out.println("服务器" + s.getLocalSocketAddress() + "断开与客户端" + s.getRemoteSocketAddress() + "联接");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端" + this.s.getRemoteSocketAddress() + "掉线");
        }
    }

}





