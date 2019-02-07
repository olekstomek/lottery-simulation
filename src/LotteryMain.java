import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LotteryMain extends Application {
    private int counter;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Symulator Lotto");
        primaryStage.setScene(initializeHomeScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Scene initializeHomeScene() {
        GridPane grid = new GridPane();
        grid = setStudentAddFormToGrid(grid);
        grid = setScoreToGrid(grid);
        int height = 300;
        int width = 250;
        return new Scene(grid, width, height);
    }

    private void refreshScene() {
        primaryStage.setScene(initializeHomeScene());
    }

    private GridPane setStudentAddFormToGrid(GridPane grid) {
        Label labelInformation = new Label("Input six numbers from 1 to 49: ");

        List<Integer> digits = IntStream.range(1, 50).boxed().collect(Collectors.toList());
        Collections.shuffle(digits);
        int[] defaultValuesInInput = new int[6];
        for (int i = 0; i < 6; i++) {
            defaultValuesInInput[i] = digits.get(i);
        }

        final Spinner<Integer> firstNumberInput = new Spinner<>();
        firstNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 49,
                defaultValuesInInput[0]));
        firstNumberInput.setEditable(true);
        final Spinner<Integer> secondNumberInput = new Spinner<>();
        secondNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 49,
                defaultValuesInInput[1]));
        secondNumberInput.setEditable(true);
        final Spinner<Integer> thirdNumberInput = new Spinner<>();
        thirdNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 49,
                defaultValuesInInput[2]));
        thirdNumberInput.setEditable(true);
        final Spinner<Integer> fourthNumberInput = new Spinner<>();
        fourthNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 49,
                defaultValuesInInput[3]));
        fourthNumberInput.setEditable(true);
        final Spinner<Integer> fifthNumberInput = new Spinner<>();
        fifthNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 49,
                defaultValuesInInput[4]));
        fifthNumberInput.setEditable(true);
        final Spinner<Integer> sixthNumberInput = new Spinner<>();
        sixthNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 49,
                defaultValuesInInput[5]));
        sixthNumberInput.setEditable(true);

        Button runCheckRandomButton = new Button("Check your luck");

        int row = 1;
        int col = 1;
        GridPane.setConstraints(labelInformation, col, row++);
        GridPane.setConstraints(firstNumberInput, col, row++);
        GridPane.setConstraints(secondNumberInput, col, row++);
        GridPane.setConstraints(thirdNumberInput, col, row++);
        GridPane.setConstraints(fourthNumberInput, col, row++);
        GridPane.setConstraints(fifthNumberInput, col, row++);
        GridPane.setConstraints(sixthNumberInput, col, row++);
        GridPane.setConstraints(runCheckRandomButton, col, row++);

        setActionToInputNumbers(runCheckRandomButton, firstNumberInput, secondNumberInput, thirdNumberInput,
                fourthNumberInput, fifthNumberInput, sixthNumberInput, grid);

        grid.getChildren().addAll(labelInformation, firstNumberInput, secondNumberInput, thirdNumberInput,
                fourthNumberInput, fifthNumberInput, sixthNumberInput, runCheckRandomButton);

        return grid;
    }

    private void setActionToInputNumbers(Button runCheckRandomButton, Spinner<Integer> firstNumberInput,
                                         Spinner<Integer> secondNumberInput, Spinner<Integer> thirdNumberInput,
                                         Spinner<Integer> fourthNumberInput, Spinner<Integer> fifthNumberInput,
                                         Spinner<Integer> sixthNumberInput, GridPane grid) {
        Set<Integer> randomNumbers = new HashSet<>();
        Set<Integer> inputNumbers = new HashSet<>();
        runCheckRandomButton.setOnAction(e -> {
            counter = 0;
            inputNumbersToSet(inputNumbers, firstNumberInput, secondNumberInput, thirdNumberInput,
                    fourthNumberInput, fifthNumberInput, sixthNumberInput);
            randomNumbersToSet(randomNumbers);
            checkRandomNumbersWithInputNumbers(randomNumbers, inputNumbers);
            while (!checkRandomNumbersWithInputNumbers(randomNumbers, inputNumbers)) {
                randomNumbers.clear();
                counter++;
                randomNumbersToSet(randomNumbers);
            }
            setScoreToGrid(grid);
            refreshScene();
        });
    }

    private GridPane setScoreToGrid(GridPane grid) {
        NumberFormat formatter = NumberFormat.getInstance();
        String result;
        if (counter % 1000000 == 0 && counter != 0) {
            result = formatter.format(counter / 1000000) + "M";
        } else if (counter % 1000 == 0 && counter != 0) {
            result = formatter.format(counter / 1000) + "K";
        } else {
            result = formatter.format(counter);
        }
        Label counterLabel = new Label("Counter of draws: " + result + " times.");
        GridPane.setConstraints(counterLabel, 1, 9);
        grid.getChildren().add(counterLabel);

        return grid;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private boolean checkRandomNumbersWithInputNumbers(Set<Integer> randomNumbers, Set<Integer> inputNumbers) {
        return randomNumbers.containsAll(inputNumbers);
    }

    private void randomNumbersToSet(Set<Integer> randomNumbers) {
        while (randomNumbers.size() < 6) {
            int randomNumber = (int) (Math.random() * 49 + 1);
            randomNumbers.add(randomNumber);
        }
    }

    private void inputNumbersToSet(Set<Integer> inputNumbers, Spinner<Integer> firstNumberInput, Spinner<Integer> secondNumberInput,
                                   Spinner<Integer> thirdNumberInput, Spinner<Integer> fourthNumberInput, Spinner<Integer> fifthNumberInput,
                                   Spinner<Integer> sixthNumberInput) {
        inputNumbers.add(firstNumberInput.getValue());
        inputNumbers.add(secondNumberInput.getValue());
        inputNumbers.add(thirdNumberInput.getValue());
        inputNumbers.add(fourthNumberInput.getValue());
        inputNumbers.add(fifthNumberInput.getValue());
        inputNumbers.add(sixthNumberInput.getValue());
    }
}
