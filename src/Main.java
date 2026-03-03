import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        System.out.println("=== Задание №1: Массивы (годы выпуска) ===");
        task1();

        System.out.println("\n=== Задание №2: Коллекции (модели) ===");
        task2();

        System.out.println("\n=== Задание №3: equals/hashCode и Comparable ===");
        task3();

        System.out.println("\n=== Задание №4: Stream API ===");
        task4();
    }

    private static void task1() {
        int[] years = new int[50];
        for (int i = 0; i < years.length; i++) {
            years[i] = 2000 + RANDOM.nextInt(26);
        }
        System.out.println("Исходный массив годов выпуска:");
        for (int y : years) {
            System.out.print(y + " ");
        }
        System.out.println();
        System.out.println("Машины после 2015 года:");
        for (int y : years) {
            if (y > 2015) {
                System.out.print(y + " ");
            }
        }
        System.out.println();
        int currentYear = 2026;
        double sum = 0;
        for (int y : years) {
            sum += currentYear - y;
        }
        System.out.printf("Средний возраст: %.2f %n", sum / years.length);
    }

    private static void task2() {
        List<String> models = new ArrayList<>(Arrays.asList(
                "Toyota Camry", "BMW X5", "Tesla Model S", "Audi A4", "Tesla Model 3",
                "BMW X5", "Mercedes C class", "Toyota Camry", "Tesla Model S", "Ford Focus",
                "Tesla Model 3", "Audi A4"
        ));
        System.out.println("Исходный список:");
        System.out.println(models);
        Set<String> uniqueSet = new LinkedHashSet<>(models);
        List<String> uniqueList = new ArrayList<>(uniqueSet);
        System.out.println("\nПосле удаления дубликатов:");
        System.out.println(uniqueList);
        uniqueList.sort(Comparator.reverseOrder());
        System.out.println("\nОтсортировано в обратном алфавитном порядке:");
        uniqueList.forEach(System.out::println);
        Set<String> resultSet = new LinkedHashSet<>(uniqueList);
        System.out.println("\nСохранено в Set (размер = " + resultSet.size() + "):");
        System.out.println(resultSet);
        List<String> modified = new ArrayList<>();
        for (String model : models) {
            if (model.contains("Tesla")) {
                modified.add("ELECTRO_CAR");
            } else {
                modified.add(model);
            }
        }
        System.out.println("\nИсходный список после замены Tesla на ELECTRO_CAR:");
        System.out.println(modified);
    }

    private static void task3() {
        Car car1 = new Car("VIN001", "Model S", "Tesla", 2020, 15000, 80000);
        Car car2 = new Car("VIN002", "X5", "BMW", 2022, 5000, 70000);
        Car car3 = new Car("VIN001", "Model 3", "Tesla", 2021, 20000, 50000);
        Car car4 = new Car("VIN003", "A4", "Audi", 2019, 30000, 40000);
        Car car5 = new Car("VIN004", "Camry", "Toyota", 2025, 0, 35000);
        Car car6 = new Car("VIN005", "Focus", "Ford", 2022, 10000, 25000);
        Car car7 = new Car("VIN006", "Focus", "Ford", 2022, 10000, 25000);

        System.out.println("Демонстрация equals");
        System.out.println("car1 (VIN001) == car3 (VIN001): " + car1.equals(car3));
        System.out.println("car1 (VIN001) == car2 (VIN002): " + car1.equals(car2));
        System.out.println("car6 (VIN005) == car7 (VIN006): " + car6.equals(car7));
        System.out.println();

        System.out.println("Проверка HashSet");
        Set<Car> carSet = new HashSet<>();
        System.out.println("Добавление car1: " + carSet.add(car1));
        System.out.println("Добавление car2: " + carSet.add(car2));
        System.out.println("Добавление car3 (дубликат VIN001): " + carSet.add(car3));
        System.out.println("Добавление car4: " + carSet.add(car4));
        System.out.println("Добавление car5: " + carSet.add(car5));
        System.out.println("Добавление car6: " + carSet.add(car6));
        System.out.println("Добавление car7: " + carSet.add(car7));
        System.out.println("\nИтоговый размер HashSet: " + carSet.size());
        System.out.println("Содержимое HashSet:");
        carSet.forEach(System.out::println);
        System.out.println();

        System.out.println("Сортировка по году");
        List<Car> carList = new ArrayList<>(carSet);
        System.out.println("До сортировки:");
        carList.forEach(c -> System.out.println(c.getYear() + " " + c.getModel() + " (VIN: " + c.getVin() + ")"));
        Collections.sort(carList);
        System.out.println("\nПосле сортировки:");
        carList.forEach(c -> System.out.println(c.getYear() + " " + c.getModel() + " (VIN: " + c.getVin() + ")"));
    }

    private static List<Car> generateCars() {
        List<Car> cars = new ArrayList<>();
        String[] manufacturers = {"Toyota", "BMW", "Tesla", "Audi", "Mercedes", "Ford"};
        String[] models = {"Camry", "X5", "Model S", "A4", "C class", "Focus"};
        for (int i = 0; i < 10; i++) {
            String vin = "VIN" + (1000 + i);
            String man = manufacturers[RANDOM.nextInt(manufacturers.length)];
            String mod = models[RANDOM.nextInt(models.length)];
            int year = 2015 + RANDOM.nextInt(11);
            int mileage = RANDOM.nextInt(100000);
            double price = 20000 + RANDOM.nextDouble() * 60000;
            cars.add(new Car(vin, mod, man, year, mileage, price));
        }
        return cars;
    }

    private static void task4() {
        List<Car> cars = generateCars();
        System.out.println("Исходный список автомобилей:");
        cars.forEach(System.out::println);
        System.out.println();

        List<Car> lowMileageCars = cars.stream()
                .filter(c -> c.getMileage() < 50000)
                .collect(Collectors.toList());
        System.out.println("Машины с пробегом менее 50 000 км:");
        if (lowMileageCars.isEmpty()) {
            System.out.println("  (нет таких машин)");
        } else {
            lowMileageCars.forEach(c -> System.out.println("  " + c));
        }
        System.out.println();

        List<Car> sortedByPriceDesc = cars.stream()
                .sorted(Comparator.comparingDouble(Car::getPrice).reversed())
                .collect(Collectors.toList());
        System.out.println("Топ-3 самых дорогих машин:");
        sortedByPriceDesc.stream()
                .limit(3)
                .forEach(c -> System.out.printf("  %s (производитель: %s, год: %d, цена: %.2f)%n",
                        c.getModel(), c.getManufacturer(), c.getYear(), c.getPrice()));
        System.out.println();

        double avgMileage = cars.stream()
                .mapToInt(Car::getMileage)
                .average()
                .orElse(0.0);
        System.out.printf("Средний пробег всех машин: %.2f км%n", avgMileage);
        System.out.println();

        Map<String, List<Car>> byManufacturer = cars.stream()
                .collect(Collectors.groupingBy(Car::getManufacturer));
        System.out.println("Группировка по производителю:");
        byManufacturer.forEach((manufacturer, carList) -> {
            System.out.println("  " + manufacturer + ":");
            carList.forEach(c -> System.out.println("    " + c));
        });
    }
}
