package com.yc.thread;

/**
 * 并发和单线程执行测试
 */
public class Test8_whyjoin {
    /** 执行次数 */
    private static final long count = 100000000L;

    public static void main(String[] args) throws InterruptedException {
        //并发计算
        concurrency();
        //单线程计算
        serial();
    }

    private static void concurrency() throws InterruptedException {
        long start = System.currentTimeMillis(); //测试开始时间
        Thread thread = new Thread(new Runnable() {  // 匿名内部类
            @Override
            public void run() {
                int a = 0;
                for (long i = 0; i < count; i++) {    //执行累加操作
                    a += 5;
                }
                System.out.println(a);
            }
        });
        thread.start();  //启动新线程  ,注意此时 新线程与下面的代码 b--  是同时运行的
        //主线程:完成1000次b--
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        //为什么这里要用   join()呢? 请看解释
        thread.join();    //如果main线程中的 b--都做完成，但新线程没有做完,让步给新线程让它完成.
        long time = System.currentTimeMillis() - start;  //等新线程与主线程都运行完,再统计花费的时间
        System.out.println("并行运行场景下花费的时间 :" + time + "ms,b=" + b);   //并行性能
    }

    private static void serial() {
        long start = System.currentTimeMillis();
        int a = 0;
        for (long i = 0; i < count; i++) {   //执行累加操作
            a += 5;
        }
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("串行的花费时间:" + time + "ms,b=" + b + ",a=" + a);   //串行性能
    }
}




