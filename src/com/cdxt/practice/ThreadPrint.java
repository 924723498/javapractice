package com.cdxt.practice;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ThreadPrint
 * @Description ThreadPrint
 * @Author Administrator
 * @Date 2020/1/15 11:27
 */
public class ThreadPrint {

    private static ReentrantLock lock = new ReentrantLock();
    private Condition a = lock.newCondition();
    private Condition b = lock.newCondition();
    private Condition c = lock.newCondition();
    int i = 1;

    private void print () {
        new Thread(() ->{
            while (i < 10) {
                lock.lock();
                System.out.println("线程1打印： " + "A");
                i++;
                if (i >= 10) {
                    System.out.println("打印完毕！");
                    return;
                }
                try {
                    b.signal(); //唤醒线程2
                    a.await(); //将线程1从运行状态->阻塞等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() ->{
            while (i < 10) {
                lock.lock();
                System.out.println("线程2打印： " +  "B");
                i++;
                if (i >= 10) {
                    System.out.println("打印完毕！");
                    return;
                }
                try {
                    c.signal();
                    b.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() ->{
            while (i < 10) {
                lock.lock();
                System.out.println("线程3打印： " +  "C");
                i++;
                if (i >= 10) {
                    System.out.println("打印完毕！");
                    return;
                }
                try {
                    a.signal();
                    c.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new ThreadPrint().print();
    }
}
