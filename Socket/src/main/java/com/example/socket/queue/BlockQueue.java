package com.example.socket.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockQueue {
    //阻塞队列，FIFO
    private static LinkedBlockingQueue<Integer> concurrentLinkedQueue = new LinkedBlockingQueue<Integer>();


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Producer("producer1"));
        executorService.submit(new Producer("producer2"));
        executorService.submit(new Producer("producer3"));
        executorService.submit(new Consumer("consumer1"));
        executorService.submit(new Consumer("consumer2"));
        executorService.submit(new Consumer("consumer3"));


//        dataCollector();


//        alternate();

    }

    /**
     *（4个线程共用一个所对象）前4个线程存储时长不确定，导致第5个线程读数据不确定（该线程锁对象datalist）
     */
    private static void dataCollector() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        final DataCollector dataCollector = new DataCollector();
        for (int i = 0; i < 4; i++) {
            executorService.submit(() -> dataCollector.recordData("haha"));
        }
        executorService.submit(() -> dataCollector.sendData());
    }

    static class DataCollector {
        private List<String> dataList = new ArrayList<>();

        private synchronized void recordData(String data) {
            dataList.add(data);
        }

        private void sendData() {
            synchronized (dataList) {
                for (String s : dataList) {
                    System.out.println(s);
                }
            }
        }
    }

    /**
     * 双线程交替执行任务：利用wait和notify
     */
    private static void alternate() {
        new Thread(new SoulutionTask(), "偶数").start();
        new Thread(new SoulutionTask(), "奇数").start();
    }

    static class SoulutionTask implements Runnable {
        static int value = 0;

        @Override
        public void run() {
            while (value <= 100) {
                synchronized (SoulutionTask.class) {
                    System.out.println(Thread.currentThread().getName() + ":" + value++);
                    SoulutionTask.class.notify();
                    try {
                        SoulutionTask.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class Producer implements Runnable {
        private String name;

        public Producer(String name) {
            this.name = name;
        }

        public void run() {
            for (int i = 1; i < 10; ++i) {
                System.out.println(name + "  生产： " + i);
//                concurrentLinkedQueue.add(i);
                try {
                    concurrentLinkedQueue.put(i);
                    Thread.sleep(200); //模拟慢速的生产，产生阻塞的效果
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        }
    }

    static class Consumer implements Runnable {
        private String name;

        public Consumer(String name) {
            this.name = name;
        }

        public void run() {
            for (int i = 1; i < 10; ++i) {
                try {
                    //必须要使用take()方法在获取的时候阻塞,
                    // 当concurrentLinkedQueue为空，命令会停在此，从而堵塞线程
                    System.out.println(name + "消费： " + concurrentLinkedQueue.take());
                    System.out.println(name + "消费：hahahah " );
                    //使用poll()方法 将产生非阻塞效果
//                    System.out.println(name+"消费： " +  concurrentLinkedQueue.poll());

                    //还有一个超时的用法，队列空时，指定阻塞时间后返回，不会一直阻塞
                    //但有一个疑问，既然可以不阻塞，为啥还叫阻塞队列？
                    //System.out.println(name+" Consumer " +  concurrentLinkedQueue.poll(300, TimeUnit.MILLISECONDS));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }
}
