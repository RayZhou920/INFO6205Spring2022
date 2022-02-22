package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
	    // Assignment4 Code by Rui Zhou
        ListNode node1 = new ListNode(0);
        ListNode node2 = new ListNode(1);
        ListNode node3 = new ListNode(2);
        ListNode node4 = new ListNode(3);
        ListNode node5 = new ListNode(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        ListNode node6 = new ListNode(1000);
        ListNode node7 = new ListNode(1001);
        ListNode node8 = new ListNode(1002);
        node6.next = node7;
        node7.next = node8;
        int a = 1;
        int b = 2;
        ListNode newHead = mergeInBetween(node1, a, b, node6);
        while (newHead != null) {
            System.out.println(newHead.val);
            newHead = newHead.next;
        }

        Solution solution = new Solution(node1);
        System.out.println(solution.getRandom());
    }

    // definition of ListNode
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // LeetCode: 1669. Merge In Between Linked Lists
    // Time Complexity: ON
    public static ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        int count = 0;
        ListNode dummy = new ListNode(-1);
        dummy.next = list1;
        ListNode cur = list1;
        ListNode tail = list2;
        while (tail.next != null) {
            tail = tail.next;
        }

        ListNode temp = null;
        ListNode next = null;

        while (cur != null) {
            if (count + 1 == a) {
                temp = cur.next;
                cur.next = list2;
                cur = temp;
                count++;
                continue;
            }
            if (count == b) {
                next = cur.next;
                cur.next = null;
                tail.next = next;
            }

            cur = cur.next;
            count++;
        }
        return dummy.next;
    }

    // Leetcode: 86. Partition List
    // TC: ON
    public static ListNode partition(ListNode head, int x) {
        if (head == null) return head;

        ListNode small = new ListNode(-1);
        ListNode large = new ListNode(-2);
        ListNode temp1 = small;
        ListNode temp2 = large;

        ListNode cur = head;

        while (cur != null) {
            ListNode temp = null;
            if (cur.val < x) {
                temp1.next = cur;
                temp1 = cur;
                temp = cur.next;
                temp1.next = null;
            }
            else {
                temp2.next = cur;
                temp2 = cur;
                temp = cur.next;
                temp2.next = null;
            }

            cur = temp;
        }

        temp1.next = large.next;
        large.next = null;

        return small.next;
    }

    // Leetcode: 2074. Reverse Nodes in Even Length Groups
    // TC: ON
    public static ListNode reverseEvenLengthGroups(ListNode head) {
        if (head == null) return head;

        int group = 1;
        int count = 1;

        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode cur = head;

        while (cur != null) {
            if (cur.next != null && helper(group, count) == true) {
                group++;
                ListNode node = cur;
                int number = 0;
                while (node != null && number != group) {
                    node = node.next;
                    number++;
                }
                ListNode temp = null;
                if (node != null) {
                    temp = node.next;
                }
                else {
                    number = number - 1;
                }
                if (number % 2 == 0) {
                    if (node != null) node.next = null;
                    ListNode newHead = reverse(cur.next);
                    ListNode next = cur.next;
                    cur.next = newHead;
                    next.next = temp;
                }
            }
            count++;
            cur = cur.next;
        }
        return dummy.next;
    }

    private static ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    private static boolean helper(int group, int count) {
        int num1 = group;
        int num2 = count;
        int sum = 0;
        for (int i = 1; i <= group; i++) {
            int value = i;
            sum += value;
        }
        if (sum == num2) {
            return true;
        }
        else {
            return false;
        }
    }

    // Leetcode: 2058. Find the Minimum and Maximum Number of Nodes Between Critical Points
    // TC: ON
    public static int[] nodesBetweenCriticalPoints(ListNode head) {
        int[] result = new int[2];

        if (head == null) return new int[]{-1, -1};

        ListNode pre = head;
        ListNode cur = head.next;
        int count = 1;

        List<Integer> list = new ArrayList<>();

        while (cur != null) {
            // min
            if (cur.next != null && cur.val < pre.val && cur.val < cur.next.val) {
                list.add(count);
            }
            // max
            if (cur.next != null && cur.val > pre.val && cur.val > cur.next.val) {
                list.add(count);
            }
            count++;
            pre = cur;
            cur = cur.next;
        }

        if (count == 2) return new int[]{-1, -1};
        if (list.size() < 2) return new int[]{-1, -1};

        result[0] = Integer.MAX_VALUE;

        for (int i = 0; i < list.size() - 1; i++) {
            result[0] = Math.min(result[0], list.get(i + 1) - list.get(i));
        }

        result[1] = list.get(list.size() - 1) - list.get(0);

        return result;
    }

    // Leetcode: 148. Sort List
    // TC: ONlogN for merge sort
    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode cur = head;

        while (cur != null) {
            ListNode mid = findMid(cur);
            ListNode temp = mid.next;
            mid.next = null;

            ListNode left = sortList(head); //4->2
            ListNode right = sortList(temp); //1->3
            return merge(left, right);
        }

        return head;
    }

    private static ListNode findMid(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private static ListNode merge(ListNode node1, ListNode node2) {
        if (node1 == null && node2 == null) {
            return null;
        }

        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;

        while (node1 != null && node2 != null) {
            if (node1.val <= node2.val) {
                cur.next = node1;
                cur = node1;
                node1 = node1.next;
            }
            else {
                cur.next = node2;
                cur = node2;
                node2 = node2.next;
            }
        }

        while (node1 != null) {
            cur.next = node1;
            cur = node1;
            node1 = node1.next;
        }

        while (node2 != null) {
            cur.next = node2;
            cur = node2;
            node2 = node2.next;
        }

        return dummy.next;
    }

    // Leetcode: 382. Linked List Random Node
    // TC: ON for the getRandom method
    public static class Solution {
        Random random = new Random();
        ListNode cur;
        public Solution(ListNode head) {
            cur = head;
        }
        public int getRandom() {
            int result = 0;
            int length = 1;
            ListNode newCur = cur;

            while (newCur != null) {
                int value = random.nextInt(length);
                if (value == 0) {
                    result = newCur.val;
                }
                length++;
                newCur = newCur.next;
            }
            return result;
        }
    }

    // Leetcode: 92. Reverse Linked List II
    // TC: ON
    public static ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null) return null;

        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;

        ListNode pre = dummyNode;
        ListNode cur = head;

        int count = 1;
        while (count < left) {
            pre = cur;
            cur = cur.next;
            count++;
        }

        ListNode node = pre;

        while (count <= right) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
            count++;
        }

        node.next.next = cur;
        node.next = pre;

        return dummyNode.next;
    }

    // Leetcode: 725. Split Linked List in Parts
    // TC: ON
    public static ListNode[] splitListToParts(ListNode head, int k) {
        if (head == null) return new ListNode[k];

        ListNode cur = head;
        int count = 0;

        while (cur != null) {
            count++;
            cur = cur.next;
        }

        ListNode[] result = new ListNode[k];
        int index = 0;

        ListNode node = head;
        while (count <= k && node != null) {
            if (index >= result.length) break;
            result[index] = node;
            ListNode temp = node.next;
            node.next = null;
            node = temp;
            index++;
        }

        // 10/3=3 10/2=5
        // 4 3 3
        index = 0;
        node = head;
        int part = count / k;
        int key = count % k;
        int number = 1;
        result[index] = node;

        // [1,2,3,4,5,6,7,8,9,10], k = 3
        // 10 / 3 = 3
        // 4, 3, 3,
        // p+1, p, p

        while (count > k && node != null) {
            if (key != 0) {
                if (number == part + 1) {
                    index++;
                    if (index >= result.length) break;
                    result[index] = node.next;
                    ListNode temp = node.next;
                    node.next = null;
                    node = temp;
                    number = 1;
                    key--;
                }
                else {
                    number++;
                    node = node.next;
                }

            }
            else if (key == 0) {
                if (number == part) {
                    index++;
                    if (index >= result.length) break;
                    result[index] = node.next;
                    ListNode temp = node.next;
                    node.next = null;
                    node = temp;
                    number = 1;
                }
                else {
                    number++;
                    node = node.next;
                }
            }

        }

        return result;
    }

    // Leetcode: 817. Linked List Components
    // TC: O(M+N)
    public static int numComponents(ListNode head, int[] nums) {
        List<Integer> list = new ArrayList<>();

        ListNode cur = head;

        while (cur != null) {
            list.add(cur.val);
            cur = cur.next;
        }

        int count = 0;

        Set<Integer> hashset = new HashSet<>();

        for (int i : nums) {
            hashset.add(i);
        }

        // [0, 1, 2, 3, 4] --- list
        // [0, 2, 3] --- nums

        for (int i = 0; i < list.size(); i++) {
            int curr = list.get(i);
            if (i == list.size() - 1 && hashset.contains(list.get(i))) {
                count++;
                break;
            }
            if (hashset.contains(curr) && !hashset.contains(list.get(i + 1))) {
                count++;
            }
        }

        return count;
    }

    // Leetcode: 2130. Maximum Twin Sum of a Linked List
    // TC: ON
    public static int pairSum(ListNode head) {
        if (head == null) return 0;

        List<Integer> list = new ArrayList<>();
        int length = 0;

        while (head != null) {
            list.add(head.val);
            length++;
            head = head.next;
        }

        int result = 0;
        int left = 0;
        int right = list.size() - 1;
        while (left < right) {
            result = Math.max(list.get(left) + list.get(right), result);
            left++;
            right--;
        }

        return result;
    }
}
