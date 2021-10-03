public class MainClass {
//Начало работы - создали пустой класс с основным методом Main.

    public static void main(String[] args) {
        // 4. Создать массив из 5 сотрудников.
        Employee[] employees_arr = new Employee[5];

        //для каждой ячейки массива задаем объект
        employees_arr[0] = new Employee("Cristiano Ronaldo", "forward", "CR7@gmail.pt",
                "+3517777777", "2650000$", 36);
        employees_arr[1] = new Employee("Fedor Smolov", "penalty taker", "kosyak_man@vk.ru",
                "+79451234567", "10$", 31);
        employees_arr[2] = new Employee("Valeriy Karpin", "coach", "KarvaLOL69@mail.ru",
                "+79054040219", "105000$", 52);
        employees_arr[3] = new Employee("Vasiliy Utkin", "commentator", "vasyoOK72@mail.ru",
                "+79384658116", "3001$", 49);
        employees_arr[4] = new Employee("Franchesko Totti", "gladiator", "RomaForever10@gmail.it",
                "+396090909", "10101010$", 45);

        // 5. С помощью цикла вывести информацию только о сотрудниках старше 40 лет.
        for (int i = 0; i < employees_arr.length; i++) {
            if(employees_arr[i].getAge() > 40) {
                employees_arr[i].print_Employee_info();
            }
        }
    }
}