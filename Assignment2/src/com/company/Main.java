package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        // Assignment2 code by Rui Zhou
        // Thank you for reviewing!

        // 35. Search Insert Position
        int[] array1 = new int[]{1,3,5,6};
        int target = 5;
        System.out.println(searchInsert(array1, target));

        // 540. Single Element in a Sorted Array
        int[] array2 = new int[]{1,1,2,3,3,4,4,8,8};
        System.out.println(singleNonDuplicate(array2));

        // 153. Find Minimum in Rotated Sorted Array
        int[] array3 = new int[]{3,4,5,1,2};
        System.out.println(findMin(array3));

        // 253. Meeting Rooms II
        int[][] intervals = new int[][]{{0,30},{5,10},{15,20}};
        System.out.println(minMeetingRooms(intervals));

        // 347. Top K Frequent Elements
        int[] array4 = new int[]{1,1,1,2,2,3};
        int k = 2;
        System.out.println(Arrays.toString(topKFrequent(array4, k)));

        // 16. 3Sum Closest
        int[] array5 = new int[]{-1,2,1,-4};
        int t = 1;
        System.out.println(threeSumClosest(array5, t));

        // 57. Insert Interval
        int[][] intervalsTest = new int[][]{{1,3}, {6,9}};
        int[] newInterval = new int[]{2,5};
        int[][] results = insert(intervalsTest, newInterval);
        for (int[] i : results) {
            System.out.println(Arrays.toString(i));
        }

        // 435. Non-overlapping Intervals
        int[][] intervalsTest2 = new int[][]{{1,2},{2,3},{3,4},{1,3}};
        System.out.println(eraseOverlapIntervals(intervalsTest2));

        // 986. Interval List Intersections
        int[][] firstList = {{0,2},{5,10},{13,23},{24,25}};
        int[][] secondList = {{1,5},{8,12},{15,24},{25,26}};
        int[][] result2 = intervalIntersection(firstList, secondList);
        for (int[] i : result2) {
            System.out.print(Arrays.toString(i));
        }

        // 18. 4Sum
        int[] sumArray = {1,0,-1,0,-2,2};
        int t2 = 0;
        System.out.println("");
        System.out.println(Arrays.asList(fourSum(sumArray, t2)));

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

    // 540. Single Element in a Sorted Array
    // Time Complexity: OlogN for the binary Search
    // Space Complexity: O1
    public static int singleNonDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + right + 1 >>> 1;
            if ((mid % 2 == 0 && nums[mid] == nums[mid-1]) || (mid % 2 != 0 && nums[mid] != nums[mid-1])) {
                right = mid - 1;
            }
            else {
                left = mid;
            }
        }
        return nums[left];
    }

    // 153. Find Minimum in Rotated Sorted Array
    // Time Complexity: OlogN for the binary Search
    // Space Complexity: O1
    public static int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + right >>> 1;
            if (nums[mid] < nums[right]) {
                right = mid;
            }
            else {
                left = mid + 1;
            }
        }
        return nums[left];
    }

    // 253. Meeting Rooms II
    // Time Complexity: ONlogN for the sorting
    // Space Complexity: ON for the list
    public static int minMeetingRooms(int[][] intervals) {
         List<int[]> meetings = new ArrayList<>();

         for (int i = 0; i < intervals.length; i++) {
             int start = intervals[i][0];
             int end = intervals[i][1];
             meetings.add(new int[]{start, 1});
             meetings.add(new int[]{end, -1});
         }

         Collections.sort(meetings, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

         int count = 0;
         int result = 0;
         for (int[] meeting : meetings) {
             count += meeting[1];
             result = Math.max(count, result);
         }
         return result;
     }

    // 347. Top K Frequent Elements
    // Time Complexity: ONlogK
    // Space Complexity: OK
    public static int[] topKFrequent(int[] nums, int k) {
        // if (nums == null || nums.length == 0) return 0;
        // Value - Frequency
        // 1-3  2-2  3-1
        Map<Integer, Integer> hashmap = new HashMap<>();
        for (int i : nums) {
            hashmap.put(i, hashmap.getOrDefault(i, 0) + 1);
        }
        // min heap
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> hashmap.get(a) - hashmap.get(b));
        for (int i : hashmap.keySet()) {
            queue.add(i);
            if (queue.size() > k) {
                queue.poll();
            }
        }

        int[] result = new int[queue.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = queue.poll();
        }

        return result;
    }

    // 16. 3Sum Closest
    // Time Complexity: ON^2
    // Space Complexity: O1
    public static int threeSumClosest(int[] nums, int target) {
         if (nums == null || nums.length == 0) return 0;

         Arrays.sort(nums);

         int result = Integer.MAX_VALUE;
         int value = 0;


         for (int i = 0; i < nums.length; i++) {
             int cur = nums[i];
             // left pointer
             int left = i + 1;
             // right pointer
             int right = nums.length - 1;

             while (left < right) {
                 int sum = cur + nums[left] + nums[right];
                 if (Math.abs(sum - target) < result) {
                     result = Math.abs(sum - target);
                     value = sum;
                 }

                 if (sum > target) {
                     right--;
                 }

                 else if (sum < target) {
                     left++;
                 }
                 else if (sum == target) {
                     return sum;
                 }
             }
         }

         return value;
     }

    // 57. Insert Interval
    // Time Complexity: ON
    // Space Complexity: ON
    public static int[][] insert(int[][] intervals, int[] newInterval) {
         List<int[]> result = new ArrayList<>();
         if (intervals == null || intervals.length == 0) {
             return new int[][]{newInterval};
         }

         if (newInterval == null || newInterval.length == 0) {
             return intervals;
         }

         for (int i = 0; i < intervals.length; i++) {
             int[] cur = intervals[i];

             if (newInterval == null || cur[1] < newInterval[0]) {
                 result.add(cur);
             }

             else if (newInterval == null || cur[0] > newInterval[1]){

                 result.add(newInterval);
                 result.add(cur);

                 newInterval = null;
             }
             else {
                 newInterval[0] = Math.min(cur[0], newInterval[0]);
                 newInterval[1] = Math.max(cur[1], newInterval[1]);
             }
         }

         if (newInterval != null) result.add(newInterval);

         return result.toArray(new int[result.size()][2]);
     }

    // 435. Non-overlapping Intervals
    // Time Complexity: ONlogN for the sorting
    // Space Complexity: O1
    public static int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] == b[0]) return a[1] - b[1];
            return a[0] - b[0];
        });

        int start = intervals[0][1];
        int result = 0;

        /* [[1,2]
             [1,3]
               [2,3]
                 [3,4]]
                 */
        for (int i = 1; i < intervals.length; i++) {
            if (start <= intervals[i][0]) {
                start = intervals[i][1];
            }
            else {
                start = Math.min(start, intervals[i][1]);
                result++;
            }
        }
        return result;
    }

    // 986. Interval List Intersections
    // Time Complexity: ON
    // Space Complexity: ON
    public static int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        if (firstList == null || secondList == null || firstList.length == 0 || secondList.length == 0) return new int[0][0];

        List<int[]> result = new ArrayList<>();

        int left1 = 0;
        int left2 = 0;
        while (left1 < firstList.length && left2 < secondList.length) {
            int start = Math.max(firstList[left1][0], secondList[left2][0]);
            int end = Math.min(firstList[left1][1], secondList[left2][1]);
            if (start <= end) {
                result.add(new int[]{start, end});
            }
            if (firstList[left1][1] < secondList[left2][1]) {
                left1++;
            }
            else {
                left2++;
            }
        }

        return result.toArray(new int[result.size()][2]);
    }

    // 18. 4Sum
    // Time Complexity: ON^3
    // Space Complexity: ON
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> results = new ArrayList<>();

        if (nums == null || nums.length == 0) return results;

        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i-1]) continue;
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j-1]) continue;
                // two sum
                int left = j + 1;
                int right = nums.length - 1;
                while (left < right) {
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target) {
                        List<Integer> result = new ArrayList<>();
                        result.add(nums[i]);
                        result.add(nums[j]);
                        result.add(nums[left]);
                        result.add(nums[right]);
                        results.add(result);
                        // results.add(List.of(nums[i], nums[j], nums[left], nums[right]));
                        while (left < right && nums[left] == nums[left + 1]) left++;
                        while (left < right && nums[right] == nums[right - 1]) right--;
                        left++;
                        right--;
                    }
                    else if (sum > target) {
                        right--;
                    }
                    else {
                        left++;
                    }
                }
            }
        }

        return results;
    }
}
