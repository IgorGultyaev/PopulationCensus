package com.company;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static Collection<Person> filtersPersons(Collection<Person> person,
                                                    int ageFrom, int ageTo,
                                                    Sex... sex) {
        Stream<Person> stream = person.stream()
                .filter(personUnit -> personUnit.getAge() > ageFrom && personUnit.getAge() < ageTo)
                .filter(personUnit -> sex.length == 2 ? personUnit.getSex() == sex[1]
                        || personUnit.getSex() == sex[0] : personUnit.getSex() == sex[0]);

        return stream.collect(Collectors.toList());
    }

    public static Collection<Person> filtersWorkers(Collection<Person> person,
                                                    Education education) {
        int ageFrom = 17;
        Stream<Person> stream = person.stream()
                .filter(personUnit -> {
                    Function<Sex, Integer> ageT = x -> personUnit.getSex() == Sex.MAN ? 65 : 20;
                    return personUnit.getAge() > ageFrom && personUnit.getAge() < ageT.apply(personUnit.getSex());
                })
                .filter(personUnit -> personUnit.getEducation() == education);

        return stream.collect(Collectors.toList());
    }


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

        int ageFrom = 0;
        int ageTo = 0;

        //количество несовершеннолетних
        ageFrom = -1;
        ageTo = 18;
        long minors = filtersPersons(persons, ageFrom, ageTo, Sex.WOMAN, Sex.MAN).stream().count();
        System.out.println(minors);

        //количество призывников
        ageFrom = 17;
        ageTo = 27;
        Collection<Person> conscripts = filtersPersons(persons, ageFrom, ageTo, Sex.MAN);
//        filtersPersons(persons, ageFrom, ageTo, Sex.MAN).forEach(System.out::println);

        //список потенциально работоспособных людей с высшим образованием
        Collection<Person> workers = filtersWorkers(persons, Education.HIGHER);
//        filtersWorkers(persons, Education.HIGHER).forEach(System.out::println);

    }
}
