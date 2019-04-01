package com.example.belief;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }

    public enum Type {
        MEAT, FISH, OTHER
    }
}

class Trader {
    private final String name;
    private final String city;

    public Trader(String n, String c) {
        this.name = n;
        this.city = c;
    }

    public String getName() {
        return this.name;
    }

    public String getCity() {
        return this.city;
    }

    public String toString() {
        return "Trader:" + this.name + " in " + this.city;
    }
}

class Transaction {
    private final Trader trader;
    private final int year;
    private final int value;

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return this.trader;
    }

    public int getYear() {
        return this.year;
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        return "{" + this.trader + ", " + "year: " + this.year + ", " + "value:" + this.value + "}";
    }
}

public class Ex {

    private List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT), new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER), new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER), new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH), new Dish("salmon", false, 450, Dish.Type.FISH));

    private Trader raoul = new Trader("Raoul", "Cambridge");
    private Trader mario = new Trader("Mario","Milan");
    private Trader alan = new Trader("Alan","Cambridge");
    private Trader brian = new Trader("Brian","Cambridge");

    private List<Transaction> transactions = Arrays.asList(new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000), new Transaction(raoul, 2011, 400), new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700), new Transaction(alan, 2012, 950));


    public void doTask() {
        List<String> thcd = menu.stream().filter(d -> d.getCalories() > 300).map(d -> d.getName()).limit(3)
                .collect(toList());
        System.out.print(thcd);
    }

    public void ex2() {
        List<String> strs = Arrays.asList("Hello", "world");
        List<String> strs1 = strs.stream() // 返回Stream<String>
                .map(w -> w.split("")) // 由于Function中的R是String[]，返回Stream<String[]>

                /*
                 * faltMap将元素映射为Stream的内容而不是一个Stream
                 * /因此使用faltMap(Arrays::stream)可以将Stream<String[]>转换成Stream<String>
                 * /使用map会生成Stream<Stream<String>>
                 */
                .flatMap(Arrays::stream) // 由于Function中的R是Stream<String[]>，返回Stream<String>
                .distinct().collect(toList());
        System.out.print(strs1);
    }

    public void ex3() {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> res = nums.stream().map(x -> x * x).collect(toList());
        print(res);
    }

    public void print(Object obj) {
        System.out.print(obj);
    }

    public void ex4() {
        List<Integer> l1 = Arrays.asList(1, 2, 3);
        List<Integer> l2 = Arrays.asList(4, 5);

        List<int[]> res = l1.stream() // Stream<Intger>
                .flatMap(x -> l2.stream().filter(y -> (x + y) % 3 == 0).map(y -> new int[] { x, y }) // Stream<Ingter[]>
                ).collect(toList()); // Stream<Intger[]>
        for (int[] arr : res) {
            print("(");
            for (int item : arr) {
                print(item + " ");
            }
            print(")");
        }
    }

    public void ex5() {
        //pro1
        List<Transaction> res1 = transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted((a, b) -> {
                    if (a.getValue() < b.getValue())
                        return -1;
                    else if (a.getValue() == b.getValue())
                        return 0;
                    else
                        return 1;
                })
                .collect(toList());
        // print(res1);
        //pro2
        List<Trader> traders = Arrays.asList(raoul, mario, alan, brian);
        List<String> res2 = traders.stream().map(Trader::getCity).distinct().collect(toList());
        print(res2);

    }

    public static void main(String[] args) {
        Ex ex1 = new Ex();
        // ex1.doTask();
        // ex1.ex2();
        // ex1.ex3();
        // ex1.ex4();
        ex1.ex5();
    }
}
