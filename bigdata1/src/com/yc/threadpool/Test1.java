package com.yc.threadpool;

/*
多线程可以提高程序运行的效率，但线程并不是越多越好的，最好与当前机器的cpu核数相关，请
写一个程序，获取当前系统的核数，并创建对应数量的线程。
 */
public class Test1 {
    public static void main(String[] args) {
        //new Runtime();
        Runtime rt=Runtime.getRuntime();  //运行时环境  ,一个程序 与系统交互的一个接口  ， only one
        //唯一的一个接口  ->  设计模式?  单例
        //    构造方法私有化,即不能new
        //  对外提供唯一的访问接口     static Runtime getRuntime()
        //   懒汉实现， 尔汗实现
        System.out.println(  rt.availableProcessors() );
    }
}
