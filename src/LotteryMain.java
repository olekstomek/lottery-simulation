import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LotteryMain extends Application {
    private int counter;
    private Stage primaryStage;
    private Set<Integer> tempInputNumbers = new HashSet<>(6);
    private Set<Integer> tempFoundItemInputNumbers = new HashSet<>();
    private int previousTempFoundItemInputNumbers = 0;
    private TextArea textArea = new TextArea();
    private boolean firstRun = false;
    private int theNumberOfOccurrencesOfHitsForOne;
    private int theNumberOfOccurrencesOfHitsForTwo;
    private int theNumberOfOccurrencesOfHitsForThree;
    private int theNumberOfOccurrencesOfHitsForFour;
    private int theNumberOfOccurrencesOfHitsForFive;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Lotto simulator");
        primaryStage.setScene(initializeHomeScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Scene initializeHomeScene() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid = setAddFormToGrid(grid);
        grid = setScoreToGrid(grid);
        int height = 350;
        int width = 250;

        return new Scene(grid, width, height);
    }

    private void refreshScene() {
        primaryStage.setScene(initializeHomeScene());
    }

    private static boolean isNumericString(String input) {
        boolean result = false;

        if (input != null && input.length() > 0) {
            char[] charArray = input.toCharArray();

            for (char c : charArray) {
                if (c >= '0' && c <= '9') {
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    private <T> void commitEditorText(Spinner<T> spinner) {
        String text = spinner.getEditor().getText();
        SpinnerValueFactory<T> valueFactory = spinner.getValueFactory();

        if (!isNumericString(text)) {
            return;
        }

        if (valueFactory != null) {
            StringConverter<T> converter = valueFactory.getConverter();
            if (converter != null) {
                T value = converter.fromString(text);
                valueFactory.setValue(value);
            }
        }
    }

    private GridPane setAddFormToGrid(GridPane grid) {
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
        commitValuesFromKeyboard(firstNumberInput);

        final Spinner<Integer> secondNumberInput = new Spinner<>();
        secondNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 49,
                defaultValuesInInput[1]));
        commitValuesFromKeyboard(secondNumberInput);

        final Spinner<Integer> thirdNumberInput = new Spinner<>();
        thirdNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 49,
                defaultValuesInInput[2]));
        commitValuesFromKeyboard(thirdNumberInput);

        final Spinner<Integer> fourthNumberInput = new Spinner<>();
        fourthNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 49,
                defaultValuesInInput[3]));
        commitValuesFromKeyboard(fourthNumberInput);

        final Spinner<Integer> fifthNumberInput = new Spinner<>();
        fifthNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 49,
                defaultValuesInInput[4]));
        commitValuesFromKeyboard(fifthNumberInput);

        final Spinner<Integer> sixthNumberInput = new Spinner<>();
        sixthNumberInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 49,
                defaultValuesInInput[5]));
        commitValuesFromKeyboard(sixthNumberInput);

        Button runCheckRandomButton = new Button("Check your luck");
        runCheckRandomButton.setPrefSize(150, 30);
        runCheckRandomButton.setCursor(Cursor.HAND);

        Button runRandomDefaultValuesButton = new Button("Random new default values");
        runRandomDefaultValuesButton.setPrefSize(150, 30);
        runRandomDefaultValuesButton.setCursor(Cursor.HAND);

        Button showDetails = new Button("Show details");
        showDetails.setPrefSize(150, 30);
        showDetails.setCursor(Cursor.HAND);

        if (!firstRun) {
            showDetails.setDisable(true);
        } else {
            showDetails.setDisable(false);
        }

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
        GridPane.setConstraints(showDetails, col, row++);

        setActionToInputNumbers(showDetails, runRandomDefaultValuesButton, runCheckRandomButton, firstNumberInput,
                secondNumberInput, thirdNumberInput, fourthNumberInput, fifthNumberInput, sixthNumberInput, grid);

        grid.getChildren().addAll(labelInformation, firstNumberInput, secondNumberInput, thirdNumberInput,
                fourthNumberInput, fifthNumberInput, sixthNumberInput, runCheckRandomButton,
                runRandomDefaultValuesButton, showDetails);

        return grid;
    }

    private void commitValuesFromKeyboard(Spinner<Integer> numberInput) {
        numberInput.setEditable(true);
        numberInput.focusedProperty().addListener((s, ov, nv) -> {
            if (nv) return;
            commitEditorText(numberInput);
        });
    }

    private boolean checkDuplicateValues(Set<Integer> inputNumbers) {
        int numberOfUniqueValues = inputNumbers.size();
        if (numberOfUniqueValues < 6) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Duplicate value!");
            alert.setContentText("Enter unique values!");
            alert.showAndWait();
        }
        return numberOfUniqueValues < 6;
    }

    private void setActionToInputNumbers(Button showDetails, Button runRandomDefaultValuesButton,
                                         Button runCheckRandomButton, Spinner<Integer> firstNumberInput,
                                         Spinner<Integer> secondNumberInput, Spinner<Integer> thirdNumberInput,
                                         Spinner<Integer> fourthNumberInput, Spinner<Integer> fifthNumberInput,
                                         Spinner<Integer> sixthNumberInput, GridPane grid) {
        Set<Integer> randomNumbers = new HashSet<>(6);
        Set<Integer> inputNumbers = new HashSet<>(6);
        runRandomDefaultValuesButton.setOnAction(e -> setAddFormToGrid(grid));
        showDetails.setOnAction(e -> {

            StackPane secondaryLayout = new StackPane();
            textArea.appendText(
                    "\nThe number of occurrences of hits for one number: "
                            + returnFormatResults(theNumberOfOccurrencesOfHitsForOne) +
                            "\nThe number of occurrences of hits for a set of two numbers: "
                            + returnFormatResults(theNumberOfOccurrencesOfHitsForTwo) +
                            "\nThe number of occurrences of hits for a set of three numbers: "
                            + returnFormatResults(theNumberOfOccurrencesOfHitsForThree) +
                            "\nThe number of occurrences of hits for a set of four numbers: "
                            + returnFormatResults(theNumberOfOccurrencesOfHitsForFour) +
                            "\nThe number of occurrences of hits for a set of five numbers: "
                            + returnFormatResults(theNumberOfOccurrencesOfHitsForFive));
            textArea.appendText("\n---------------------\n");
            secondaryLayout.getChildren().add(textArea);

            Scene secondScene = new Scene(secondaryLayout, 450, 250);

            Stage newWindow = new Stage();

            newWindow.setTitle("Details");
            newWindow.setScene(secondScene);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(primaryStage);
            newWindow.show();


        });

        runCheckRandomButton.setOnAction(e -> {
            resetNumberOfOccurrences();

            inputNumbersToSet(inputNumbers, firstNumberInput, secondNumberInput, thirdNumberInput, fourthNumberInput,
                    fifthNumberInput, sixthNumberInput);
            if (checkDuplicateValues(inputNumbers))
                return;
            counter = 0;
            tempInputNumbers.addAll(inputNumbers);
            tempInputNumbers = new TreeSet<>(tempInputNumbers);
            randomNumbersToSet(randomNumbers);
            while (!checkRandomNumbersWithInputNumbers(randomNumbers, inputNumbers)) {
                randomNumbers.clear();
                randomNumbersToSet(randomNumbers);
                ++counter;
            }
            setScoreToGrid(grid);
            refreshScene();
            tempInputNumbers.clear();
        });
    }

    private void resetNumberOfOccurrences() {
        theNumberOfOccurrencesOfHitsForOne = 0;
        theNumberOfOccurrencesOfHitsForTwo = 0;
        theNumberOfOccurrencesOfHitsForThree = 0;
        theNumberOfOccurrencesOfHitsForFour = 0;
        theNumberOfOccurrencesOfHitsForFive = 0;
    }

    private String returnFormatResults(int counter) {
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
        Label counterLabel = new Label("Counter of draws: " + returnFormatResults(counter) + "\ntimes for values: "
                + tempInputNumbers);
        counterLabel.setPadding(new Insets(10));
        GridPane.setConstraints(counterLabel, 1, 11);
        grid.getChildren().add(counterLabel);

        return grid;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private boolean checkRandomNumbersWithInputNumbers(Set<Integer> randomNumbers, Set<Integer> inputNumbers) {
        for (int r : inputNumbers) {
            if (randomNumbers.contains(r)) {
                tempFoundItemInputNumbers.add(r);
            }
        }

        countNumberOfOccurrences();

        if (tempFoundItemInputNumbers.size() > previousTempFoundItemInputNumbers) {
            textArea.appendText("Found " + tempFoundItemInputNumbers.size() + " values. The values: "
                    + tempFoundItemInputNumbers + " hit after the " + returnFormatResults(++counter) + " attempt\n");
            previousTempFoundItemInputNumbers = tempFoundItemInputNumbers.size();
        }
        tempFoundItemInputNumbers.clear();

        if (previousTempFoundItemInputNumbers == 6) {
            previousTempFoundItemInputNumbers = 0;
        }

        firstRun = true;

        return randomNumbers.containsAll(inputNumbers);
    }

    private void countNumberOfOccurrences() {
        switch (tempFoundItemInputNumbers.size()) {
            case 1:
                ++theNumberOfOccurrencesOfHitsForOne;
                break;
            case 2:
                ++theNumberOfOccurrencesOfHitsForTwo;
                break;
            case 3:
                ++theNumberOfOccurrencesOfHitsForThree;
                break;
            case 4:
                ++theNumberOfOccurrencesOfHitsForFour;
                break;
            case 5:
                ++theNumberOfOccurrencesOfHitsForFive;
                break;
            default:
        }
    }

    private void randomNumbersToSet(Set<Integer> randomNumbers) {
        while (randomNumbers.size() < 6) {
            int randomNumber = (int) (Math.random() * 49 + 1);
            randomNumbers.add(randomNumber);
        }
    }

    private void inputNumbersToSet(Set<Integer> inputNumbers, Spinner<Integer> firstNumberInput,
                                   Spinner<Integer> secondNumberInput, Spinner<Integer> thirdNumberInput,
                                   Spinner<Integer> fourthNumberInput, Spinner<Integer> fifthNumberInput,
                                   Spinner<Integer> sixthNumberInput) {
        inputNumbers.add(firstNumberInput.getValue());
        inputNumbers.add(secondNumberInput.getValue());
        inputNumbers.add(thirdNumberInput.getValue());
        inputNumbers.add(fourthNumberInput.getValue());
        inputNumbers.add(fifthNumberInput.getValue());
        inputNumbers.add(sixthNumberInput.getValue());
    }
}