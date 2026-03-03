import java.util.*;
import java.util.stream.Collectors;

public class CarDealership {
    private Set<Car> cars = new HashSet<>();

    public boolean addCar(Car car) {
        return cars.add(car);
    }

    public List<Car> findCarsByManufacturer(String manufacturer) {
        return cars.stream()
                .filter(c -> c.getManufacturer().equalsIgnoreCase(manufacturer))
                .collect(Collectors.toList());
    }

    public double averagePriceByType(CarType type) {
        return cars.stream()
                .filter(c -> c.getType() == type)
                .mapToDouble(Car::getPrice)
                .average()
                .orElse(0.0);
    }

    public List<Car> getCarsSortedByYearDesc() {
        return cars.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public Map<CarType, Long> countByType() {
        return cars.stream()
                .collect(Collectors.groupingBy(Car::getType, Collectors.counting()));
    }

    public Optional<Car> oldestCar() {
        return cars.stream().min(Comparator.comparingInt(Car::getYear));
    }

    public Optional<Car> newestCar() {
        return cars.stream().max(Comparator.comparingInt(Car::getYear));
    }

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Меню автоцентра ---");
            System.out.println("1. Добавить машину");
            System.out.println("2. Найти машины по производителю");
            System.out.println("3. Средняя цена по типу");
            System.out.println("4. Список машин (отсортирован по году)");
            System.out.println("5. Статистика: количество по типам");
            System.out.println("6. Самая старая и новая машина");
            System.out.println("7. Показать все машины");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("VIN: ");
                    String vin = scanner.nextLine();
                    System.out.print("Модель: ");
                    String model = scanner.nextLine();
                    System.out.print("Производитель: ");
                    String man = scanner.nextLine();
                    System.out.print("Год выпуска: ");
                    int year = scanner.nextInt();
                    System.out.print("Пробег: ");
                    int mileage = scanner.nextInt();
                    System.out.print("Цена: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.println("Выберите тип автомобиля (введите номер):");
                    CarType[] types = CarType.values();
                    for (int i = 0; i < types.length; i++) {
                        System.out.println((i + 1) + ". " + types[i]);
                    }
                    System.out.print("Ваш выбор: ");
                    int typeChoice = scanner.nextInt();
                    scanner.nextLine();

                    CarType selectedType;
                    if (typeChoice >= 1 && typeChoice <= types.length) {
                        selectedType = types[typeChoice - 1];
                    } else {
                        System.out.println("Неверный номер, будет установлен SEDAN");
                        selectedType = CarType.SEDAN;
                    }

                    Car newCar = new Car(vin, model, man, year, mileage, price, selectedType);
                    if (addCar(newCar)) {
                        System.out.println("Машина добавлена.");
                    } else {
                        System.out.println("Машина с таким VIN уже существует!");
                    }
                    break;

                case 2:
                    System.out.print("Производитель: ");
                    String manufacturer = scanner.nextLine();
                    List<Car> found = findCarsByManufacturer(manufacturer);
                    if (found.isEmpty()) {
                        System.out.println("Машины не найдены.");
                    } else {
                        found.forEach(System.out::println);
                    }
                    break;

                case 3:
                    System.out.println("Выберите тип (введите номер):");
                    CarType[] typesForAvg = CarType.values();
                    for (int i = 0; i < typesForAvg.length; i++) {
                        System.out.println((i + 1) + ". " + typesForAvg[i]);
                    }
                    System.out.print("Ваш выбор: ");
                    int typeAvgChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (typeAvgChoice >= 1 && typeAvgChoice <= typesForAvg.length) {
                        CarType searchType = typesForAvg[typeAvgChoice - 1];
                        double avg = averagePriceByType(searchType);
                        System.out.printf("Средняя цена для типа %s: %.2f%n", searchType, avg);
                    } else {
                        System.out.println("Неверный номер.");
                    }
                    break;

                case 4:
                    System.out.println("Машины, отсортированные по году (новые → старые):");
                    getCarsSortedByYearDesc().forEach(System.out::println);
                    break;

                case 5:
                    Map<CarType, Long> stats = countByType();
                    if (stats.isEmpty()) {
                        System.out.println("Нет машин.");
                    } else {
                        stats.forEach((t, cnt) -> System.out.println(t + ": " + cnt));
                    }
                    break;

                case 6:
                    Optional<Car> oldest = oldestCar();
                    Optional<Car> newest = newestCar();
                    oldest.ifPresentOrElse(
                            c -> System.out.println("Самая старая: " + c),
                            () -> System.out.println("Нет машин.")
                    );
                    newest.ifPresentOrElse(
                            c -> System.out.println("Самая новая: " + c),
                            () -> System.out.println("Нет машин.")
                    );
                    break;

                case 7:
                    if (cars.isEmpty()) {
                        System.out.println("Автоцентр пуст.");
                    } else {
                        cars.forEach(System.out::println);
                    }
                    break;

                case 0:
                    System.out.println("Выход из программы.");
                    return;

                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }
}
