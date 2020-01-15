package com.cdxt.practice;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName Print
 * @Description Print
 * @Author Administrator
 * @Date 2020/1/13 17:02
 */
class Print implements Runnable {
    private static int i;
    private ReentrantLock lock = new ReentrantLock();
    Condition condition1;

    public Print(ReentrantLock lock) {
        this.lock = lock;
        condition1 = lock.newCondition();
    }

    @Override
    public void run() {
        while (i < 20) {
            lock.lock();

            System.out.println("当前线程 " + Thread.currentThread().getName() + " 输出值为：" + i++);
            try {
                condition1.await();
                    Thread.sleep(1500);

            } catch (Exception e) {

            }finally {
                condition1.signal();
                lock.unlock();
            }

        }
        System.out.println("打印完成");
    }
}
