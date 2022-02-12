package com.company;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
	// write your code here
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
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
    public Node copyRandomList(Node head) {
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
    
}
