
// Builder pattern requires us to return an immutable object.

public class Car {
    // Required parameters
    private final int numberOfTyres;
    private final int numberOfSeats;
    

    // Optional parameters
    private final boolean sunRoofPresent;
    private final int mileageOnSteepRoads;

    public static class CarBuilder {
        // Required parameters
        private final int numberOfTyres;
        private final int numberOfSeats;

        // Optional 
        private boolean sunRoofPresent;
        private int mileageOnSteepRoads;

        public static CarBuilder getCarBuilder(int numberOfTyres, int numberOfSeats) {
            this.numberOfSeats = numberOfSeats;
            this.numberOfTyres = numberOfTyres;
        }

        public CarBuilder setSunRoofPresent(boolean sunRoofPresent) {
            this.sunRoofPresent = sunRoofPresent;
        }

        public CarBuilder setMileageOnSteepRoads(int mileageOnSteepRoads) {
            this.mileageOnSteepRoads = mileageOnSteepRoads;
        }

        public Car build() {
            return new Car(this);
        }
    }

    private Car(CarBuilder carBuilder) {
        this.numberOfTyres = carBuilder.numberOfTyres;
        this.numberOfSeats = carBuilder.numberOfSeats;
        if(carBuilder.sunRoofPresent != null)
            this.sunRoofPresent = carBuilder.sunRoofPresent;
        if(carBuilder.mileageOnSteepRoads != null)
            this.mileageOnSteepRoads = carBuilder.mileageOnSteepRoads;
    }
}

// Creating a Car object
CarBuilder carBuilder = Car.CarBuilder.getCarBuilder(1, 2);
carBuilder.setSunRoofPresent(2)
        .setMileageOnSteepRoads(299);

Car car = carBuilder.build();