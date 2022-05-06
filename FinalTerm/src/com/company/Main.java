package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String str = "aabcccccaaa";
        System.out.println(compressString(str));
    }

    // Question 1: String Compression
    /*
    Input: "aabcccccaaa"
    Output: "a2b1c5a3"
     */
    public static String compressString(String str){
        // corner case
        if (str == null || str.length() == 0) return "";

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char cur = str.charAt(i);
            int j = i;
            while (j < str.length() && str.charAt(j) == cur) {
                j++;
            }
            int count = j - i;
            sb.append(cur);
            sb.append(String.valueOf(count));
            i = j - 1;
        }

        if (sb.toString().length() >= str.length()) return str;
        else return sb.toString();
    }

    // Question 2: Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.

    public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;

        int count = 0;
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    dfs(i, j, grid, dirs);
                    count++;
                }
            }
        }

        return count;
    }

    private static void dfs(int row, int col, char[][] grid, int[][] dirs) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length) return;

        if (grid[row][col] != '1') return;

        grid[row][col] = '2';
        for (int[] dir : dirs) {
            int x = dir[0] + row;
            int y = dir[1] + col;
            dfs(x, y, grid, dirs);
        }
    }

    // Question 3: Given an array of strings strs, group the anagrams together.
    // You can return the answer in any order.
    /*
    Input: strs = ["eat","tea","tan","ate","nat","bat"]
    Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> results = new ArrayList<>();
        if (strs == null || strs.length == 0) return results;

        Map<String, List<String>> hashmap = new HashMap<>();
        for (String str : strs) {
            char[] temp = str.toCharArray();
            Arrays.sort(temp);
            StringBuilder sb = new StringBuilder();
            for (char c : temp) sb.append(c);
            String next = sb.toString();
            hashmap.putIfAbsent(next, new ArrayList<>());
            hashmap.get(next).add(str);
        }

        for (String s : hashmap.keySet()) {
            List<String> neighbors = hashmap.get(s);
            results.add(new ArrayList<>(neighbors));
        }

        return results;
    }

    // Question 4: Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
    /*
    Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
Output: 6
     */

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        public TreeNode(int val) {
            this.val = val;
        }
        public TreeNode() {}
    }
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;

        return dfs(root, p, q);
    }

    private static TreeNode dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;

        if (root == p || root == q) return root;

        TreeNode left = dfs(root.left, p, q);
        TreeNode right = dfs(root.right, p, q);
        if (left != null && right != null) return root;
        else if (left != null) return left;
        else return right;
    }

}
