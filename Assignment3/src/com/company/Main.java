package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        // Assignment 3 Code by Rui Zhou
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(4);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(5);
        ListNode node5 = new ListNode(6);
        ListNode node6 = new ListNode(4);
        node1.next = node2;
        node2.next = node3;
        node4.next = node5;
        node5.next = node6;
        ListNode finalNode = addTwoNumbers(node1, node4);
        while (finalNode != null) {
            System.out.println(finalNode.val);
            finalNode = finalNode.next;
        }
    }

    // Leetcode 2. Add Two Numbers
    // Time Complexity: ON
    // Definition for singly-linked list.
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) return null;

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
            }
            else if (l2 == null) {
                sum = (l1.val + carry) % 10;
                carry = l1.val + carry >= 10 ? 1 : 0;
            }
            else {
                int l1val = l1.val;
                int l2val = l2.val;
                sum = l1val + l2val + carry;
                if (sum >= 10) {
                    sum = sum % 10;
                    carry = 1;
                }
                else {
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

    // Leetcode 138. Copy List with Random Pointer
    // Time Complexity: ON
    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public static Node copyRandomList(Node head) {
        if (head == null) return head;

        Map<Node, Node> hashmap = new HashMap<>();

        Node cur = head;
        while (cur != null) {
            hashmap.put(cur, new Node(cur.val));
            cur = cur.next;
        }

        Node cur2 = head;
        while (cur2 != null) {
            hashmap.get(cur2).next = hashmap.get(cur2.next);
            hashmap.get(cur2).random = hashmap.get(cur2.random);
            cur2 = cur2.next;
        }
        return hashmap.get(head);
    }

    // Leetcode 23. Merge k Sorted Lists
    // Time Complexity: ONlogK
    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        return helper(lists, 0, lists.length - 1);
    }

    private static ListNode helper(ListNode[] lists, int left, int right) {
        if (left == right) {
            return lists[left];
        }

        if (left > right) return null;

        if (left < right) {
            int mid = (left + right) / 2;
            ListNode node1 = helper(lists, left, mid);
            ListNode node2 = helper(lists, mid + 1, right);
            return merge(node1, node2);
        }

        return null;
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

    // Leetcode 143. Reorder List
    // Time Complexity: ON
    public static void reorderList(ListNode head) {
         if (head == null) return;

         // 1. find the middle of the linked list
         ListNode slow = head;
         ListNode fast = head;
         while (fast != null && fast.next != null) {
             fast = fast.next.next;
             slow = slow.next;
         }

         // 2. reverse the half part of the linked list
         ListNode newHead = reverse(slow);

         // 3. merge sort
         ListNode left = head;
         ListNode right = newHead;

         while (right.next != null) {
             ListNode temp = left.next;
             left.next = right;
             left = temp;

             ListNode temp2 = right.next;
             right.next = left;
             right = temp2;
         }
    }

    private static ListNode reverse(ListNode slow) {
         if (slow == null) return slow;
         ListNode pre = null;
         while (slow != null) {
             ListNode next = slow.next;
             slow.next = pre;
             pre = slow;
             slow = next;
         }
         return pre;
    }

    // Leetcode 234. Palindrome Linked List
    // Time Complexity: ON
    public static boolean isPalindrome(ListNode head) {
        List<Integer> helper = new ArrayList<>();

        while (head != null) {
            helper.add(head.val);
            head = head.next;
        }

        int left = 0;
        int right = helper.size() - 1;
        while (left < right) {
            if (helper.get(left) == helper.get(right)) {
                left++;
                right--;
            }
            else {
                return false;
            }
        }

        return true;
    }

    // Leetcode 19. Remove Nth Node From End of List
    // Time Complexity: ON
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode left = dummy;
        ListNode right = dummy;

        int count = 0;
        while (count < n) {
            right = right.next;
            count++;
        }

        while (right.next != null) {
            right = right.next;
            left = left.next;
        }

        left.next = left.next.next;

        return dummy.next;
    }

    // Leetcode 328. Odd Even Linked List
    // Time Complexity: ON
    public static ListNode oddEvenList(ListNode head) {
        if (head == null) return head;

        ListNode odd = head;
        ListNode even = head.next;

        ListNode temp = head.next;
        ListNode last = null;

        while (odd != null && even != null) {
            ListNode temp1 = odd.next;
            if (odd.next != null) odd.next = odd.next.next;
            if (temp1.next == null) last = odd;
            if (temp1 != null) odd = temp1.next;

            ListNode temp2 = even.next;
            if (even.next != null) even.next = even.next.next;
            if (temp2 != null) even = temp2.next;
        }

        if (odd != null) odd.next = temp;
        if (odd == null) last.next = temp;

        return head;
    }

    // Leetcode 708. Insert into a Sorted Circular Linked List
    // Time Complexity: ON
    public static Node insert(Node head, int insertVal) {
        Node node = new Node(insertVal);

        // case1:
        if (head == null) {
            node.next = node;
            return node;
        }

        Node pre = head;
        Node cur = head.next;

        // cur回到一圈到head，作为循环的终止判断，也就是最后cur为head，pre为tail
        while (cur != head) {
            // case1:
            // 3 -> 5 ->7  (6)
            if (pre.val <= insertVal && insertVal <= cur.val) {
                break;
            }
            // case2:
            // 1 -> 3 -> 9 (10)
            // 1 -> 3 -> 9 (0)
            // pre应该始终小于等于cur，但是当pre>cur的时候，说明pre到了tail，cur到了head
            else if (pre.val > cur.val && (insertVal <= cur.val || insertVal >= pre.val)) {
                break;
            }
            // 这里还有case3:
            // 3 -> 3 -> 3 (5)
            // 这种情况可以不用判断，因为此时cur==head了，退出循环了，直接插入环形链表之中即可
            pre = cur;
            cur = cur.next;
        }

        pre.next = node;
        node.next = cur;

        return head;
    }

    // Leetcode 1019. Next Greater Node In Linked List
    // Time Complexity: ON
    public static int[] nextLargerNodes(ListNode head) {
        if (head == null) return null;

        List<Integer> list = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();

        // [2, 1, 5]
        for (ListNode node = head; node != null; node = node.next) {
            while (!stack.isEmpty() && list.get(stack.peek()) < node.val) {
                list.set(stack.pop(), node.val);
            }

            stack.push(list.size());
            list.add(node.val);
        }

        for (int i : stack) {
            list.set(i, 0);
        }

        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    // Leetcode 82. Remove Duplicates from Sorted List II
    // Time Complexity: ON
    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null) return null;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode cur = head;

        while (cur != null) {
            if (cur.next != null && cur.val == cur.next.val) {
                int value = cur.val;
                while (cur.next != null && cur.next.val == value) {
                    cur = cur.next;
                }
                cur = cur.next;
                pre.next = cur;
            }
            else {
                pre = cur;
                cur = cur.next;
            }
        }

        return dummy.next;
    }

}
