package com.thekingqj.com;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS是什么？
 *   比较并交换
 */
public class CASDemo {
    public static void main(String[] args) {

        AtomicInteger atomicInteger=new AtomicInteger(5);
        System.out.println(atomicInteger.getAndIncrement());
    }
}
