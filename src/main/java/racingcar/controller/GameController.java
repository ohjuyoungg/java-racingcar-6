package racingcar.controller;

import java.util.ArrayList;
import java.util.List;
import racingcar.domain.Car;
import racingcar.util.Cars;
import racingcar.util.RandomNumbers;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class GameController {

    public void play() {
        // 1. 게임 시작 시 안내 메시지를 출력한다.
        OutputView.printGameStartMessage();

        // 2. 게임 진행에 필요한 값들을 사용자로부터 입력 받는다.
        List<String> carNames = InputView.inputCarName();
        int numberOfAttempts = InputView.inputTryCount();
        String carNamesInput = String.join(",", carNames);

        // 3. 입력받은 값을 기반으로 Car 객체 리스트를 생성하고, 게임을 진행한다.
        List<Car> cars = createCars(carNamesInput);
        for (int attempt = 0; attempt < numberOfAttempts; attempt++) {
            moveCars(cars);
        }

        // 4. 우승자를 결정하고 사용자에게 출력한다.
        List<String> winners = determineWinners(cars);
        OutputView.printGameResult(cars, numberOfAttempts);
        OutputView.printWinners(winners);
    }

    private List<Car> createCars(String carNamesInput) {
        String[] carNames = carNamesInput.split(",");
        List<Car> cars = new ArrayList<>();
        for (String carName : carNames) {
            cars.add(new Car(carName));
        }
        return cars;
    }

    private void moveCar(Car car) {
        int randomNumber = RandomNumbers.pick();
        if (randomNumber >= 4) {
            car.moveForward();
        }
    }

    private void moveCars(List<Car> cars) {
        for (Car car : cars) {
            moveCar(car);
        }
        OutputView.printCurrentPositions(cars);
    }

    private List<String> determineWinners(List<Car> cars) {
        int maxPosition = getMaxPosition(cars);
        List<String> winners = Cars.getWinners(cars, maxPosition);
        return winners;
    }

    private int getMaxPosition(List<Car> cars) {
        int maxPosition = 0;
        for (Car car : cars) {
            maxPosition = Math.max(maxPosition, car.getPosition());
        }
        return maxPosition;
    }
}

