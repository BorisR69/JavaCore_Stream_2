import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        // Выборка несовершеннолетних
        Stream <Person> personYoung = persons.stream();
        System.out.println("Количество несовершеннолетни: " + personYoung.filter(a -> a.getAge() < 18).count());
        // Выборка фамилий призывников
        Stream <Person> personArmy = persons.stream();
        List <String> familyArmy = personArmy.filter(a -> a.getAge() >= 18).filter(a -> a.getAge() < 27)
                .map(Person::getFamily).collect(Collectors.toList());
        System.out.println("Фамилии призывников: " + familyArmy);
        // Выборка потенциально работоспособных людей с высшим образованием
        Stream <Person> personWork = persons.stream();
        List <Person> worker = personWork.filter(w -> w.getAge() >= 18 && w.getAge() <= 60 && w.getSex()
                .equals(Sex.WOMAN) ||
                w.getSex().equals(Sex.MAN) && w.getAge() >= 18 && w.getAge() <= 65)
                .filter(e -> e.getEducation().equals(Education.HIGHER))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        for (Person f : worker) {
            System.out.println(f);
        }
    }
}
