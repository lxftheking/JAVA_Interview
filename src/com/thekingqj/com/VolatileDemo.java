package com.thekingqj.com;

import java.util.concurrent.atomic.AtomicInteger;

class MyData {
    volatile int m = 0;

    public void addM() {
        m = m + 60;
    }

    public void addPlus(){
        m++;
    }
    AtomicInteger atomicInteger=  new AtomicInteger();
    public  void addAtomic(){
        atomicInteger.getAndIncrement();
    }
}


public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j <2000 ; j++) {
                    myData.addPlus();
                    myData.addAtomic();
                }
                    }, String.valueOf(i)).start();
        }

        while(Thread.activeCount()>2){
            Thread.yield();
        }

        System.out.println(myData.m);
        System.out.println(myData.atomicInteger);
    }


    //实现volatile的可见性问题
    public static void test() {
        MyData myData = new MyData();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\tcome in");
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addM();
            System.out.println(Thread.currentThread().getName() + "\tupdate" + myData.m);
        }, "A").start();

        while (myData.m == 0) {

        }
        System.out.println(Thread.currentThread().getName() + "\tend" + myData.m);
    }
}
