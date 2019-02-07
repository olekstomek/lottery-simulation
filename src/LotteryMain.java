import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Set;

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

        TextField firstNumberInput = new TextField();
        firstNumberInput.setPromptText("Input unique number");
        TextField secondNumberInput = new TextField();
        secondNumberInput.setPromptText("Input unique number");
        TextField thirdNumberInput = new TextField();
        thirdNumberInput.setPromptText("Input unique number");
        TextField fourthNumberInput = new TextField();
        fourthNumberInput.setPromptText("Input unique number");
        TextField fifthNumberInput = new TextField();
        fifthNumberInput.setPromptText("Input unique number");
        TextField sixthNumberInput = new TextField();
        sixthNumberInput.setPromptText("Input unique number");

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

    private void setActionToInputNumbers(Button runCheckRandomButton, TextField firstNumberInput,
                                         TextField secondNumberInput, TextField thirdNumberInput,
                                         TextField fourthNumberInput, TextField fifthNumberInput,
                                         TextField sixthNumberInput, GridPane grid) {
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
        Label counterLabel = new Label("Counter of draws: " + result + " times");
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

    private void inputNumbersToSet(Set<Integer> inputNumbers, TextField firstNumberInput, TextField secondNumberInput,
                                   TextField thirdNumberInput, TextField fourthNumberInput, TextField fifthNumberInput,
                                   TextField sixthNumberInput) {
            inputNumbers.add(Integer.parseInt(firstNumberInput.getText()));
            inputNumbers.add(Integer.parseInt(secondNumberInput.getText()));
            inputNumbers.add(Integer.parseInt(thirdNumberInput.getText()));
            inputNumbers.add(Integer.parseInt(fourthNumberInput.getText()));
            inputNumbers.add(Integer.parseInt(fifthNumberInput.getText()));
            inputNumbers.add(Integer.parseInt(sixthNumberInput.getText()));
    }
}
