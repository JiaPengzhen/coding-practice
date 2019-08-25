import org.junit.Before;
import org.junit.Test;

public class TreeNodeTest {
    @Test
    public void SerializeTest () {
        // 创建一棵用于测试的二叉树
        TreeNode root = TreeNode.createBiTree("8,6,#,7,#,#,11,#,#");
        // 测试
        String s1 = Solution1.Serialize(root);
        System.out.println(s1);

    }



}
