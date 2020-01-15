package com.cdxt.practice;

import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程顺序执行
 * 通过  notify   await 来实现
 *
 * @ClassName TurnsPrintNumber
 * @Description TurnsPrintNumber
 * @Author Administrator
 * @Date 2020/1/13 16:32
 */
public class TurnsPrintNumber2 {
    private static ReentrantLock lock = new ReentrantLock();
    private  Condition a = lock.newCondition();
//    private  Condition b = lock.newCondition();

    private  int i = 1;

    private void print () {
        new Thread(() ->{
            while (i < 20) {
                lock.lock();
                System.out.println("线程1打印： " + i++);
                try {
//                    b.signal();//唤醒线程2
                    a.await(); //将线程1从运行状态->阻塞等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(() ->{
            while (i < 20) {
                lock.lock();
                System.out.println("线程2打印： " + i++);
                try {
//                    b.await();
                    a.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }



    public static void main(String[] args) {
        Print print1 = new Print(lock);
        Print print2 = new Print(lock);

        Thread thread1 = new Thread(print1);
        Thread thread2 = new Thread(print2);
        thread1.start();
        thread2.start();
        new TurnsPrintNumber2().print();

    }




}
