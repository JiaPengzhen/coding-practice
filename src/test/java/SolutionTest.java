import org.junit.Assert;
import org.junit.Test;
public class SolutionTest {
    @Test
    public void FibonacciTest() throws Exception {
        int n = 5;
        int s;
        s = Solution.fibonacci(10);
        Assert.assertEquals(55, s);
    }


}
