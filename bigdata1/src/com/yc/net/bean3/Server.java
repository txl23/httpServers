package com.yc.net.bean3;

import com.yc.threadpool.Taskable;
import com.yc.threadpool.ThreadPoolManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

/**
 * @author 张影 QQ:1069595532  WX:zycqzrx1
 * @date Apr 3, 2020
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ThreadPoolManager tpm=new ThreadPoolManager();   //线程池的启动

        ServerSocket ss = new ServerSocket(8887);
        System.out.println("服务器启动了...监听:" + ss.getLocalPort() + "端口");
        while (true) {
            Socket s = ss.accept();
//            Thread t = new Thread(new TalkTask(s));
//            t.start();

            tpm.process(   new TalkTask( s  )    );   //给线程中的空闲线程绑定一个任务.
        }
    }
}

//任务类
class TalkTask implements Taskable {
    private Socket s;
    private Scanner sc = new Scanner(System.in);   //键盘输入    相当于前面的   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public TalkTask(Socket s) {
        this.s = s;
    }

    @Override
    public void doTask() {
        //socket输入流   is
        //socket输出流   pw
        //键盘输入流  br
        String command="";
        try (Socket s = this.s;
             InputStream iis=s.getInputStream();         //面向字节流
             //BufferedReader is = new BufferedReader(new InputStreamReader());
             PrintWriter pw = new PrintWriter(s.getOutputStream());
        ) {
			//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("一个新的客户端:" + s.getRemoteSocketAddress() + "联接成功");
            String line = null;   //客户端的聊天的内容
            String response=null;  //服务器响应给客户端的内容( String )
            do {

                //line = is.readLine();   //阻塞式
                byte[] bs=genByteArray(  iis );   //将客户端传过来的数据转为 byte[]
                if(  "SNAPSHOT".equals(command)){
                    saveToDisk(  bs, s.getRemoteSocketAddress()     );
                    response="RECEIVE SNAPSHOT OK";
                }else {
                    //默认当成聊天的内容
                    line=new String( bs );
                    //从客户端输入
                    System.out.println("客户端说:" + line);
                    if ("bye".equalsIgnoreCase(line)) {
                        System.out.println("客户端主动断线，。。。");
                        break;
                    }
                    System.out.println("请输入你想回应客户端的话:");
                    response = sc.nextLine();
                }
                pw.println(response);
                pw.flush();
                if ("bye".equals(response)) {
                    command="bye";
                    System.out.println("服务器主动断开与客户端的联接");
                    break;
                }else if( "SNAPSHOT".equals(response)){
                    command="SNAPSHOT";
                }else{
                    command="";
                }
            } while (true);
            System.out.println("服务器" + s.getLocalSocketAddress() + "断开与客户端" + s.getRemoteSocketAddress() + "联接");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端" + this.s.getRemoteSocketAddress() + "掉线");
        }
    }

    //保存客户端上传的图片
    private void saveToDisk(byte[] bs, SocketAddress remoteSocketAddress) {
        String path=System.getProperty("user.home");   //用户目录   C:\Users\Administrator
        //                    c:/document and settings     /  服务器ip:xxx   TODO: 文件名是否合法
        String filepath=path+File.separator+remoteSocketAddress.toString();
        try( FileOutputStream fos=new FileOutputStream(new File( filepath))){
            fos.write( bs );
            fos.flush();
        }catch( Exception ex){
            ex.printStackTrace();
        }
    }
    // 从输入流中获取数据，一次性的转为  byte[] 输出 .
    private byte[] genByteArray(InputStream iis) {
        byte[] bs=new byte[1024];
        int length=-1;
        try( ByteArrayOutputStream baos=new ByteArrayOutputStream();
            InputStream iis0=iis;
        ){
            while(  (length=iis0.read(bs,0,bs.length))!=-1){
                baos.write(bs,0,length);
                baos.flush();
            }
            return  baos.toByteArray();
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

}





