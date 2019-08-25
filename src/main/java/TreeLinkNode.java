/**
 * @author JiaYi
 */
public class TreeLinkNode {
    int val;
    TreeLinkNode left = null;
    TreeLinkNode right = null;
    /**
     * 指向父节点
     */
    TreeLinkNode next = null;

    TreeLinkNode(int val) {
        this.val = val;
    }
}