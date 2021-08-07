package com.yc.thread;

public class Test5 {
    public static void main(String[] args) {
        //yield的使用
        YeildTask y1=new YeildTask();
        YeildTask y2=new YeildTask();   //两个任务对象

        Thread t1=new Thread(  y1,"员工" );
        Thread t2=new Thread(  y2, "领导");

        System.out.println(    t1.getPriority()+"\t"+t2.getPriority() );

        t1.setPriority(5);
        t2.setPriority(5);

        t1.start();
        t2.start();
    }
}

class YeildTask implements Runnable{

    @Override
    public void run() {
        if(  "员工".equalsIgnoreCase(  Thread.currentThread().getName() ) ){
            Thread.yield();    //  yield静态方法，代表当前的线程  让步
            // 只让步给优先级高
        }
        for( int i=0;i<99999999;i++){
            if(  i%10000==0  ) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }
    }
}