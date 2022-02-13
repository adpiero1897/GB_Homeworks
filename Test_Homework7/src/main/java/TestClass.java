public class TestClass {

    private String testName;

    public TestClass(final String testName) {
        this.testName = testName;
    }

    @BeforeSuite
    public void testBefore(){
        System.out.println("Подготовка к тесту...");
    }

    @AfterSuite
    public void testAfter(){
        System.out.println("Закончили тест");
    }

    @Test(value = 1)
    protected void test1(){
        System.out.println("Был запущен тестовый PROTECTED МЕТОД 1 ");
    }

    //Пусть value @Test методов идут не по порядку для проверки нашего кода "тестировки"
    @Test(value = 5)
    public void test5(){
        System.out.println("Был запущен тестовый МЕТОД 5");
    }

    public void unTest(){
        System.out.println("Этот метод не должен тестироваться!");
    }


    @Test(value = 9)
    private void test9(){
        System.out.println("Был запущен тестовый ПРИВАТНЫЙ МЕТОД 9 ");
    }

    @Test(value = 2)
    public void test2(){
        System.out.println("Был запущен тестовый МЕТОД 2 ");
    }
    @Test(value = 3)
    public void extraTest3(){
        System.out.println("Был запущен тестовый МЕТОД 3/2");
    }
    @Test(value = 3)
    public void extraPlusTest3(){
        System.out.println("Был запущен тестовый МЕТОД 3/3");
    }

    @Test(value = 3)
    public void test3(){
        System.out.println("Был запущен тестовый МЕТОД 3/1");
    }


}
