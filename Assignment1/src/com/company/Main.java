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

        String order = "cba";
        String str = "abcd";
        System.out.println(customSortString(order, str));

        System.out.println(pancakeSort(citations));

        int[] nums = {1,1,2,2,2,3};
        System.out.println(frequencySort(nums));

        String[] words = new String[]{"i","love","leetcode","i","love","coding"};
        int k2 = 2;

        System.out.println(topKFrequent(words, 2));

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

    // Leetcode: 791. Custom Sort String
    // Time Complexity: ON
    // Space Complexity: ON（ for StringBuilder )
    public static String customSortString(String order, String s) {
        Map<Character, Integer> hashmap = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            hashmap.put(s.charAt(i), hashmap.getOrDefault(s.charAt(i), 0) + 1);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < order.length(); i++) {
            if (hashmap.containsKey(order.charAt(i))) {
                while (hashmap.get(order.charAt(i)) != 0) {
                    sb.append(order.charAt(i));
                    hashmap.put(order.charAt(i), hashmap.get(order.charAt(i)) - 1);
                }

            }
        }

        for (char c : hashmap.keySet()) {
            while (hashmap.get(c) != 0) {
                sb.append(c);
                hashmap.put(c, hashmap.get(c) - 1);
            }
        }

        return sb.toString();
    }

    // Leetcode: 969. Pancake Sorting
    // Time Complexity: ON^2
    // Space Complexity: O1
    public static List<Integer> pancakeSort(int[] arr) {
        List<Integer> result = new ArrayList<>();

        if (arr == null || arr.length == 0) return result;

        int end = arr.length;
        helper(arr, result, end);
        return result;
    }

    private static void helper(int[] arr, List<Integer> result, int end) {
        if (end == 0) {
            return;
        }

        // 每轮翻转都要找最大数以及对应的下标：
        // [3, 2, 4, 1] maxValue=4  maxIndex=2
        // 两轮翻转：[4, 2, 3, 1]   --->   [1, 3, 2, 4](将4翻转到0的位置，再翻转到末尾)
        // [1, 3, 2, 4]
        // 两轮翻转：[3, 1, 2, 4]   --->   [2, 1, 3, 4](将3翻转到0的位置，再翻转到数组第3位)
        // [2, 1, 3, 4]
        // [1, 2, 3, 4]
        int maxValue = 0;
        int maxIndex = 0;
        for (int i = 0; i < end; i++) {
            if (arr[i] > maxValue) {
                maxValue = arr[i];
                maxIndex = i;
            }
        }

        result.add(maxIndex + 1);
        reverse(0, maxIndex, arr);
        result.add(end);
        reverse(0, end - 1, arr);
        helper(arr, result, end - 1);
    }

    private static void reverse(int start, int end, int[] arr) {
        int left = start;
        int right = end;
        while (left < right) {
            int temp = arr[right];
            arr[right] = arr[left];
            arr[left] = temp;
            left++;
            right--;
        }
    }

    // Leetcode: 1636. Sort Array by Increasing Frequency
    // Time Complexity: ON
    // Space Complexity: OlogN (for the heap)
    public static int[] frequencySort(int[] nums) {
        if (nums == null || nums.length == 0) return new int[0];

        Map<Integer, Integer> hashmap = new HashMap<>();
        for (int i : nums) {
            hashmap.put(i, hashmap.getOrDefault(i, 0) + 1);
        }
        // [1,1,2,2,2,3]
        // [1 - 2; 2 - 3; 3 - 1]

        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> hashmap.get(a) == hashmap.get(b) ? b - a : hashmap.get(a) - hashmap.get(b));
        queue.addAll(hashmap.keySet());

        int[] result = new int[nums.length];
        int index = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            while (hashmap.get(cur) != 0) {
                result[index] = cur;
                index++;
                hashmap.put(cur, hashmap.get(cur) - 1);
            }
        }

        return result;
    }

    // Leetcode: 692. Top K Frequent Words
    // Time Complexity: ONlogK
    // Space Complexity: OlogK for the heap
    public static List<String> topKFrequent(String[] words, int k) {
        List<String> result = new ArrayList<>();
        Map<String, Integer> hashmap = new HashMap<>();
        // 最小堆堆内排序：
        // 1. 如果词频不同，按照从小到大生序排序 -> 最小堆
        // 2. 如果词频相同，按照字母表顺序进行降序排序（倒序）（即字母表在前的在堆内，字母表在后的在堆上）
        PriorityQueue<String> queue = new PriorityQueue<>((a, b) -> hashmap.get(a) == hashmap.get(b) ? b.compareTo(a) : hashmap.get(a) - hashmap.get(b));

        for (String s : words) {
            hashmap.put(s, hashmap.getOrDefault(s, 0) + 1);
        }

        for (String s : hashmap.keySet()) {
            // 词频
            queue.add(s);
            if (queue.size() > k) {
                queue.poll();
            }

        }

        while (!queue.isEmpty()) {
            result.add(queue.poll());
        }

        // 最后需要反转list，因为queue 首先poll出来的是堆顶元素，即刚好第k个 / 字母表在后的
        // 题目要求是从高 -> 低，字母表在前的在列表前面，所以需要反转列表
        Collections.reverse(result);

        return result;
    }
}
