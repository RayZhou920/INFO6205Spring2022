package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
	// Assignment7 Code by Rui Zhou
    }

    // 133. Clone Graph
    // Definition for a Node.
    class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
    // Time Complexity: ONM
    // Space Complexity: ON
    class Solution {
        public Node cloneGraph(Node node) {
            if (node == null) return null;
            // DFS -> copy the node along with DFS
            // record whether we have traversed the node because this is undirectioonal no-cyclyic graph
            Map<Node, Node> hashmap = new HashMap<>();
            hashmap.put(node, new Node(node.val));
            dfs(hashmap, node);
            return hashmap.get(node);
        }

        private void dfs(Map<Node, Node> hashmap, Node node) {
            List<Node> neighbors = node.neighbors;
            if (neighbors == null || neighbors.size() == 0) return;
            // 获得当前node的一个neighbor
            for (Node neighbor : neighbors) {
                // new一个同名neighbor
                if (hashmap.containsKey(neighbor)) {
                    hashmap.get(node).neighbors.add(hashmap.get(neighbor));
                }
                else {
                    Node next = new Node(neighbor.val);
                    // 加入之前new的node的neighbors里面
                    hashmap.get(node).neighbors.add(next);
                    // 当前的neighbor的对应的copy的neighbor
                    hashmap.put(neighbor, next);
                    dfs(hashmap, neighbor);
                }
            }
        }
    }

    // 847. Shortest Path Visiting All Nodes
    // BFS + Bit Manipulation
    class Solution2 {
        public int shortestPathLength(int[][] graph) {
            if (graph == null || graph.length == 0) return 0;

            int n = graph.length;

            Queue<int[]> queue = new LinkedList<>();
            boolean[][] visited = new boolean[n][1 << n];
            for (int i = 0; i < n; i++) {
                queue.add(new int[]{i, 1 << i, 0});
                visited[i][1 << i] = true;
            }

            while (!queue.isEmpty()) {
                int[] cur = queue.poll();
                int index = cur[0];
                int mask = cur[1];
                int dist = cur[2];

                if (mask == (1 << n) - 1) return dist;

                for (int i : graph[index]) {
                    int next = mask | (1 << i);
                    if (!visited[i][next]) {
                        queue.add(new int[]{i, next, dist + 1});
                        visited[i][next] = true;
                    }
                }
            }

            return 0;
        }
    }

    // 2065. Maximum Path Quality of a Graph
    // Backtracking
    class Solution3 {
        int res = 0;
        public int maximalPathQuality(int[] values, int[][] edges, int maxTime) {
            // if (edges == null || edges.length == 0) return 0;

            Map<Integer, List<int[]>> hashmap = new HashMap<>();
            for (int[] edge : edges) {
                int start = edge[0];
                int end = edge[1];
                int value = edge[2];
                hashmap.putIfAbsent(start, new ArrayList<>());
                hashmap.putIfAbsent(end, new ArrayList<>());
                int[] endValues = new int[]{end, value};
                int[] startValues = new int[]{start, value};
                hashmap.get(start).add(endValues);
                hashmap.get(end).add(startValues);
            }

            boolean[] seen = new boolean[values.length];
            seen[0] = true;

            Set<Integer> hashset = new HashSet<>();
            hashset.add(0);

            backtracking(values, hashmap, maxTime, 0, 0, values[0], seen, hashset);
            return res;
        }

        // (each node's value is added at most once to the sum).
        private void backtracking(int[] values, Map<Integer, List<int[]>> hashmap, int maxTime, int start, int time, int cost, boolean[] seen, Set<Integer> hashset) {
            if (time > maxTime) return;
            // 时间在maxTime范围内，并且最后返回到了原点0
            if (time <= maxTime && start == 0) {
                res = Math.max(res, cost);
            }

            // cur:0 -> 0 -> 0
            for (int[] choices : hashmap.getOrDefault(start, new ArrayList<>())) {
                if (choices == null || choices.length == 0) continue;
                int next = choices[0];
                int spend = choices[1];
                time += spend;
                boolean exist = hashset.contains(next) ? true : false;
                if (exist == false) {
                    cost += values[next];
                    hashset.add(next);
                }

                backtracking(values, hashmap, maxTime, next, time, cost, seen, hashset);
                time -= spend;
                if (exist == false) {
                    cost -= values[next];
                    hashset.remove(next);
                }
            }
        }
    }

}
