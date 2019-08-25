
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * 面试题1~30
 * @author JiaYi
 */
public class Solution {

    /**
     * 面试题3：数组中重复的数字
     * @param numbers
     * @param length
     * @param duplication
     * @return boolean
     */
    public boolean duplicate(int [] numbers,int length,int [] duplication) {
        boolean result = false;
        HashMap<Integer, Integer> map = new HashMap(16);
        for (int i = 0; i < length; i++) {
            if (map.containsKey(numbers[i])) {
                duplication[0] = numbers[i];
                result = true;
                break;
            }
            map.put(numbers[i], 1);
        }
        return result;
    }

    /**
     * 面试题4：二维数组中的查找
     * @param target
     * @param array
     * @return boolean
     */
    public boolean Find(int target, int [][] array) {
        // 矩阵行数
        int m = array.length;
        if (m == 0){
            return false;
        }
        // 矩阵列数
        int n = array[0].length;
        // 从右上角开始搜索
        int i= 0;
        int j = n-1;
        while(i<m && j>=0){
            if (array[i][j] == target){
                return true;
            }else if(array[i][j] > target){
                j--;
            }else{
                i++;
            }
        }
        return false;
    }

    /**
     * 面试题5：替换空格
     * @param str
     * @return
     */
    public static String replaceSpace(StringBuffer str) {
        // 原来字符串长度
        int len = str.length();
        // count是字符串中空格数量
        int count = 0;
        if (len == 0) {
            return str.toString();
        }
        for (int i = 0; i<len; i++) {
            if (str.charAt(i) == ' ') {
                count++;
            }
        }
        // 新字符串长度
        int n_length = len + count*2;
        // 原来字符串的索引
        int index_old = len-1;
        int index_new = n_length-1;
        str.setLength(n_length);
        while(index_old >= 0 && index_new > index_old) {
            if(str.charAt(index_old)== ' '){
                str.setCharAt(index_new--, '0');
                str.setCharAt(index_new--, '2');
                str.setCharAt(index_new--, '%');
            }else{
                str.setCharAt(index_new--, str.charAt(index_old));
            }
            --index_old;
        }
        return str.toString();
    }

    /**
     * 面试题6：从尾到头打印链表
     * @param listNode
     * @return
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        // 使用递归实现
        if (listNode == null) {
            return res;
        }
        printList(res, listNode);
        return res;
    }

    /**
     * 面试题6的辅助函数
     * @param integers
     * @param listNode
     */
    public void printList (ArrayList<Integer> integers, ListNode listNode) {
        if (listNode.next != null) {
            printList(integers,listNode.next);
        }
        integers.add(listNode.val);

    }

    /**
     * 面试题7：重建二叉树
     * 根据前序遍历和中序遍历重建二叉树
     * 需要记住：
     * 已知前序和中序遍历，可以唯一确定一颗二叉树；
     * 已知中序和后序遍历，可以唯一确定一颗二叉树；
     * 但是知道前序和后序遍历，无法确定二叉树。
     * @param pre
     * @param in
     * @return
     */
    public TreeNode reConstructBinaryTree(int [] pre, int [] in) {
        //如果前序或者中序有一个是空直接返回
        if (pre == null || in == null) {
            return null;
        }
        // 构建二叉树的核心算法
        TreeNode root = rebuildBinaryTreeCore(pre, 0, pre.length - 1,
                in, 0, in.length - 1);
        return root;
    }

    /**
     * 面试题7的辅助核心算法
     * @param preOrder
     * @param startPreOrder
     * @param endPreOrder
     * @param inOrder
     * @param startInorder
     * @param endInorder
     * @return
     */
    public TreeNode rebuildBinaryTreeCore(int [] preOrder, int startPreOrder,
                                          int endPreOrder, int [] inOrder, int startInorder, int endInorder) {
        //停止递归的条件
        if (startPreOrder > endPreOrder || startInorder > endInorder) {
            return null;
        }
        // 前序遍历的第一个就是树的根节点
        TreeNode root = new TreeNode(preOrder[startPreOrder]);
        //在中序遍历中寻找根节点
        for (int i = startInorder; i <= endInorder; i++) {
            if (preOrder[startPreOrder] == inOrder[i]) {
                // 其中（i - startInorder）为中序排序中左子树结点的个数
                //左子树
                root.left = rebuildBinaryTreeCore(preOrder, startPreOrder + 1,
                        startPreOrder + (i - startInorder), inOrder,
                        startInorder, i - 1);
                //右子树
                root.right = rebuildBinaryTreeCore(preOrder, (i - startInorder)
                                + startPreOrder + 1, endPreOrder, inOrder, i + 1,
                        endInorder);

            }
        }
        return root;
    }

    /**
     * 面试题8：二叉树的下一个节点
     * @param pNode
     * @return
     */
    public TreeLinkNode GetNext(TreeLinkNode pNode) {
        if(pNode==null) {
            return null;
        }
        //如果有右子树，则找右子树的最左节点
        if(pNode.right!=null){
            pNode = pNode.right;
            while(pNode.left!=null) {
                pNode = pNode.left;
            }
            return pNode;
        }
        //没右子树，则找第一个当前节点是父节点左孩子的节点
        while(pNode.next!=null){
            if(pNode.next.left==pNode) {
                return pNode.next;
            }
            pNode = pNode.next;
        }
        //退到了根节点仍没找到，则返回null
        return null;
    }

    /**
     * 面试题9：用两个栈实现队列
     */
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if(stack2.size() == 0) {
            while(!stack1.isEmpty()) {
                int temp = stack1.peek();
                stack2.push(temp);
                stack1.pop();
            }
        }
        int res = stack2.peek();
        stack2.pop();
        return res;
    }

    /**
     * 面试题10：斐波那契数列
     * 题目一：求斐波那契数列的第n项
     * @param n
     * @return int
     */
    public static int fibonacci(int n) {
        // 简单解法：由下往上计算
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int f1 = 0;
        int f2 = 1;
        int result = 0;
        for(int i = 2; i <= n; i++) {
            result = f1 + f2;
            f1 = f2;
            f2 = result;
        }
        return result;
    }

    /**
     * 面试题10：斐波那契数列
     * 题目二：求斐波那契数列的第n项
     * @param target
     * @return int
     */
    public static int JumpFloor(int target) {
        //本质上就是斐波那契数列，使用递归（动态规划）来做
        if (target == 0) {
            return 1;
        }
        if (target == 1) {
            return 1;
        }
        return JumpFloor(target - 1) + JumpFloor(target - 2);
    }

    /**
     * 面试题11：旋转数组的最小数字
     * 应用二分查找可以将时间复杂度降低为O(logn)。
     * @param array
     * @return
     */
    public int minNumberInRotateArray(int [] array) {
        int low = 0;
        int high = array.length-1;
        int mid = low;
        while(low <= high) {
            if (high - low == 1) {
                mid = high;
                break;
            }
            mid = (low + high) / 2;
            if (array[mid] >= array[low]) {
                low = mid;
            }else if (array[mid] <= array[high]){
                high = mid;
            }
        }
        return array[mid];
    }

    /**
     * 面试题15： 二进制中1的个数。
     * 把一个整数减去1，再和原整数做与运算，会把该整数最右边的1变成0.
     * @param n
     * @return
     */
    public int NumberOf1(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n = (n-1) & n;
        }
        return count;
    }

    /**
     * 面试题16：数值的整数次方
     *
     * @param base
     * @param exponent
     * @return
     */
    public static double Power(double base, int exponent) {
        // 考虑负指数
        int n=Math.abs(exponent);
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return base;
        }
        // 求a的n次方的简便算法
        double result = Power(base, n>>1);
        result *= result;
        if ((n & 1) == 1) {
            result *= base;
        }

        if(exponent < 0) {
            result=1/result;
        }
        return result;
    }


    /**
     * 面试题21：调整数组顺序使得奇数位于偶数前面
     * @param array
     */
    public static void reOrderArray(int [] array) {
        int [] temp = new int [array.length];
        int count = 0;
        for (int i = 0; i < array.length; i++) {
                if ((array[i] % 2) != 0) {
                    temp[count] = array[i];
                    count++;
                }
        }
        for (int i = 0; i < array.length; i++) {
            if ((array[i] % 2) == 0) {
                temp[count] = array[i];
                count++;
            }
        }
        // 数组直接赋值的方法在本地IDE上通过了，
        // 但是在牛客网的判题系统上无法通过，需要逐个赋值，
        // 这个问题还需要再考虑解决。
        array = temp;
    }

    /**
     * 面试题22： 链表中倒数第k个节点
     * 快慢指针法
     * @param head
     * @param k
     * @return
     */
    public ListNode FindKthToTail(ListNode head,int k) {
        if (head == null || k == 0) {
            return  null;
        }
        ListNode ahead = head;
        ListNode behind;
        // 一个节点先向前走k-1步
        for (int i = 0; i <k - 1; i++) {
            if (ahead.next != null) {
                ahead = ahead.next;
            }else {
                return null;
            }
        }
        // 两个点一起走，直到先走的走到尾部。
        behind = head;
        while (ahead.next != null) {
            ahead = ahead.next;
            behind = behind.next;
        }
        return behind;
    }

    /**
     * 面试题24：反转链表
     * 需要再仔细研究
     * @param head
     * @return
     */
    public ListNode ReverseList(ListNode head) {
        // 反转的链表的头节点
        ListNode reversehead = null;
        // 当前节点
        ListNode node = head;
        // 前一个节点
        ListNode prev = null;
        // 后一个节点
        ListNode next = null;
        while (node != null) {
            // next 指向node的下一个节点
            next = node.next;
            // 如果next为空，就是到尾部了，把node赋给reversehead
            if (next == null) {
                reversehead = node;
            }
            // node指向前一个节点，关键操作
            node.next = prev;
            // prev向尾部走一个，指向现在的node
            prev = node;
            // node向尾部走一个，指向现在的next
            node = next;
        }
        return reversehead;


    }

    /**
     * 面试题25：合并两个排序的链表
     * @param list1
     * @param list2
     * @return
     */
    public ListNode Merge(ListNode list1,ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        ListNode mergeHead = null;
        if (list1.val < list2.val) {
            mergeHead = list1;
            mergeHead.next = Merge(list1.next, list2);
        } else {
            mergeHead = list2;
            mergeHead.next = Merge(list1, list2.next);
        }
        return mergeHead;

    }

    /**
     * 面试题26：树的子结构
     * 分两步：第一步找到根节点，第二步判断根节点之下的节点是否一致
     * @param root1
     * @param root2
     * @return
     */
    public boolean HasSubtree(TreeNode root1,TreeNode root2) {
        boolean result = false;
        if (root1 != null && root2 != null) {
            if (root1.val == root2.val) {
                result = tree1HasTree2(root1, root2);
            }
            if (!result) {
                result = HasSubtree(root1.right, root2);
            }
            if (!result) {
                result = HasSubtree(root1.left, root2);
            }
        }
        return result;

    }

    private boolean tree1HasTree2(TreeNode root1, TreeNode root2) {
        // 到了B树的叶节点，必须先判断这个，否则就是错的。
        if (root2 == null) {
            return true;
        }
        if (root1 == null) {
            return false;
        }
        if (root1.val != root2.val) {
            return false;
        }
        return tree1HasTree2(root1.left, root2.left) &&
                tree1HasTree2(root1.right, root2.right);
    }

    /**
     * 面试题27：二叉树的镜像
     * 先前序遍历这棵树的每个节点，如果遍历到的节点有子节点，就交换它的两个
     * 子节点。当交换完所有非叶节点的左右子节点之后，就得到了树的镜像。
     * @param root
     */
    public void Mirror(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            return;
        }
        // 交换两个子节点
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        if (root.left != null) {
            Mirror(root.left);
        }
        if (root.right != null) {
            Mirror(root.right);
        }
    }

    /**
     * 面试题29：顺时针打印矩阵
     * 复杂问题画图来找规律，拆分成简单问题
     * @param matrix
     * @return
     */
    public ArrayList<Integer> printMatrix(int [][] matrix) {
        if (matrix.length == 0) {
            return null;
        }
        int rows = matrix.length;
        int columns = matrix[0].length;
        int start = 0;
        ArrayList<Integer> result = new ArrayList<>(rows * columns);
        // 举例子找规律，结果就是这个判断条件
        while (columns > start * 2 && rows > start * 2) {
            PrintMatrixInCircle(matrix, columns, rows, start, result);
            start++;

        }
        return result;
    }

    private void PrintMatrixInCircle(int [] [] matrix, int columns, int rows, int start, ArrayList<Integer> result) {
        int endX = columns - 1 - start;
        int endY = rows - 1 - start;
        // 从左到右打印一行
        for (int i = start; i <= endX; i++) {
            result.add(matrix[start][i]);
        }
        // 从上到下打印一列
        if (start < endY) {
            for (int i = start + 1; i <= endY; i++) {
                result.add(matrix[i][endX]);
            }
        }
        // 从右到左打印一行
        if (start < endX && start < endY) {
            for (int i = endX -1; i >= start; i--) {
                result.add(matrix[endY][i]);
            }
        }
        // 从下到上打印一列
        if (start < endX && start < endY - 1) {
            for (int i = endY -1; i >= start+1; i--) {
                result.add(matrix[i][start]);
            }
        }
    }


}
