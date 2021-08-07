package com.yc.thread;

import java.util.Date;

//线程的分类
public class Test6_threadTypes {
    public static void main(String[] args) {
        //取出当前程序 所在的线程: 主线程
        Thread t= Thread.currentThread();
        System.out.println(   "名字:"+ t.getName() );
        System.out.println("编号:"+t.getId());
        System.out.println(  "优先级:"+t.getPriority()  );
        System.out.println(  "线程组:"+t.getThreadGroup() );

        ThreadGroup tg=new ThreadGroup("线程组");
        tg.setMaxPriority(3);   //最大允许的优先级

        Thread t2=new Thread( tg, new Clock() );
        t2.setDaemon(  true  );   //精灵线程/ 后台线程  : 资源回收的任务( jvm的垃圾回收器 gc )
        t2.setName( "新线程"   );
        t2.setPriority(10);
        System.out.println(  t2.getPriority() );
        t2.start();
    }
}

class Clock implements Runnable{
    @Override
    public void run() {
        //操作:
        while(true){
            System.out.println(   Thread.currentThread().getName()+":"+  new Date() );
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
