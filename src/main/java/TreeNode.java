import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author JiaYi
 */
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) {
        val = x;
    }


    /**
     * 将前序遍历序列化字符串装入一个queue中
     */
    public static TreeNode createBiTree(String str) {
        Queue<String> queue = new LinkedList<>();
        Collections.addAll(queue, str.split(","));
        return Deserialize(queue);
    }

    /**
     * 前序遍历反序列化
     * 从队列中取出一个判断是否是"#"，如果是表示该结点为null，否则新建结点
     */
    private static TreeNode Deserialize(Queue<String> queue) {
        String string = queue.poll();
        if (string.equals("#")) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(string));
        root.left = Deserialize(queue);
        root.right = Deserialize(queue);
        return root;
    }


    }

