package com.yc.net.bean1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//服务器向客户端发信息
public class Test3Server {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9999);
        while (true) {
            Socket client = server.accept();

            OutputStream is = client.getOutputStream();
            DataOutputStream dos = new DataOutputStream(is);
            dos.writeUTF("hello,"+client.getInetAddress()+":"+client.getPort());
            dos.flush();
            is.flush();
            dos.close();
            is.close();
            client.close();
        }

    }

}

