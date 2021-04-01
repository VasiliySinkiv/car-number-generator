package com.example.car.number.generator.manager.impl;

import com.example.car.number.generator.entity.CarNumber;
import com.example.car.number.generator.manager.CarNumberGeneratorManager;
import com.example.car.number.generator.repository.CarNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class CarNumberGeneratorManagerImpl implements CarNumberGeneratorManager {

    @Autowired
    private CarNumberRepository carNumberRepository;

    private static final List<Character> LETTERS = Arrays.asList('А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х');
    private static final String NUMBER_LINE_FORMAT = "%c%03d%c%c %d %s";
    private static final int REGION = 116;
    private static final String COUNTRY = "RUS";
    private static final int MAX_POSITION_LETTER = LETTERS.size() - 1;
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 999;
    private static final int MAX_QUANTITY_NUMBERS = (int) Math.pow(LETTERS.size(), 3) * MAX_NUMBER;
    private static final Random RANDOMIZER = new Random();

    private boolean isInitialized;
    private long countNumbers;
    private int positionFirstLiteral;
    private int positionSecondLiteral;
    private int positionThirdLiteral;
    private int serialNumber;

    @Override
    public String generateNextNumber() {
        StringBuilder seriesBuilder = new StringBuilder();
        if (!isInitialized) {
            initPosition();
        }

        while (true) {
            if (countNumbers == MAX_QUANTITY_NUMBERS) {
                return "Номера закончились";
            }

            incrementNextNumber();

            seriesBuilder.append(LETTERS.get(positionFirstLiteral))
                    .append(LETTERS.get(positionSecondLiteral))
                    .append(LETTERS.get(positionThirdLiteral));

            CarNumber carNumber = doBuilderCarNumber(serialNumber,seriesBuilder.toString(), TypeGetting.SEQUENCE.getValue());
            seriesBuilder.setLength(0);

            if (isSave(carNumber)) {
                return lineFormatter(carNumber);
            }
        }
    }

    @Override
    public String generateRandomNumber() {
        StringBuilder seriesBuilder = new StringBuilder();
        while (true) {
            if (countNumbers == MAX_QUANTITY_NUMBERS) {
                return "Номера закончились";
            }

            seriesBuilder.append(randomLetter(positionFirstLiteral))
                    .append(randomLetter(0))
                    .append(randomLetter(0));

            int serialNumberRandom = RANDOMIZER.nextInt(MAX_NUMBER) + 1;

            CarNumber carNumber = doBuilderCarNumber(serialNumberRandom, seriesBuilder.toString(), TypeGetting.RANDOM.getValue());
            seriesBuilder.setLength(0);

            if (isSave(carNumber)) {
                return lineFormatter(carNumber);
            }
        }
    }

    private boolean isSave(CarNumber carNumber) {
        if (!carNumberRepository.existsCarNumberBySeriesAndNumberAndRegionAndCountry(carNumber.getSeries(),
                carNumber.getNumber(), carNumber.getRegion(), carNumber.getCountry())) {
            carNumberRepository.save(carNumber);
            countNumbers++;
            return true;
        }
        return false;
    }

    private void incrementNextNumber() {
        if (++serialNumber > MAX_NUMBER) {
            serialNumber = MIN_NUMBER;
            positionThirdLiteral++;
        }

        if (positionThirdLiteral > MAX_POSITION_LETTER) {
            positionSecondLiteral++;
            positionThirdLiteral = 0;
        }

        if (positionSecondLiteral > MAX_POSITION_LETTER) {
            positionFirstLiteral++;
            positionSecondLiteral = 0;
        }
    }

    private char randomLetter(int startIndex) {
        return LETTERS.get(RANDOMIZER.nextInt(LETTERS.size() - startIndex) + startIndex);
    }

    private String lineFormatter(CarNumber carNumber) {
        return String.format(NUMBER_LINE_FORMAT, carNumber.getSeries().charAt(0),
                carNumber.getNumber(),
                carNumber.getSeries().charAt(1),
                carNumber.getSeries().charAt(2),
                carNumber.getRegion(),
                carNumber.getCountry());
    }

    private void initPosition() {
        CarNumber carNumber = carNumberRepository
                .findFirstByTypeGettingOrderBySeriesDescNumberDesc(TypeGetting.SEQUENCE.getValue());
        if (carNumber == null) {
            return;
        }
        positionFirstLiteral = LETTERS.indexOf(carNumber.getSeries().charAt(0));
        positionSecondLiteral = LETTERS.indexOf(carNumber.getSeries().charAt(1));
        positionThirdLiteral = LETTERS.indexOf(carNumber.getSeries().charAt(2));
        serialNumber = carNumber.getNumber();

        countNumbers = carNumberRepository.count();
        isInitialized = true;
    }

    private CarNumber doBuilderCarNumber(int doNumber, String series, String typeGetting) {
        CarNumber carNumber = new CarNumber();
        carNumber.setSeries(series);
        carNumber.setNumber(doNumber);
        carNumber.setRegion(REGION);
        carNumber.setCountry(COUNTRY);
        carNumber.setTypeGetting(typeGetting);

        return carNumber;
    }

    public enum TypeGetting {
        SEQUENCE("sequence"),
        RANDOM("random");

        private final String value;

        public String getValue() {
            return value;
        }

        TypeGetting(String value) {
            this.value = value;
        }
    }

}
