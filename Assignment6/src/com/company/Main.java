package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        // Assignment 6 Code by Rui Zhou
        TreeNode node1 = new TreeNode(10);
        TreeNode node2 = new TreeNode(5);
        TreeNode node3 = new TreeNode(-3);
        TreeNode node4 = new TreeNode(3);
        TreeNode node5 = new TreeNode(2);
        TreeNode node6 = new TreeNode(11);
        TreeNode node7 = new TreeNode(3);
        TreeNode node8 = new TreeNode(-2);
        TreeNode node9 = new TreeNode(1);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.right = node6;
        node4.left = node7;
        node4.right = node8;
        node5.left = node9;
        Solution solution1 = new Solution();
        System.out.println(solution1.pathSum(node1, 8));


    }

    // TreeNode Class
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

    // Leetcode 437. Path Sum III
    static class Solution {
        int count = 0;
        Map<Integer, Integer> hashmap;
        public int pathSum(TreeNode root, int targetSum) {
            if (root == null) return 0;

            hashmap = new HashMap<>();
            hashmap.put(0, 1);
            // sum会发生变化 在递归回溯的过程中
            dfs(root, targetSum, 0);

            return count;
        }

        private void dfs(TreeNode root, int targetSum, int sum) {
            if (root == null) return;

            // 收集当前节点的值
            sum += root.val;

            // 这里需要判断sum - targetSum 是否在hashmap中出现过
            // 为避免root头节点就是targetSum，我们需要提前在hashmap中加入一个（0，1）哨兵
            if (hashmap.containsKey(sum - targetSum)) {
                count += hashmap.get(sum - targetSum);
            }

            // 将前缀和加入hashmap中
            hashmap.put(sum, hashmap.getOrDefault(sum, 0) + 1);
            dfs(root.left, targetSum, sum);
            dfs(root.right, targetSum, sum);
            // 回溯
            // [1, -2, -3] target=-1
            // {{0, 1}, {1, 1}, {-1, 1}, {-2, 1}}
            // 当sum = -1时，-1-(-1)=0，此时map中出现，所以count+=1
            // 将{-1, 1}存入map
            // 此时左子树全部递归完，进入到叶子节点的右子树，也递归完，进行回溯到上层，如果不从hashmap中去掉{-1,1}的话，进入到右子树sum=1+（-3）-（-1）=-1
            // 此时会多计算一次{-1,1}，所以相当于是重复计算了，那么需要在当前节点的左右子树都递归结束之后把上一次递归加入map的值减掉
            hashmap.put(sum, hashmap.get(sum) - 1);
        }
    }

    // Leetcode 236. Lowest Common Ancestor of a Binary Tree
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;

        return dfs(root, p, q);
    }
    private static TreeNode dfs(TreeNode root, TreeNode p, TreeNode q) {
        // 我们没有找到p/q节点 直接返回null
        if (root == null) return root;
        // 我们找到了p/q的节点
        if (root == p || root == q) return root;
        // 找到了之后返回给left
        // 要么是left==null（代表左边没找到）
        // 要么是p/q，代表左边找到了，说明p和q都在左边
        TreeNode left = dfs(root.left, p, q);
        // 右子树找到了之后返回给right
        // 要么是right==null（代表右子树没有找到）
        // 要么是p/q，代表右边找到了，说明p和q都在右边
        TreeNode right = dfs(root.right, p, q);
        // 分别在左右两边
        if (left != null && right != null) return root;
        else if (left != null) return left;
        else if (right != null) return right;
        // 代表都没找到
        return null;
    }

    // Leetcode 687. Longest Univalue Path
    static class Solution2 {
        int res = 0;
        public int longestUnivaluePath(TreeNode root) {
            if (root == null) return 0;
            dfs(root);
            return res;
        }
        private int dfs(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int left = dfs(root.left);
            int right = dfs(root.right);
            // 特殊情况：如果根节点与左右均同值，则根节点收集同值节点数量+2
            if (root != null && root.left != null && root.right != null && root.val == root.left.val && root.val == root.right.val) {
                res = Math.max(res, left + right + 2);
            }
            // 如果根节点只与左同值，那么需要左+1，再跟右边的同值信息比较取大小
            // //从左右子树中选择最长的同值路径
            int maxPath = 0;
            if (root != null && root.left != null && root.val == root.left.val) {
                maxPath = Math.max(maxPath, left + 1);
            }
            // 如果只与右同值，那么需要右+1，再跟左边的同值信息比较取大小
            if (root != null && root.right != null && root.val == root.right.val) {
                maxPath = Math.max(maxPath, right + 1);
            }
            // 如果上面的特殊情况没有满足，需要在这里再更新下结果（全局结果），取结果最大的信息
            res = Math.max(res, maxPath);
            // 返回到上层递归/上层父节点的是左右边收集的最大同值节点数量
            return maxPath;
        }
    }

    // Leetcode 297. Serialize and Deserialize Binary Tree
    public static class Codec {
        // Encodes a tree to a single string.
        // 将二叉树转化为字符串表现形式："123nullnull45"
        public String serialize(TreeNode root) {
            if (root == null) return "";
            StringBuilder sb = new StringBuilder();
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    TreeNode cur = queue.poll();
                    if (cur != null) {
                        sb.append(cur.val + "");
                        sb.append(",");//方便拆分字符串
                    }
                    if (cur == null) {
                        sb.append("null");
                        sb.append(",");
                    }

                    if (cur != null) {
                        queue.add(cur.left);
                        queue.add(cur.right);
                    }
                }
                if (queue.isEmpty()) break;
            }

            return sb.toString();
        }

        // Decodes your encoded data to tree.
        // "123nullnull45"
        // 将字符串表现形式的二叉树转化为树
        public TreeNode deserialize(String data) {
            if (data == null || data.length() == 0) return null;

            Queue<TreeNode> queue = new LinkedList<>();
            // 将data这个字符串转化为list的形式
            List<String> list = new ArrayList<>();
            String[] strs = data.split(",");
            for (String s : strs) list.add(s);
            list.remove(list.size() - 1);
            if (list.get(0).equals("null")) return null;

            TreeNode root = new TreeNode(Integer.parseInt(list.get(0)));
            queue.add(root);

            int index = 1;
            while (!queue.isEmpty()) {
                TreeNode cur = queue.poll();
                if (index < list.size() && !list.get(index).equals("null")) {
                    TreeNode left = new TreeNode(Integer.parseInt(list.get(index)));
                    cur.left = left;
                    queue.add(left);
                }
                index++;
                if (index < list.size() && !list.get(index).equals("null")) {
                    TreeNode right = new TreeNode(Integer.parseInt(list.get(index)));
                    cur.right = right;
                    queue.add(right);
                }
                index++;
            }

            return root;
        }
    }

    // Leetcode 987. Vertical Order Traversal of a Binary Tree
    public static List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> results = new ArrayList<>();

        if (root == null) return results;

        // create a pair
        // node 3: [0, 0]
        // node 15: [2, 0]
        // 按照col归为一组，
        // 当col 0相同时，按照row 进行排序，所以0在2前面，3在15前面
        // 如果col和row都相同，按照节点值大小从小到大排序
        /*
        仔细看题目，本题其实涉及到3个排序
        i. 列坐标 j 的排序 - treemap的key - 里面嵌套第二层的treemap
        ii. 列坐标相同的行坐标 i 的排序 - 第二层的treemap，key为横坐标，（列坐标相同时用横坐标排序）
        iii. 坐标相同的节点值 val 的排序 - 第二层的treemap，value为pq，（横列坐标都相同时用value值排序）
        所以为了方便排序，前面在存储坐标时，列坐标在行坐标的前面
        */

        // key纵坐标 - value：map(横坐标， 节点值)
        TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> map = new TreeMap<>();

        dfs(root, 0, 0, map);

        // 对纵坐标for循环
        for (TreeMap<Integer, PriorityQueue<Integer>> tree : map.values()) {
            List<Integer> res = new ArrayList<>();
            // 对横坐标for循环
            for (PriorityQueue<Integer> pq : tree.values()) {
                while (!pq.isEmpty()) {
                    res.add(pq.poll());
                }
            }
            results.add(new ArrayList<>(res));
        }

        return results;
    }
    private static void dfs(TreeNode root, int x, int y, TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> map) {
        if (root == null) {
            return;
        }

        // 如果map不包含这个纵坐标
        if (!map.containsKey(y)) {
            map.put(y, new TreeMap<>());
        }
        // 如果map包括纵坐标这个key，不包含横坐标
        if (!map.get(y).containsKey(x)) {
            map.get(y).put(x, new PriorityQueue<>());
        }
        map.get(y).get(x).add(root.val);

        dfs(root.left, x + 1, y - 1, map);
        dfs(root.right, x + 1, y + 1, map);
    }

    // Leetcode 222. Count Complete Tree Nodes
    public static int countNodes(TreeNode root) {
        if (root == null) return 0;
        // 当前节点为root
        // 得到左子树的高度
        int leftDepth = getLeftDepth(root);
        // 得到右子树的高度
        int rightDepth = getRightDepth(root);
        // 如果左右子树的高度一样，那么返回的节点值为2^level + 1
        if (leftDepth == rightDepth) {
            return (int)Math.pow(2, leftDepth) - 1;
        }
        // 否则我们递归求左右子树的高度，两者相加并最后加1
        // 递归左子树看左右两边的高度
        // 递归右子树看左右两边的高度
        return countNodes(root.left) + countNodes(root.right) + 1;
    }
    private static int getLeftDepth(TreeNode root) {
        int level = 0;
        while (root != null) {
            level++;
            root = root.left;
        }
        return level;
    }
    private static int getRightDepth(TreeNode root) {
        int level = 0;
        while (root != null) {
            level++;
            root = root.right;
        }
        return level;
    }

    // Leetcode 129. Sum Root to Leaf Numbers
    static class Solution3 {
        int totalSum = 0;
        public int sumNumbers(TreeNode root) {
            if (root == null) return 0;

            List<Integer> list = new ArrayList<>();
            list.add(root.val);
            dfs(root, list);

            return totalSum;
        }

        private void dfs(TreeNode root, List<Integer> list) {
            if (root.left == null && root.right == null) {
                int sum = 0;
                for (int i : list) {
                    sum = sum * 10 + i;
                }
                totalSum += sum;
                return;
            }

            if (root.left != null) {
                list.add(root.left.val);
                dfs(root.left, list);
                list.remove(list.size() - 1);
            }

            if (root.right != null) {
                list.add(root.right.val);
                dfs(root.right, list);
                list.remove(list.size() - 1);
            }
        }
    }

    // Leetcode 1325. Delete Leaves With a Given Value
    public static TreeNode removeLeafNodes(TreeNode root, int target) {
        if (root == null) return null;
        // DFS函数
        TreeNode node = dfs(root, target);
        return node;
    }
    // 后序遍历对树进行遍历，采取左 右 根的顺序
    // 即树的左右子树都已经遍历完了，再对根节点处进行操作
    private static TreeNode dfs(TreeNode root, int target) {
        if (root == null) {
            return null;
        }
        TreeNode left = dfs(root.left, target);
        TreeNode right = dfs(root.right, target);
        // 当前root为3，左节点为null，右节点为null
        // 要删掉当前root节点3，可以向上一层root进行返回的时候，返回空节点即可
        // 所以我们可以不用刻意去做删除操作，只需要利用DFS往上返回的过程中遇到我们需要删除的目标节点
        // 我们对上一层返回null节点即可
        // 先将root指向左右孩子节点
        root.left = left;
        root.right = right;
        // 判断当前的root是否符合target
        if (root.left == null && root.right == null && root.val == target) {
            return null;// 注意这里的return null是对当前root的上一层root返回null，相当于是删除了当前root
        }
        // 否则不用删除，我们直接返回当前root即可
        return root;
    }
}
