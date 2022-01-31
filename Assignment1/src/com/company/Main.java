package com.company;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int[] array1 = new int[]{2,0,2,1,1,0};
        sortColors(array1);
        System.out.println(Arrays.toString(array1));
    }

    // Leetcode 75. Sort Colors
    // Time Complexity: ON
    // Space Complexity: O1
    public static void sortColors(int[] nums) {
        if (nums == null || nums.length == 0) return;
        if (nums.length < 2) return;
        // [0, m) == 0
        // [m, i) == 1
        // (n, length-1] == 2
        int m = 0;
        int i = 0;
        int n = nums.length - 1;
        while (i <= n) {
            if (nums[i] == 0) {
                swap(m, i, nums);
                m++;
                i++;
            }
            else if (nums[i] == 1) {
                i++;
            }
            else {
                // nums[i] == 2
                swap(n, i, nums);
                n--;
            }
        }
    }
    // swap method
    private static void swap(int x, int y, int[] nums) {
        int temp = nums[x];
        nums[x] = nums[y];
        nums[y] = temp;
    }
}
