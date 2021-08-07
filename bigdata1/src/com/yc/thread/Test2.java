package com.yc.thread;

import java.util.Date;

//多线程的并行运行
public class Test2 {
    public static void main(String[] args) {
        //并行
        Thread t1=new Thread(    new ShowTime()     );
        t1.start();  // jvm会自动调用   thread的   run();
        Thread t2=new Thread( new Download() );
        t2.start();
    }
}
//包权限
class ShowTime implements Runnable{
    @Override
    public void run() {
        //操作:
        while(true){
            System.out.println(   new Date() );
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
//下载 是一个耗时的操作
class Download implements Runnable{
    @Override
    public void run() {
        for( int i=0;i<1000000;i++){
            if(  i%1000==0 ) {
                System.out.println("下载数据" + i);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
