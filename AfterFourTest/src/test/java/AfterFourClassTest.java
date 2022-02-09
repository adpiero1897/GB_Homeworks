import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

public class AfterFourClassTest {

    private AfterFourClass afc;

    @Before
    public void init() {
        afc = new AfterFourClass();
    }

    @Test
    public void afterFourMethodTest1() {
        //Проверяем тест1, когда 4 много разных входит и могут подряд
        int[] test1Arr = {1, 2, -1, 0, 4, 4, 512, 4, 4, 0, 1, 2, 3};
        try {
            int[] realOut = afc.afterFourMethod(test1Arr);
            //сравниваем массив на выходе теста с ожидаемым
            Assertions.assertTrue(Arrays.equals(realOut, new int[]{0, 1, 2, 3}));
        } catch (Exception exception) {
            Assertions.fail();  //Если выкинул исключение, то этот тест провален
        }
    }


    @Test
    public void afterFourMethodTest2() {
        //Проверяем тест2, когда после 4 нет больше чисел
        int[] test2Arr = {1, 2, -1, 0, 4};
        try {
            int[] realOut = afc.afterFourMethod(test2Arr);
            //сравниваем массив на выходе теста с ожидаемым
            Assertions.assertTrue(Arrays.equals(realOut, new int[]{}));
        } catch (Exception exception) {
            Assertions.fail();  //Если выкинул исключение, то этот тест провален
        }
    }

    @Test
    public void afterFourMethodTest3() {
        //Проверяем тест3, когда 4 нет во входном массиве
        int[] test3Arr = {1, 0, -1424, 41};
        try {
            afc.afterFourMethod(test3Arr);
            //Если не выдаст ошибку, то тест провален, т.к. 4 нет во входном массиве
            Assertions.fail();
        } catch (RuntimeException ignored) {
        }
    }

    @Test
    public void afterFourMethodTest4() {
        //Проверяем тест4, когда подаётся пустой массив
        int[] test4Arr = {};
        try {
            afc.afterFourMethod(test4Arr);
            //Если не выдаст ошибку, то тест провален, т.к. 4 нет во входном массиве
            Assertions.fail();
        } catch (RuntimeException ignored) {
        }
    }

    @Test
    public void check14Test1() {
        int[] test5Arr = {1, 1, 1, 4, 4, 1, 4, 4};
        try {
            Assertions.assertTrue(afc.check14(test5Arr));   //Должен выдать true, иначе тест провален будет
        } catch (Exception exception) {
            Assertions.fail();  //Если выкинул исключение, то этот тест провален
        }
    }

    @Test
    public void check14Test2() {
        int[] test6Arr = {1, 1, 1, 1, 1, 1, 1};
        try {
            Assertions.assertFalse(afc.check14(test6Arr));   //Должен выдать false, иначе тест провален будет
        } catch (Exception exception) {
            Assertions.fail();  //Если выкинул исключение, то этот тест провален
        }
    }

    @Test
    public void check14Test3() {
        int[] test7Arr = {4, 4, 4, 4};
        try {
            Assertions.assertFalse(afc.check14(test7Arr));   //Должен выдать false, иначе тест провален будет
        } catch (Exception exception) {
            Assertions.fail();  //Если выкинул исключение, то этот тест провален
        }
    }

    @Test
    public void check14Test4() {
        int[] test8Arr = {1, 4, 4, 1, 1, 4, 3};
        try {
            Assertions.assertFalse(afc.check14(test8Arr));   //Должен выдать false, иначе тест провален будет
        } catch (Exception exception) {
            Assertions.fail();  //Если выкинул исключение, то этот тест провален
        }
    }

}
