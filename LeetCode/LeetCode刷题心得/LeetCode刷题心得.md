---
title: LeetCode刷题心得
date: 2018-12-12 12:34:35
tags: 数据机构 
categories: 数据结构与算法
toc: true
---

# LeetCode刷题心得

- 181212 链表刷题
<!--more-->
## 数组



## 链表

### 链表设计

- 单链表，双链表，循环链表
- 合理增加哨兵节点（head、tail节点）
  - head节点是不是要放在第一个节点位置，还是在第一个节点前；tail指向最后一个节点
- 删除节点p怎么方便？

> 通常遇到都会考虑，找到p前面一个节点q，然后q->next = q->next->next
>
> /
>
> 还有一种是找到节点p，然后替换p的值为后一个节点的值(`p.val = p.next.val`)，然后`p->next = p->next-next`

### 双指针应用

#### 判断链表有无环（环的入口处节点位置，环的长度）

> 我们遍历所有结点并在哈希表中存储每个结点的引用（或内存地址）。如果当前结点为空结点 `null`（即已检测到链表尾部的下一个结点），那么我们已经遍历完整个链表，并且该链表不是环形链表。如果当前结点的引用已经存在于哈希表中，那么返回 `true`（即该链表为环形链表）。 
>
> 时间空间复杂度都为O(n)

第二种方法

> 两个指针，一个步长为一，一个为二。只要二者遍历时重合，必定存在环
>
> 时间复杂度O(n)，空间复杂度O(1)
>
> **求环的入口点**：p1和p2重合时，把p1拉回head处，p1、p2同步长，再次相交处就是入口点q
>
> **求环的长度**：由上，再次相交后，p2再次走一圈，回到q处所走的距离就是环的长度
>
> **关键在于以上步长能够保证p1，p2相遇时，p1不可能走完一圈**



```java
public boolean hasCycle(ListNode head) {//	判断有环
        ListNode p1 = head;
        ListNode p2 = head;
        
        while(p2 != null && p2.next != null){
            p1 = p1.next;
            p2 = p2.next.next;
            
            if(p1==p2)
                return true;
        }
        return false;
    }

public ListNode detectCycle(ListNode head) {// 有环的话，求解环入口节点
        ListNode p1 = head;
        ListNode p2 = head;
        while(p2 != null && p2.next != null){
            p1 = p1.next;
            p2 = p2.next.next;
            
            if(p1 == p2){
                p1 = head;
                int index = 0;
                while(p1!=p2){
                    p1 = p1.next;
                    p2 = p2.next;
                    index++;
                }
                return p1;
            }
        }
        return null;
    }
```



#### 判断两个单链表是否相交及找到第一个交点 [160. Intersection of Two Linked Lists](https://leetcode-cn.com/problems/intersection-of-two-linked-lists/) 

【这道题默认单链表无环，下面链接给出了有环的解法】

最简单解法：两个链表相交的话，链表长度和一定相等，每次遍历到尾部时，再次从另一链表头部出发，一定会相交。

如果不相交，如果链表长度相等，第一次遍历就会结束；不相等，第二次遍历会结束

```java
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null)
            return null;
        ListNode node1 = headA;
        ListNode node2 = headB;
        while (node1 != node2) {
            node1 = (node1 == null ? headB : node1.next);
            node2 = (node2 == null ? headA : node2.next);
        }
        return node1;
    }
```



这道题解法也是妙，把相交转化为求解有无环

> 先遍历第一个链表到它的尾部，然后将尾部的next指针指向第二个链表(尾部指针的next本来指向的是null)。这样两个链表就合成了一个链表。若该链表有环，则原两个链表一定相交。否则，不相交。
>
> 这样进行转换后就可以从链表头部进行判断了，其实并不用。通过简单的了解我们就很容易知道，如果新链表是有环的，那么原来第二个链表的头部一定在环上。因此我们就可以从第二个链表的头部进行遍历的，从而减少了时间复杂度(减少的时间复杂度是第一个链表的长度)
>
> 
> 参考：https://blog.csdn.net/fengxinlinux/article/details/78885764 



```java
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA==null||headB==null)
            return null;
        
        ListNode h1 = headA;
        // ListNode h2 = headB;
        while(h1.next!=null){
             h1 = h1.next;
        }
        h1.next = headB;
        return findCyclw(headA);
    }
public ListNode findCycle(ListNode head){
        ListNode p1 = head;
        ListNode p2 = head;
        while(p2 != null && p2.next != null){
            p1 = p1.next;
            p2 = p2.next.next;
            
            if(p1 == p2){
                p1 = head;
                int index = 0;
                while(p1!=p2){
                    p1 = p1.next;
                    p2 = p2.next;
                    index++;
                }
                return p1;
            }
        }
        return null;
    }
```

#### 不知道单链表有无环如何判断是否相交？

https://blog.csdn.net/liuxialong/article/details/6556096

> ```markdown
> 先判断是否有环，判断是否有环可以使用追逐办法，设置两个指针，一个走一步，一个走两步，如果能相遇则说明存在环
> （1）两个都没环，就是上面解题思路
> （2）**一个有环，一个没环，肯定两链表不相交**。如果相交，相交节点处next就有两个指针了
> （3）两个都有环：判断链表A的碰撞点是否出现在链表B的环中，如果在，则相交。（相交时，环必定是两链表共有的）
> 
> ```



![](/leetcode/Cycle.png)

#### 删除链表倒数第N个节点

让一个指针先走N步，然后两个指针一起走即可

注意边界点：最后一个节点和**第一个节点**删除问题

> public ListNode removeNthFromEnd(ListNode head, int n) {// 返回头结点
> ​        ListNode p1 = head;
> ​        ListNode p2 = head;
> ​        while(n>0){
> ​            n--;
> ​            p2 = p2.next;
> ​        }
> ​        if(p2==null){// 删除第一个节点的情况
> ​            head = head.next;
> ​            return head;
> ​        }
> ​        while(p2.next!=null){
> ​            p1 = p1.next;
> ​            p2 = p2.next;
> ​        }
> ​        p1.next = p1.next.next;
> ​        return head;
> ​    }

#### 判断链表中点位置

> 同样是两个指针，一个步长为一，一个为二。步长为二的指针走到终点时，另一个指针走到中点。
>
> **注意奇偶个数节点所得中点不同**

### 链表简单题



#### 反转链表

![1545790754297](/leetcode/reverseList.png)

思路就是每次把节点1后面的节点移到head处（作为head节点），时间复杂度O(n)，空间复杂度O(1)

```java
// LeetCode提交100%，0ms
class Solution {
    public ListNode reverseList(ListNode head) {
        if(head==null||head.next==null)
            return head;
        ListNode q = head;
        ListNode p = head.next;
        while(p!=null){
            ListNode flag = p.next;
            q.next = flag;
            p.next = head;
            head = p;
            p = flag;
        }
        return head;
    }
}
```

#### 删除链表中所有等于val的节点

```java
public ListNode removeElements(ListNode head, int val) {
        if(head==null){
            return null;
        }
    	// 要么创建一个哨兵节点置于最前面
        // ListNode p = new ListNode(0);
        // p.next = head;
        // head = p;
        // while(p.next != null){
        //     if(p.next.val==val){
        //         p.next = p.next.next;
        //     }else{
        //         p = p.next;
        //     }
        // }
        // return head.next;
    	// 要么最后来处理head.val==val的情况
        ListNode p = head;
        while(p.next!=null){
            if(p.next.val==val)
                p.next = p.next.next;
            else
                p = p.next;
        }
        if(head.val==val)
            head = head.next;
        return head;
    }
```

看到有一种递归的提交解法

```java
public ListNode removeElements(ListNode head, int val) {
    if (head == null)
        return null
    head.next = removeElements(head.next, val);
    return head.val == val ? head.next : head;
}
```

#### 奇偶链表

给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。请注意，这里的**奇数节点和偶数节点指的是节点编号的奇偶性**，而不是节点的值的奇偶性。

请尝试使用原地算法完成。你的算法的空间复杂度应为 O(1)，时间复杂度应为 O(nodes)，nodes 为节点总数

- 要求
  - 应当保持奇数节点和偶数节点的相对顺序。
  - 链表的第一个节点视为奇数节点，第二个节点视为偶数节点，以此类推。

![1545793841798](/leetcode/奇偶链表.png)

```java
public ListNode oddEvenList(ListNode head) {
        if(head==null)
            return null;
        ListNode p = head;
        ListNode q = head.next,tmp = head.next;
        //tmp指针使得每次调整位置时，无需将如图所示节点3的next指向节点2，而是最后将奇链表最后一个节点的next指向节点2

        while(p.next!=null&&q.next!=null){
            p.next = p.next.next;
            q.next = q.next.next;
            p = p.next;
            q = q.next;
        }
        p.next = tmp;
        return head;
    }
```

如下是二次改进版，提交耗时3ms，上面的7ms

```java
    while(p.next!=null&&q.next!=null){
        // 注意，以下不会出现指针指向空指针的next节点，这种情况根本进不了循环
        p.next = q.next;
        p = p.next;
        q.next = p.next;
        q = q.next;
    }
```

如上写法时间开销更少，好处在于链表遍历次数减少，如果是`p.next = p.next.next`，指针需跳两次循环一次就是四次，所以n个节点时间复杂度O(2n)，而直接`q.next`是O(n)

#### 回文链表判断  [234. Palindrome Linked List](https://leetcode-cn.com/problems/palindrome-linked-list/)

要求Could you do it in O(n) time and O(1) space 



想法一：自己上手思路是翻转链表，再逐个遍历比较。翻转O(n)，遍历O(n)，时间复杂度O(n)；另外还需O(n)的空间复杂度（比较的话，需复制一个链表）

想法二：其次是用栈stack保存前半段数据，通过快慢指针，先走到中点处（此时stack中已存下前半段数据），继续从中点处开始遍历，出栈逐个比较。时间复杂度O(n)，空间复杂度O(n/2)，更好点

看了下别人的题解，我去，也是翻转链表，不过是翻转半段链表

这题确实不错，既有快慢指针用法，有又翻转链表

```java
public boolean isPalindrome(ListNode head) {
        if(head==null||head.next==null)
            return true;
        
        ListNode fast = head,slow = head;
        while(fast!=null&&fast.next!=null){// 找到中点slow
            slow = slow.next;
            fast = fast.next.next;
        }
        if(fast==null){// 偶
            slow = reverseList(slow);
        }else{// 奇
            slow = reverseList(slow.next);
        }
        while(slow!=null){
            if(head.val!=slow.val)
                return false;
            head = head.next;
            slow = slow.next;
        }
        return true;
    }
public ListNode reverseList(ListNode head){
        if(head==null||head.next==null)
            return head;
        ListNode p = head.next,q = head;
        while(p!=null){
            ListNode flag = p.next;
            q.next = flag;
            p.next = head;
            head = p;
            p = flag;
        }
        return head;
    }
```



### 个人感触：遍历next节点时，务必判断当前节点是否为空；

链表中双指针多是一个步长一，一个步长二

**复杂度分析**

```
空间复杂度分析容易。如果只使用指针，而不使用任何其他额外的空间，那么空间复杂度将是 O(1)。但是，时间复杂度的分析比较困难。为了得到答案，我们需要分析运行循环的次数。

在前面的查找循环示例中，假设我们每次移动较快的指针 2 步，每次移动较慢的指针 1 步。

1. 如果没有循环，快指针需要 N/2 次才能到达链表的末尾，其中 N 是链表的长度。
2. 如果存在循环，则快指针需要 M 次才能赶上慢指针，其中 M 是列表中循环的长度。

显然，M <= N 。所以我们将循环运行 N 次。对于每次循环，我们只需要常量级的时间。因此，该算法的时间复杂度总共为 O(N)。

```