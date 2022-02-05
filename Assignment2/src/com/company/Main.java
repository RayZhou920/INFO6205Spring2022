package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int[] array1 = new int[]{1,3,5,6};
        int target = 5;
        System.out.println(searchInsert(array1, target));
    }

    // 35. Search Insert Position
    // Time Complexity: OlogN for the binary search
    // Space Complexity: O1
    public static int searchInsert(int[] nums, int target) {
        if (nums == null || nums.length == 0) return 0;
        if (target > nums[nums.length - 1]) return nums.length;

        // Binary Search
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            // Stop Overflow
            // int mid = left + (right - left) / 2
            int mid = left + right >>> 1;
            if (nums[mid] >= target) {
                right = mid;
            }
            else {
                left = mid + 1;
            }
        }

        return left;
    }
}
