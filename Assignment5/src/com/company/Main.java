package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
	// Assignment5 Code by Rui Zhou
        TreeNode node1 = new TreeNode(3);
        TreeNode node2 = new TreeNode(4);
        TreeNode node3 = new TreeNode(5);
        TreeNode node4 = new TreeNode(6);
        TreeNode node5 = new TreeNode(10);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;

        List<List<Integer>> results = levelOrderBottom(node1);
        for (List<Integer> list : results) {
            System.out.println(Arrays.toString(list.toArray()));
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
        }
    }

    // Leetcode 107. Binary Tree Level Order Traversal II
    // Time Complexity: ON
    // Space Complexity: ON
    public static List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> results = new ArrayList<>();

        if (root == null) return results;

        Queue<TreeNode> queue = new LinkedList<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                result.add(node.val);

                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            results.add(new ArrayList<>(result));
        }

        Collections.reverse(results);

        return results;
    }

    // Leetcode 366. Find Leaves of Binary Tree
    // Time Complexity: ON
    // Space Complexity: ON
    public static List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> results = new ArrayList<>();

        if (root == null) return results;

        dfs(root, results);

        return results;
    }

    private static int dfs(TreeNode root, List<List<Integer>> results) {
        // 递归出口：空节点的时候，向上返回的高度是-1
        if (root == null) {
            return -1;
        }

        // 计算当前节点的高度：下面节点返回的高度 + 1（其中下面节点的高度需要对左右节点高度进行取较大值）
        // 自底向上
        int height = Math.max(dfs(root.left, results), dfs(root.right, results)) + 1;

        // 将高度加入到结果集中
        // 判断结果集中是否存在本高度的集合：比较结果集的size大小与高度
        // 然后将高度加入到对应高度的集合之中
        // 比如节点4， 5， 3的高度是0，那么results的size如果是0，说明需要新建一个集合
        // 如果不为0，那么我们取出高度+1的那个集合，将4，5，3加入其中

        if (height + 1 > results.size()) {
            results.add(new ArrayList<>());
        }
        results.get(height).add(root.val);

        // 本次递归结束之后需要将高度返回到上层递归
        return height;
    }

    // Leetcode 662. Maximum Width of Binary Tree
    public static int widthOfBinaryTree(TreeNode root) {
        if (root == null) return 0;

        int result = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        Map<TreeNode, Integer> hashmap = new HashMap<>();
        hashmap.put(root, 0);

        while (!queue.isEmpty()) {
            int size = queue.size();
            int min = 0;
            int max = 0;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                int index = hashmap.get(node);
                if (i == 0) {
                    min = hashmap.get(node);
                }
                if (i == size - 1) {
                    max = hashmap.get(node);
                }

                if (node.left != null) {
                    queue.add(node.left);
                    hashmap.put(node.left, index * 2 + 1);
                }
                if (node.right != null) {
                    queue.add(node.right);
                    hashmap.put(node.right, index * 2 + 2);
                }
            }
            result = Math.max(result, max - min + 1);
        }

        return result;
    }

    // Leetcode 515. Find Largest Value in Each Tree Row
    // Time Complexity: ON
    // Space Complexity: OH
    public static List<Integer> largestValues(TreeNode root) {
        List<Integer> results = new ArrayList<>();

        if (root == null) return results;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            int result = Integer.MIN_VALUE;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                result = Math.max(result, node.val);

                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            results.add(result);
        }

        return results;
    }
}
