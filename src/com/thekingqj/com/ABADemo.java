package com.thekingqj.com;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {
    static AtomicReference<Integer> atomicReference=new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference =new AtomicStampedReference<>(100,1);
    public static void main(String[] args) {
        System.out.println("==============ABA问题=================");
        new Thread(() -> {
                    atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);
            System.out.println(atomicReference.get());
        }, "A").start();

        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicReference.compareAndSet(100,2020);
            System.out.println(atomicReference.get());
                }, "B").start();

        System.out.println("============解决ABA===============");
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println("线程C当前版本号"+stamp);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println("线程C第二次修改的版本号"+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println("线程C第三次修改的版本号"+atomicStampedReference.getStamp());
            System.out.println("C线程修改结束");
            }, "C").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println("线程D当前版本号"+stamp);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = atomicStampedReference.compareAndSet(100, 2000, stamp, stamp + 1);
            System.out.println("修改结果"+b+"\t 线程D当前版本号"+atomicStampedReference.getStamp());
                }, "D").start();
    }
}
