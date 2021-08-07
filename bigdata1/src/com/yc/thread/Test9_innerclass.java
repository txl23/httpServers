package com.yc.thread;

import java.util.Date;

//匿名内部类的写法
public class Test9_innerclass {
    public static void main(String[] args) {
        //ShowTime3 st=new ShowTime3();
        Thread t=new Thread(  new Runnable(){  // 匿名的内部类
            @Override
            public void run() {
                while( true ){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(   new Date() );
                }
            }
        }  );
        t.start();
    }
}
//外部类
class ShowTime3 implements Runnable{
    @Override
    public void run() {
       while( true ){
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           System.out.println(   new Date() );
       }
    }
}

