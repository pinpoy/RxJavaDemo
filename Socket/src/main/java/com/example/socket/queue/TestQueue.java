package com.example.socket.queue;

import java.util.PriorityQueue;

public class TestQueue {
    public static void main(String[] args) {
        testForPriorityQueue();

    }

    /**
     * PriorityQueue已经违反了队列的最基本原则:先进先出(FIFO)
     */
    private static void testForPriorityQueue() {
        PriorityQueue priorityQueue = new PriorityQueue();
        priorityQueue.offer(3);
        priorityQueue.offer(-6);
        priorityQueue.offer(9);

        //打印结果为[-6, 3, 9]
        System.out.println(priorityQueue);
        //打印结果为-6
        System.out.println(priorityQueue.peek());
        //打印结果为-6
        System.out.println(priorityQueue.poll());
    }
}
