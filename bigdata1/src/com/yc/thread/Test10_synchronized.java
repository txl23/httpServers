package com.yc.thread;

/*
   需求: 请写一个程序模拟一下火车站卖票的场景
 */
public class Test10_synchronized {
    public static void main(String[] args) {
        //new Vector();    Hashtable 安全    synchroinzed
        //    ArrayList()  HashMap  不安全
        //new Hashtable();

        TicketCounterTask  tct=new TicketCounterTask(   10 );

        Thread t1=new Thread(  tct  );    //窗口 1 ->   线程1
        t1.setName("*窗口1*");
        t1.start();
        Thread t2=new Thread(  tct );      //窗口 2 ->   线程2
        t2.setName("+++++窗口2++++");
        t2.start();
        Thread t3=new Thread(  tct );      //窗口 3 ->   线程3
        t3.setName("------窗口3-----");
        t3.start();
    }
}

//售票窗口的任务
class TicketCounterTask implements Runnable{
    private int total;   //票池
    Object o=new Object();
    public TicketCounterTask(  int total){
        this.total=total;
    }
    @Override
    public  void run() {
        while( true ){
            //方案二:   同步方法
//            boolean f=doThing();
//            if( f){
//                break;
//            }
            //方案一: 同步块
            synchronized ( this ) {    //  也可以是o, 但必须是同一对象
                if (total <= 0) {
                    return ;
                }
                System.out.println("线程:" + Thread.currentThread().getName() + "正在售出第" + total + "张票");
                total--;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private synchronized boolean  doThing(){  //   锁定了  this  ->  TicketCounterTask
        if (total <= 0) {
            return true;
        }
        System.out.println("线程:" + Thread.currentThread().getName() + "正在售出第" + total + "张票");
        total--;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}