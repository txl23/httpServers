package com.yc.threadpool;

public class Test2 {
    static{
        System.out.println( "1");
    }
    public Test2(){
        System.out.println("2");
    }

    {
        System.out.println("3");
    }

    public static void main(String[] args) {
        new Test2();
        new Test2();
    }
}
