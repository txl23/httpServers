package com.yc.thread;

//测试Thread的属性
public class Test4 {
    public static void main(String[] args) {
        //属性:  name
       // ShowTime2Thread tt=new ShowTime2Thread(  "新线程"  );
        //ShowTime2Thread tt=new ShowTime2Thread(    );
       // tt.start();
        // 属性:  Priority   优先级
        Thread t1=new Thread(  new showNumber() );
        t1.setName("线程一-------");
        t1.setPriority(  Thread.MIN_PRIORITY  );  //10

        Thread t2=new Thread(  new showNumber() );
        t2.setName("线程二");
        t2.setPriority(  Thread.MAX_PRIORITY  );  //1

        t1.start();
        t2.start();


    }
}

class  showNumber implements Runnable{

    @Override
    public void run() {
        for( int i=0;i<99999999;i++){
            //System.out.println(  this.getName()+"\t"+ i )
            if(   i%10000==0 ) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }
    }
}
