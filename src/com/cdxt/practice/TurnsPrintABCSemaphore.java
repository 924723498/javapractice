package com.cdxt.practice;

import java.util.concurrent.Semaphore;

/**
 * @ClassName SemaPhore
 * @Description SemaPhore
 * @Author Administrator
 * Key顺序控制
 *
 * @Date 2020/1/15 11:30
 */
public class TurnsPrintABCSemaphore {
    // A初始信号量数量为1
    private static Semaphore A = new Semaphore(1);
    // B、C初始信号数量为0
    private static Semaphore B = new Semaphore(0);
    private static Semaphore C = new Semaphore(0);

    static class ThreadA extends Thread {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    A.acquire();// A获取信号执行,A信号量减1,当A为0时将无法继续获得该信号量
                    System.out.println("线程1打印： " + "A");
                    B.release();
                    //System.out.println(B.drainPermits());//B释放之后信号量加1（初始为0），可以查看到 B 的信号量为1
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ThreadB extends Thread {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    B.acquire();
                    System.out.println("线程2打印： " + "B");
                    C.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ThreadC extends Thread {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    C.acquire();
                    System.out.println("线程3打印： " + "C");
                    A.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ThreadA().start();
        new ThreadB().start();
        new ThreadC().start();
    }
}

