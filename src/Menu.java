public class Menu {
    public static void main(String[] args) {
        CarDealership dealership = new CarDealership();

        dealership.addCar(new Car("V001", "Model 3", "Tesla", 2023, 10000, 55000, CarType.ELECTRIC));
        dealership.addCar(new Car("V002", "X5", "BMW", 2022, 20000, 70000, CarType.SUV));
        dealership.addCar(new Car("V003", "Camry", "Toyota", 2021, 30000, 25000, CarType.SEDAN));
        dealership.addCar(new Car("V004", "Model S", "Tesla", 2024, 5000, 90000, CarType.ELECTRIC));
        dealership.addCar(new Car("V005", "RAV4", "Toyota", 2020, 45000, 28000, CarType.SUV));
        dealership.addCar(new Car("V006", "Mustang", "Ford", 2019, 60000, 35000, CarType.COUPE));
        dealership.addCar(new Car("V007", "Focus", "Ford", 2022, 15000, 22000, CarType.HATCHBACK));

        System.out.println("=== Добро пожаловать в автоцентр ===");
        dealership.runMenu();
    }
}
