package com.yc.thread;
// join的测试
public class Test7 {
    public static void main(String[] args)  {
        Thread t=new Thread(  new Task7() );  //子线程, 优先级  5
       // t.setPriority(10);
        t.setName("新线程********");
        t.start();

        try {
            t.join();     //将当前线程 main 加入到等待队列中， 先等  t 运行完
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Thread.currentThread().setPriority(1);
        //Thread.yield();  //当前线程main,所以是main让步给  t
        //主线程: 优先级  5
        for(int i=0;i<999;i++){
            System.out.println(  "线程:"+Thread.currentThread().getName()+"输出 "+i );
        }
    }
}
class Task7 implements Runnable{
    // 阿里的代码规范插件
    @Override
    public void run(){
        for(int i=0;i<999;i++){
            try {
                Thread.sleep(  100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(  "线程:"+Thread.currentThread().getName()+"输出 "+i );
        }
    }
}
