package me.zoro.peachgardenmall;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void replaceString() {
        String oldSpec = "已选：一般包装200.00 x1";
        //String spec = oldSpec.replaceFirst("x\\d$", "x"+String.valueOf(5));
        String spec = oldSpec.replaceFirst("已选：", "");

        System.out.println("ExampleUnitTest.replaceString spec ==> " + spec);

    }

    @Test
    public void testCompareString() {
        String k1 = 1 + "_" + 2;
        String k2 = new String(1 + "_" + 2);
        assertEquals(k1, k2);
        System.out.println(k1 == k2);
        System.out.println(k1.equals(k2));
    }
}