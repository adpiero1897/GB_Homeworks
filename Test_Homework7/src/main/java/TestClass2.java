public class TestClass2 {


    public TestClass2(final String testName) {
    }

    @BeforeSuite
    public void testBefore(){
        System.out.println("Подготовка к тесту...");
    }

    @AfterSuite
    public void testAfter(){
        System.out.println("Закончили тест");
    }

    @BeforeSuite
    public void testBefore2(){
        System.out.println("Излишняя Подготовка к тесту...");
    }

    @Test(value = 1)
    public void test1(){
        System.out.println("Был запущен тестовый МЕТОД 1 ");
    }
}
