import java.util.ArrayList;
import java.util.HashMap;

public class PhoneBook {

    private final HashMap<String, ArrayList<String>> phoneBookMap;

    public PhoneBook() {
        phoneBookMap = new HashMap<>();
    }


    public String get(String name) {
        return phoneBookMap.get(name).toString();
    }

    public void add(String name, String number) {
        ArrayList<String> numbersList;
        if(phoneBookMap.containsKey(name)){   //Если такая фамилия уже есть в телефонной книге
            numbersList = phoneBookMap.get(name);   //получаем старый список номеров по даннйо фамилии
        } else { //Если такой фамилии нет в телефонной книге, то добавляем её
            numbersList = new ArrayList<>();
        }
        numbersList.add(number);    //Добавляем новый номер в стейдж номеров данной фамилии из справочника
        phoneBookMap.put(name,numbersList); //Заменяем старый список дополненным списком номеров
    }
}
