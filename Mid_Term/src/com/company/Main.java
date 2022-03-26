package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        // test for the question1
        int[] arr = {1};
        int low = 2;
        int upper = 98;
        List<String> res = countRanges(arr, low, upper);
        for (String s : res) {
            System.out.println(s);
        }

        // test for the question2
        // node l1: [1,2,3,5]
        // node l2: [7,9,5,3]
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        ListNode node5 = new ListNode(7);
        ListNode node6 = new ListNode(9);
        ListNode node7 = new ListNode(5);
        ListNode node8 = new ListNode(3);
        node5.next = node6;
        node6.next = node7;
        node7.next = node8;

        ListNode res2 = addTwoNumbers(node1, node5);
        while (res2 != null) {
            System.out.println(res2.val);
            res2 = res2.next;
        }

        // test for the q3
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};
        TreeNode node = createTree(preorder, inorder);
        System.out.println(node.val);

        // test for the q4
        // intervals = [[1,3],[2,6],[8,10],[15,18]]
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] result = mergeIntegervals(intervals);
        for (int[] r : result) {
            System.out.println(Arrays.toString(r));
        }
    }

    // Question1: Count the ranges
    public static List<String> countRanges(int[] arr, int low, int upper) {
        List<String> results = new ArrayList<>();
        if (arr == null || arr.length == 0) return results;

        // check the first condition
        if (arr[0] < low) {
            StringBuilder sb = new StringBuilder();
            sb.append(arr[0] + "");
            if (low - arr[0] > 1) {
                sb.append("->");
                sb.append(low + "");
            }
            results.add(sb.toString());
        }

        if (arr.length == 1 && arr[0] < upper) {
            StringBuilder sb = new StringBuilder();
            sb.append(low + "");
            sb.append("->");
            sb.append(upper + "");
            results.add(sb.toString());
            return results;
        }

        // nums = [0,1,3,50,75], lower = 0, upper = 99
        for (int i = 0; i < arr.length; i++) {
            int cur = arr[i];
            if (i != arr.length - 1 && arr[i + 1] - cur > 1) {
                StringBuilder sb = new StringBuilder();
                sb.append((cur + 1) + "");
                if (arr[i + 1] - cur > 2) {
                    sb.append("->");
                    sb.append((arr[i + 1] - 1) + "");
                }
                results.add(sb.toString());
            }

            if (i == arr.length - 1 && cur + 1 < upper) {
                StringBuilder sb = new StringBuilder();
                sb.append((cur + 1) + "");
                if (upper - arr[i] > 2) {
                    sb.append("->");
                    sb.append(upper + "");
                }
                results.add(sb.toString());
            }
        }

        return results;
    }

    // Question 2: add the two numbers from linkedlist
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {
        }
        ListNode(int val) {
            this.val = val;
        }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    // listNode a: [1, 2, 3, 4]
    // listNode b: [5, 6, 7, 8]
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) return null;
        int carry = 0;
        ListNode dummy = new ListNode(-1);
        int first = l1.val;
        int second = l2.val;
        int total = (first + second) % 10;
        carry = first + second >= 10 ? 1 : 0;
        ListNode head = new ListNode(total);
        dummy.next = head;

        l1 = l1.next;
        l2 = l2.next;

        int sum = 0;

        while (l1 != null || l2 != null) {
            if (l1 == null) {
                sum = (l2.val + carry) % 10;
                carry = l2.val + carry >= 10 ? 1 : 0;
            } else if (l2 == null) {
                sum = (l1.val + carry) % 10;
                carry = l1.val + carry >= 10 ? 1 : 0;
            } else {
                int l1val = l1.val;
                int l2val = l2.val;
                sum = l1val + l2val + carry;
                if (sum >= 10) {
                    sum = sum % 10;
                    carry = 1;
                } else {
                    carry = 0;
                }
            }
            ListNode node = new ListNode(sum);
            head.next = node;
            head = node;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        if (carry != 0) {
            ListNode last = new ListNode(carry);
            head.next = last;
        }
        return dummy.next;
    }

    // Question 3:
    // Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
    // Output: [3,9,20,null,null,15,7]
    // Given two integer arrays preorder and inorder where preorder is the preorder traversal of a binary tree and inorder is the inorder traversal of the same tree,
    // construct and return the binary tree.
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        public TreeNode() {};
        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static TreeNode createTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null) return null;

        Map<Integer, Integer> hashmap = new HashMap<>();
        // create the map for the inorder
        for (int i = 0; i < inorder.length; i++) {
            hashmap.put(inorder[i], i);
        }

        return dfs(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1, hashmap);
    }

    private static TreeNode dfs(int[] preorder, int[] inorder, int pl, int pr, int il, int ir, Map<Integer, Integer> hashmap) {
        if (pl > pr || il > ir) {
            return null;
        }

        int root = preorder[pl];
        // find the root's index in the inorder
        int index = hashmap.get(root);
        TreeNode rootTree = new TreeNode(root);
        // find the left length according to the inorder traverse
        int leftLength = index - il;
        rootTree.left = dfs(preorder, inorder, pl + 1, pl + leftLength, il, index - 1, hashmap);
        rootTree.right = dfs(preorder, inorder, pl + leftLength + 1, pr, index + 1, ir, hashmap);

        return rootTree;
    }

    // Question 4: merge intervals
    /*
    Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
    Output: [[1,6],[8,10],[15,18]]
    Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
     */
    public static int[][] mergeIntegervals(int[][] intervals) {
        List<int[]> results = new ArrayList<>();
        if (intervals == null || intervals.length == 0) return new int[][]{};

        int[] interval1 = intervals[0];
        results.add(interval1);
        int index = 0;

        for (int i = 1; i < intervals.length; i++) {
            if (results.get(index)[1] >= intervals[i][0]) {
                results.get(index)[0] = Math.min(results.get(index)[0], intervals[i][0]);
                results.get(index)[1] = Math.max(results.get(index)[1], intervals[i][1]);
            }
            else {
                results.add(intervals[i]);
                index++;
            }
        }

        int[][] finalResult = new int[results.size()][2];
        for (int i = 0; i < results.size(); i++) {
            int[] temp = results.get(i);
            finalResult[i][0] = temp[0];
            finalResult[i][1] = temp[1];
        }

        return finalResult;
    }
}
