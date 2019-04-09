package com.example.socket.classloder;

/**
 * Created by jesgoo on 2019/1/10.
 */

public class Algorithm {


    public static void main(String args[]) {
        System.out.println("Hello World!");

        int[] arry = {1, 3, 4, 5, 7, 8, 9};
        int i = binarySearch(arry, 8);
        System.out.println("-----------" + i);
    }

    private static int binarySearch(int[] arry, int key) {
        int low = 0;
        int high = arry.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (key == arry[mid]) {
                return mid;
            } else if (key > arry[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}
