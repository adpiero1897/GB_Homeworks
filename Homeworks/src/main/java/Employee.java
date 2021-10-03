public class Employee {
    // 1. Создать класс "Сотрудник" с полями: ФИО, должность, email, телефон, зарплата, возраст.
    private String fullName;
    private String position;
    private String email;
    private String phone_Number;
    private String salary;
    private int age;

    // 2. Конструктор класса должен заполнять эти поля при создании объекта.
    public Employee(String fullName, String position, String email, String phone_Number, String salary, int age) {
        this.fullName = fullName;
        this.position = position;
        this.email = email;
        this.phone_Number = phone_Number;
        this.salary = salary;
        this.age = age;
    }

    // 3. Внутри класса «Сотрудник» написать метод, который выводит информацию об объекте в консоль.
    public void print_Employee_info(){
        System.out.println("ФИО: " + fullName);
        System.out.println("Должность: " + position);
        System.out.println("Email: " + email);
        System.out.println("телефон: " + phone_Number);
        System.out.println("Зарплата: " + salary);
        System.out.println("Возраст: " + age);
        System.out.println();
    }

    // сделаем геттер для получения инфо о возрасте сотрудника извне (нужно для задания 5 вывести возраст 40+)
    public int getAge() {
        return age;
    }
}