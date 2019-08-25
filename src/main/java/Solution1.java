import java.util.*;

/**
 * 面试题30题往后
 * @author JiaYi
 */
public class Solution1 {
    /**
     * 面试题30：包含min函数的栈
     * @param node
     */
    private Stack<Integer> stack = new Stack<>();
    private Stack<Integer> stackMin = new Stack<>();
    public void push(int node) {
        stack.push(node);
        if (stackMin.isEmpty() || node < (int) stackMin.peek()) {
            stackMin.push(node);
        }else {
            stackMin.push(stackMin.peek());
        }
    }
    public void pop() {
        stack.pop();
        stackMin.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int min() {
        return stackMin.peek();
    }

    /**
     * 面试题31： 栈的压入，弹出顺序
     * @param pushA
     * @param popA
     * @return
     */
    public boolean isPopOrder(int [] pushA, int [] popA) {
        if (pushA.length == 0 || popA.length == 0) {
            return false;
        }
        Stack<Integer> stack = new Stack<>();
        int nextPush = 0;
        int nextPop = 0;
        while (nextPush < pushA.length && nextPop < popA.length) {
            stack.push(pushA[nextPush]);
            while (!stack.empty() && popA[nextPop] == stack.peek()) {
                stack.pop();
                nextPop++;
            }
            nextPush++;
        }
        return stack.empty();
    }

    /**
     * 面试题32：从上到下打印二叉树
     * 就是树的层序遍历，需要用到队列。
     * 太耗时间了吗？
     * @param root
     * @return
     */
    public ArrayList<Integer> printFromTopToBottom(TreeNode root) {
        ArrayList<TreeNode> queue = new ArrayList<>();
        ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return null;
        }
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode temp = queue.remove(0);
            result.add(temp.val);
            if (temp.left != null) {
                queue.add(temp.left);
            }
            if (temp.right != null) {
                queue.add(temp.right);
            }
        }
        return result;
    }

    /**
     * 面试题33：二叉搜索树的后序遍历序列
     * 后序遍历是左右中，树状结构适合用递归来做
     * @param sequence
     * @return
     */
    public static boolean verifySquenceOfBST(int [] sequence) {
        if (sequence.length == 0) {
            return false;
        }
        if (sequence.length == 1) {
            return true;
        }
        return verifyBST(sequence, 0, sequence.length-1);
    }

    private static boolean verifyBST(int[] sequence, int start, int end) {
        if(start >= end){
            return true;
        }
        int i = start;
        while (sequence[i] < sequence[end]) {
            i++;
        }
        for (int j = i; j < end; j++) {
            if(sequence[j] < sequence[end]) {
                return false;
            }
        }
        return verifyBST(sequence, start, i-1) &&
                verifyBST(sequence, i, end-1);

    }

    /**
     * 面试题34：二叉树中和为某一值的路径
     * 树的前序遍历中做一些事情
     * @param root
     * @param target
     * @return
     */
    private ArrayList<ArrayList<Integer>> results = new ArrayList<>();
    private ArrayList <Integer> result = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        if (root == null) {
            return results;
        }
        result.add(root.val);
        target -= root.val;
        if (target == 0 && root.right == null && root.left == null) {
            results.add(new ArrayList<>(result));
        }
        FindPath(root.left, target);
        FindPath(root.right, target);
        result.remove(result.size()-1);
        return results;
    }

    /**
     * 面试题35：复杂链表的复制
     * 思路1：创建新的链表节点的时候用哈希表建立旧节点到新节点的映射
     * 思路2：三步，第一步复制节点到旧节点的后面，第二步设置随机指针，第三步断开新旧链表。
     * @param pHead
     * @return
     */
    public RandomListNode Clone(RandomListNode pHead) {
        CloneNodes(pHead);
        ConnectRandom(pHead);
        return ReConnectNodes(pHead);

    }

    private RandomListNode ReConnectNodes(RandomListNode pHead) {
        RandomListNode node = pHead;
        RandomListNode clonedHead = null;
        RandomListNode clonedNode = null;

        if (node != null) {
            clonedHead = clonedNode = node.next;
            node.next = clonedNode.next;
            node = node.next;
        }
        while (node != null) {
            clonedNode.next = node.next;
            clonedNode = clonedNode.next;
            node.next = clonedNode.next;
            node = node.next;
        }

        return clonedHead;
    }

    private void ConnectRandom(RandomListNode pHead) {
        RandomListNode node = pHead;
        while (node != null) {
            RandomListNode cloned = node.next;
            if (node.random != null) {
                cloned.random = node.random.next;
            }
            node = node.next;
        }

    }

    private void CloneNodes(RandomListNode pHead) {
        RandomListNode node = pHead;
        while (node != null) {
            RandomListNode clone = new RandomListNode(node.label);
            clone.next = node.next;
            node.next = clone;
            node = clone.next;
        }
    }

    /**
     * 面试题36：二叉搜索树与双向链表
     * 将二叉搜索树变成双向链表
     * @param pRootOfTree
     * @return
     */
    private TreeNode pLast = null;
    public TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null) {
            return null;
        }
        // 如果左子树为空，那么根节点root为双向链表的头节点
        TreeNode head = Convert(pRootOfTree.left);
        if (head == null) {
            head = pRootOfTree;
        }
        // 连接当前节点root和当前链表的尾节点pLast
        pRootOfTree.left = pLast;
        if (pLast != null) {
            pLast.right = pRootOfTree;
        }
        pLast = pRootOfTree;
        Convert(pRootOfTree.right);
        return head;

    }

    /**
     * 面试题37：序列化二叉树
     * 前序遍历得到扩展二叉树。
     * @param root
     * @return
     */

    public static String Serialize(TreeNode root) {
        StringBuffer se = new StringBuffer();
        serialize(root, se);
        se.deleteCharAt(se.length()-1);
        return se.toString();

    }

    private static void serialize(TreeNode root, StringBuffer se) {
        if(root == null){
            se.append("#,");
        } else {
            //问题出在append的加法上! 加','和加","是不一样的！
            se.append(root.val + ",");
            serialize(root.left, se);
            serialize(root.right, se);
        }
    }

    /**
     * 面试题38：字符串的排列
     * 求整个字符串的排列可以看成两步：
     * 第一步求所有可能出现在第一个位置的字符，第二步固定第一个字符，求后面所有字符的排列。
     * 这是典型的递归思路。
     * @param str
     * @return
     */
    public ArrayList<String> Permutation(String str){

        ArrayList<String> list = new ArrayList<>();
        // && 两边的条件式不一样的，注意区别
        if(str!=null && str.length()>0){
            PermutationHelper(str.toCharArray(),0,list);
            Collections.sort(list);
        }
        return list;
    }
    private void PermutationHelper(char[] chars,int i,ArrayList<String> list){
        if(i == chars.length-1){
            list.add(String.valueOf(chars));
        }else{
            Set<Character> charSet = new HashSet<>();
            for(int j=i;j<chars.length;++j){
                if(j==i || !charSet.contains(chars[j])){
                    charSet.add(chars[j]);
                    swap(chars,i,j);
                    PermutationHelper(chars,i+1,list);
                    swap(chars,j,i);
                }
            }
        }
    }

    private void swap(char[] cs,int i,int j){
        char temp = cs[i];
        cs[i] = cs[j];
        cs[j] = temp;
    }

}
