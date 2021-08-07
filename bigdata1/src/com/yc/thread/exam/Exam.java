package com.yc.thread.exam;

import java.util.Random;

/*
 编写一个java cmd程序，模拟在线考试的倒计时效果。
      正在做第i题。。。。
      。。。
      。。。。
      考试时间到，强制交卷...
 */
public class Exam {
    public static void main(String[] args) {
        NotifyListener nl=new MyNotify();   //监听器
        //计时任务
        Thread t=new CountTime(  nl, 10  );
        t.start();

        Student s=new Student(nl);
        s.setName("张三");
        Student s2=new Student(nl);
        s2.setName("李四 ");

        s.start();
        s2.start();


    }
}

class Student extends Thread{
    private NotifyListener listener;

    public Student(  NotifyListener listener){
        this.listener=listener;
    }

    @Override
    public void run() {
       for( int i=0;i<100;i++){
           System.out.println("学生"+ this.getName()+"正在做"+i+"题");
           try {
               Thread.sleep(  new Random().nextInt(4000) );
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           //有一个电铃
           if(  this.listener!=null  &&   this.listener instanceof MyNotify  ){
               if(  ((MyNotify)listener).isFinish()    ){
                   break;
               }
           }
       }
    }
}

/*
监听接口
 */
interface NotifyListener{
    /*
    监听的回调方法
     */
    public void notifyfinish( boolean isFinish  );
}
/*
监听器对象，
 */
class MyNotify implements NotifyListener{
    private boolean isFinish;
    //计时的子线程当时间到了，则调用这个方法，改变状态
    @Override
    public void notifyfinish(boolean isFinish) {
        this.isFinish=isFinish;
    }
    //考试端通过这个方法获取状态
    public boolean isFinish() {
        return isFinish;
    }
}

class CountTime extends Thread{
    private NotifyListener notifyListener;   // 监听器的对象
    private int elipsTime;  //总时间
    private int count;   //已用的时间
    private boolean flag=true;     //标识当前计时任务的状态

    public CountTime(  NotifyListener notifyListener, int eclipsTime  ){
        this.notifyListener=notifyListener;
        this.elipsTime=eclipsTime;
    }
    @Override
    public void run() {
        System.out.println("计时开始");
        while(  flag  ){
            System.out.println("距离考试结束还有:"+(elipsTime-count)+"秒");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            if(  count==elipsTime  ){
                flag=false;
                System.out.println("考试时间到，强制交卷");
                if(  this.notifyListener!=null ){
                    this.notifyListener.notifyfinish(  true);
                }
            }
        }
    }
}


