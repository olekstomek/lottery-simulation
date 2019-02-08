import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        grid.setPadding(new Insets(10));
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
        labelInformation.setPadding(new Insets(10));

        List<Integer> digits = IntStream.range(1, 50).boxed().collect(Collectors.toList());
        Collections.shuffle(digits);
        int[] defaultValuesInInput = new int[6];
        for (int i = 0; i < 6; i++) {
            defaultValuesInInput[i] = digits.get(i);
        }

        final Spinner<Integer> firstNumberInput = new Spinner<>();
        firstNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 49,
                defaultValuesInInput[0]));
        final Spinner<Integer> secondNumberInput = new Spinner<>();
        secondNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 49,
                defaultValuesInInput[1]));
        final Spinner<Integer> thirdNumberInput = new Spinner<>();
        thirdNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 49,
                defaultValuesInInput[2]));
        final Spinner<Integer> fourthNumberInput = new Spinner<>();
        fourthNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 49,
                defaultValuesInInput[3]));
        final Spinner<Integer> fifthNumberInput = new Spinner<>();
        fifthNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 49,
                defaultValuesInInput[4]));
        final Spinner<Integer> sixthNumberInput = new Spinner<>();
        sixthNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 49,
                defaultValuesInInput[5]));

        Button runCheckRandomButton = new Button("Check your luck");
        runCheckRandomButton.setPrefSize(150, 30);
        runCheckRandomButton.setCursor(Cursor.HAND);

        Button runRandomDefaultValuesButton = new Button("Random new default values");
        runRandomDefaultValuesButton.setPrefSize(150, 30);
        runRandomDefaultValuesButton.setCursor(Cursor.HAND);

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
        GridPane.setConstraints(runRandomDefaultValuesButton, col, row++);

        setActionToInputNumbers(runRandomDefaultValuesButton, runCheckRandomButton, firstNumberInput, secondNumberInput, thirdNumberInput,
                fourthNumberInput, fifthNumberInput, sixthNumberInput, grid);

        grid.getChildren().addAll(labelInformation, firstNumberInput, secondNumberInput, thirdNumberInput,
                fourthNumberInput, fifthNumberInput, sixthNumberInput, runCheckRandomButton,
                runRandomDefaultValuesButton);

        return grid;
    }

    private boolean checkDuplicateValues(Set<Integer> inputNumbers) {
        int numberOfUniqeValues = inputNumbers.size();
        if (numberOfUniqeValues < 6) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Duplicate value!");
            alert.setContentText("Enter unique values!");
            alert.showAndWait();
        }
        return numberOfUniqeValues < 6;
    }

    private void setActionToInputNumbers(Button runRandomDefaultValuesButton, Button runCheckRandomButton, Spinner<Integer> firstNumberInput,
                                         Spinner<Integer> secondNumberInput, Spinner<Integer> thirdNumberInput,
                                         Spinner<Integer> fourthNumberInput, Spinner<Integer> fifthNumberInput,
                                         Spinner<Integer> sixthNumberInput, GridPane grid) {
        Set<Integer> randomNumbers = new HashSet<>(6);
        Set<Integer> inputNumbers = new HashSet<>(6);

        runRandomDefaultValuesButton.setOnAction(e -> setStudentAddFormToGrid(grid));

        runCheckRandomButton.setOnAction(e -> {
            inputNumbersToSet(inputNumbers, firstNumberInput, secondNumberInput, thirdNumberInput, fourthNumberInput,
                    fifthNumberInput, sixthNumberInput);
            if (checkDuplicateValues(inputNumbers))
                return;
            counter = 0;
            randomNumbersToSet(randomNumbers);
            while (!checkRandomNumbersWithInputNumbers(randomNumbers, inputNumbers)) {
                randomNumbers.clear();
                randomNumbersToSet(randomNumbers);
                counter++;
            }
            setScoreToGrid(grid);
            refreshScene();
        });
    }

    private String returnFormatResults() {
        NumberFormat formatter = NumberFormat.getInstance();
        String result;
        if (counter % 1000000 == 0 && counter != 0) {
            result = formatter.format(counter / 1000000) + "M";
        } else if (counter % 1000 == 0 && counter != 0) {
            result = formatter.format(counter / 1000) + "K";
        } else {
            result = formatter.format(counter);
        }
        return result;
    }

    private GridPane setScoreToGrid(GridPane grid) {
        Label counterLabel = new Label("Counter of draws: " + returnFormatResults() + " times.");
        counterLabel.setPadding(new Insets(10));
        GridPane.setConstraints(counterLabel, 1, 11);
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
