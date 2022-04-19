package com.company;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static Collection<Person> filtersPersons(Collection<Person> person,
                                                    int ageFrom, int ageTo, Education education, boolean educationFiler,
                                                    Sex... sex) {

        Stream<Person> stream = person.stream()
                .filter(personUnit -> personUnit.getAge() > ageFrom && personUnit.getAge() < ageTo)
                .filter(personUnit -> sex.length == 2 ? personUnit.getSex() == sex[1]
                        || personUnit.getSex() == sex[0] : personUnit.getSex() == sex[0])
                .filter(personUnit -> educationFiler ? personUnit.getEducation() == education
                        : personUnit.getEducation() == Education.ELEMENTARY);

        return stream.collect(Collectors.toList());
    }

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000; i++) {
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


//        //количество несовершеннолетних
//        ageFrom = -1;
//        ageTo = 18;
//        System.out.println(filtersPersons(persons, ageFrom, ageTo,
//                Education.ELEMENTARY,true, Sex.WOMAN, Sex.MAN).stream().count());
//
//        //количество призывников
//        ageFrom = 17;
//        ageTo = 27;
//        System.out.println(filtersPersons(persons, ageFrom, ageTo,
//                Education.ELEMENTARY,true, Sex.MAN).stream().count());


        //список потенциально работоспособных людей с высшим образованием
        Sex gender = Sex.WOMAN;
        Function<Sex, Integer> ageF = x -> gender == Sex.MAN ? 2 : 4;
//        System.out.println(ageF.apply(gender));
        Function<Sex, Integer> ageT = x -> gender == Sex.MAN ? 4 : 6;
//        System.out.println(ageT.apply(gender));

        ageFrom = ageF.apply(gender);
        ageTo = ageT.apply(gender);
//        System.out.println(filtersPersons(persons, ageFrom, ageTo,
//                Education.ELEMENTARY, true, Sex.MAN).stream().count());

        System.out.println(filtersPersons(persons, ageFrom, ageTo,
                Education.ELEMENTARY, true, Sex.MAN));

        // TODO Сделать вызов метода, для подсчета через лямбду закинуть условие


        //int ageFrom = Sex  -> {}


//        Function<Sex,Integer> aa = x -> gender==Sex.MAN ? 2: 3;

//        System.out.println(aa.apply(gender));

//Function<Integer, String> convert = x -> x + " долларов";
//System.out.println(convert.apply(5)); // 5 долларов
    }
}
