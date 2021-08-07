package com.yc.threadpool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

/*
线程的创建和销毁都是非常耗费资源的，所以在一个高并发的场景下，
会事先创建好一定数量的线程，存放到集合中，如果有新的任务要运行，
则从这个集合获取一个空闲的线程绑定任务后运行即可，任务运行完后，
再将这个线程的状态设置为空闲，放到集合中，从而避免了频繁地创建和销毁线程的时间，提高了效率。
这种思想就是线程池的思想，下面请大家根据这种需求开发出一个简单版地线程池. 注意：初始情况下线程池的线程数与核数相关。
     精灵线程， 一起管理(线程组).
 */
public class Test2ThreadPool {
    public static void main(String[] args) throws InterruptedException, IOException {
        ThreadPoolManager tpm=new ThreadPoolManager();
        InputStream iis=System.in;
        BufferedReader br=new BufferedReader(new InputStreamReader(iis));
        String s=null;
        while(  (  s=br.readLine() )!=null  ){
            tpm.process(   new MyTask( s  )    );
        }
      Thread.sleep(    100000000   );
    }
}

class MyTask implements Taskable{
    private String content;
    public MyTask(  String content){
        this.content=content;
    }
    @Override
    public void doTask() {
          System.out.println(Thread.currentThread().getName()+"执行了任务"+content);
        try {
            //假设这个任务很耗时...
            Thread.sleep(   new Random().nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
