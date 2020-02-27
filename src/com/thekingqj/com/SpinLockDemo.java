package com.thekingqj.com;

import java.util.concurrent.atomic.AtomicReference;

public class SpinLockDemo {

    private AtomicReference<Thread> reference =new AtomicReference<>();
    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"lock");
        while(!reference.compareAndSet(null,thread)){
            System.out.println(Thread.currentThread().getName()+"wait");
        }
        System.out.println(Thread.currentThread().getName()+"lock,method over");
    }

    public void muUnLock(){
        Thread thread = Thread.currentThread();
        reference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"unlock");
    }
    public static void main(String[] args) {

        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(() -> {
                    spinLockDemo.myLock();
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.muUnLock();
        }, "A").start();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            spinLockDemo.myLock();

            spinLockDemo.muUnLock();
                }, "B").start();
    }
}
