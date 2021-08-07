package com.yc.thread;

import java.util.Random;

//生产者消费者
//需求 ： 写个程序 模拟苹果生产和销售的过程
//  生产者：负责生产苹果，存到 仓库(集合，数组），最多存5个
//  销售者： 负责销售苹果，从仓库取，至到卖空
public class Test12_producerAndConsumer {
    public static void main(String[] args) {
        //资源
        AppleBox box=new AppleBox();

        ProducerTask pt=new ProducerTask(  box );
        ConsumerTask ct=new ConsumerTask(  box );
        Thread t1=new Thread(   pt );    //生产
        t1.setName("生产者*****:");
        t1.start();

        Thread t2=new Thread(   ct );    //生产
        t2.setName("*****消费者:");
        t2.start();
    }
}

//生产者任务类
class ProducerTask implements Runnable{
    AppleBox appleBox;     //生产和消费操作的资源相同
    public ProducerTask(  AppleBox appleBox){
        this.appleBox=appleBox;
    }
    @Override
    public void run() {
        Random r=new Random();
        //生产苹果
        for( int i=0;i<6;i++){
            Apple a=new Apple(  i );
            appleBox.deposite(   a );
            try {
                Thread.sleep( r.nextInt(3)*1000    );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
// 销售任务类
class ConsumerTask implements Runnable{
    AppleBox appleBox;     //生产和消费操作的资源相同
    public ConsumerTask(  AppleBox appleBox){
        this.appleBox=appleBox;
    }
    @Override
    public void run() {
        Random r=new Random();
        for( int i=0;i<6;i++){
            Apple a=appleBox.withdraw();
            try {
                Thread.sleep(  r.nextInt(3) *1000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//  资源  仓库类或容器类，存取苹果
class AppleBox{
    Apple[] apples=new Apple[5];
    int index=0;    //  是针对apples的索引 .
    //入库苹果
    public synchronized void deposite(  Apple apple ){   // AppleBox 锁
        while(  index>=apples.length ){
            //存满了话，则要等消费端消费一下
//            try {
//                Thread.sleep(  1000 );
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            try {
                this.wait();   //   Object的方法
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notifyAll();   //唤醒其它正在wait的线程
        apples[index]=apple;   //入库
        index++;
        System.out.println(Thread.currentThread().getName()+"生产了苹果:"+  apple);
    }
    //出库苹果
    public synchronized Apple withdraw(  ){
        //处循环
        while(   index==0){   //没有苹果可以消费，只能等生产端生产
//            try {
//               // Thread.sleep(  1000 );  //sleep时，不释放锁
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            try {
                this.wait();    //  等待，但会释放锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notifyAll();
        Apple a=apples[index-1];
        index--;
        System.out.println(   Thread.currentThread().getName()+"消费了:"+ a );
        return a;
    }
}

//苹果类
class Apple{
    int id;
    Apple(  int id){
        this.id=id;
    }
    @Override
    public String toString() {
        return "apple:"+id;
    }
}
