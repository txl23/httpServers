package com.yc.thread;

public class Main {
    //单线程的串行运行
    public static void main(String[] args) {
        //串行
	    //1. 单线程
        System.out.println("hello world");
        a();
        System.out.println("bye a");
        b();
        System.out.println( "bye b");
    }


    private static void a(){
        for( int i=0;i<100;i++){
            System.out.println(  "hello a "+ i);
        }
    }

    private static void b(){
        for( int i=0;i<100;i++){
            System.out.println(  "hello b "+ i);
        }
    }

}
