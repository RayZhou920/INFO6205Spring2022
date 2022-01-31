package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int[] array1 = new int[]{2,0,2,1,1,0,0};
        int[] citations = new int[]{3,0,6,1,5};

        sortColors(array1);
        System.out.println(Arrays.toString(array1));

        System.out.println(majorityElement(array1));

        System.out.println(hIndex(citations));

        System.out.println(Arrays.toString(intersection(array1, citations)));

        int[] array2 = {1,2,3,4,5};
        int k = 4, x = 3;
        System.out.println(findClosestElements(array2, k, x));

        String s = "aab";
        System.out.println(reorganizeString(s));
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

    // Leetcode: 229. Majority Element II
    // Time Complexity: ONLogN
    // Space Complexity: O1
    public static List<Integer> majorityElement(int[] nums) {
        List<Integer> result = new ArrayList<>();

        Arrays.sort(nums);

        int target = nums.length / 3;

        int count = 1;

        int left = 0;
        // 2 3 3 3 3   1
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i-1]) {
                count++;
            }
            else {
                count = 1;
            }
            if (!result.contains(nums[i]) && count > target) {
                result.add(nums[i]);
            }

        }
        return result;
    }

    // Leetcode: 274. H-Index
    // Time Complexity: Binary Search OlogN, Traverse ON, total is ONlogN
    // Space Complexity: O1
    public static int hIndex(int[] citations) {
        if (citations == null || citations.length == 0) {
            return 0;
        }

        // 1, 1, 3  -> 2 1
        // 二分对象：论文篇数h，最少符合的为0，最多符合的为数组的长度
        int count = 0;
        int left = 0;
        int right = citations.length;
        while (left < right) {
            int mid = left + right + 1 >>> 1;
            // 需要有mid个至少要大于mid
            count = helper(mid, citations);
            // [0, 1, 3, 5, 6]
            // mid = 4, 大于mid的只有2个，需要有至少4个，
            // 说明此时mid取偏大，mid当前这个值肯定不能要
            if (mid > count) {
                right = mid - 1;
            }
            else {
                left = mid;
            }
        }

        return left;
    }

    private static int helper(int mid, int[] citations) {
        int count = 0;
        for (int i : citations) {
            if (i >= mid) {
                count++;
            }
        }

        return count;
    }

    // Leetcode: 349. Intersection of Two Arrays
    // Time Complexity: ON
    // Space Complexity: O1
    public static int[] intersection(int[] nums1, int[] nums2) {
        List<Integer> result = new ArrayList<>();

        Map<Integer, Integer> hashmap = new HashMap<>();
        for (int i : nums1) {
            hashmap.put(i, 1);
        }

        for (int i : nums2) {
            if (hashmap.containsKey(i) && hashmap.get(i) == 1) {
                result.add(i);
            }
            if (hashmap.containsKey(i)) {
                hashmap.put(i, hashmap.get(i) - 1);
            }
        }

        int[] f = new int[result.size()];
        int index = 0;
        for (int i : result) {
            f[index++] = i;
        }

        return f;
    }

    // Leetcode: 658. Find K Closest Elements
    // Time Complexity: ONlogN
    // Space Complexity: O1
    public static List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> result = new ArrayList<>();

        if (arr == null || arr.length == 0) return result;

        int left = 0;
        int right = arr.length - k;
        // 二分搜索左边界区间（左边界在这个区间内移动）
        while (left < right) {
            // mid为当前情况下的左边界
            int mid = left + right >>> 1;
            if (Math.abs(arr[mid] - x) > Math.abs(arr[mid + k] - x)) {
                left = mid + 1;
            }
            else {
                right = mid;
            }
        }

        for (int i = left; i < left + k; i++) {
            result.add(arr[i]);
        }

        return result;
    }

    // Leetcode: 767. Reorganize String
    // Time Complexity: ONlogK(k is the number of total Characters)
    // Space Complexity: OlogK for the heap
    public static String reorganizeString(String s) {
        if (s == null || s.length() == 0) return "";

        // count
        Map<Character, Integer> hashmap = new HashMap<>();
        for (char c : s.toCharArray()) {
            hashmap.put(c, hashmap.getOrDefault(c, 0) + 1);
        }

        // build the maxheap
        PriorityQueue<Character> maxHeap = new PriorityQueue((a ,b) -> hashmap.get(b) - hashmap.get(a));
        maxHeap.addAll(hashmap.keySet());

        StringBuilder sb = new StringBuilder();

        // pay attention to
        while (maxHeap.size() > 1) {
            char cur = maxHeap.poll();
            char next = maxHeap.poll();
            sb.append(cur);
            sb.append(next);
            hashmap.put(cur, hashmap.get(cur) - 1);
            hashmap.put(next, hashmap.get(next) - 1);
            if (hashmap.get(cur) != 0) {
                maxHeap.add(cur);
            }
            if (hashmap.get(next) != 0) {
                maxHeap.add(next);
            }
        }

        // check the last elements
        //  aaaabbbbcc
        //  ababababcc
        // the last is c, its frequency is 2 bigger than 1
        if (!maxHeap.isEmpty()) {
            char last = maxHeap.poll();
            if (hashmap.get(last) > 1) {
                return "";
            }
            else {
                sb.append(last);
            }
        }

        return sb.toString();

    }

}
