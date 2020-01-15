package com.cdxt.practice;

/**
 * 多线程顺序执行
 * 通过  notify   await 来实现
 * @ClassName TurnsPrintNumber
 * @Description TurnsPrintNumber
 * @Author Administrator
 * @Date 2020/1/13 16:32
 */
public class TurnsPrintNumber {
    private static Object lock = new Object();
    private static  int i = 1;

    static class Print implements Runnable{
        @Override
        public void run() {
            while (true){
                synchronized(lock){
                    if(i>20){
                        System.out.println("打印完成");
                        lock.notify();
                        break;
                    }
                    System.out.println("当前线程 "+Thread.currentThread().getName()+" 输出值为："+i++);
                    lock.notify();
                    try {
//                        Thread.sleep(1500);
                        lock.wait();
                    }catch (Exception e){

                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Print print1 = new Print();
        Print print2 = new Print();
        Print print3 = new Print();
        new Thread(print1).start();
        new Thread(print2).start();
        new Thread(print3).start();
    }
}
