package com.yc.thread;

import java.util.Date;

//研究一下Thread类和Runnable 接口
public class Test3 {

    /*
       Runnable中有一个run(),表示要执行的操作
       new  Thread(   Runnable对象 )  :  创建一个线程对象，并绑定任务
       start()   则启动线程， jvm会自动地调用  run();

     */


    public static void main(String[] args) {
        ShowTime2Thread stt2=new ShowTime2Thread(    );    //    因为此时这个  Showtime2Thread已经有 run()方法了，即任务定了
        stt2.start();  //启动     jvm会自动的调用    线程ShowTime2Thread 的run()


    }

}

class ShowTime2Thread extends Thread{   // Thread implements Runnalbe,所以   它有一个run()方法  这个方法中放要执行的操作
                                 // 那么问题就是   设计师( Thread) 他怎么知道我要做什么操作   ->   空实现


    public ShowTime2Thread(){   super(); }

    public ShowTime2Thread(String threadName){   super( threadName  ); }

    @Override   // 重写了Thread类中的 run()
    public void run() {     //
        //操作:
        while(true){
            System.out.println(   this.getName()+":"+  new Date() );
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
