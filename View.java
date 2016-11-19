package littlehtml;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.text.ParseException;

public class View extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("littleHTML syntax analyzer");

        GridPane rootNode = new GridPane();
        rootNode.setPadding(new Insets(15));
        rootNode.setHgap(5);
        rootNode.setVgap(5);
        rootNode.setAlignment(Pos.CENTER);

        Scene scene = new Scene(rootNode, 800, 600);

//        rootNode.add(new Label("littleHTML input:"), 0, 0);

        TextArea textArea = new TextArea();
        textArea.setPrefRowCount(50);
        textArea.setText("( ( ) ) ");
        rootNode.add(textArea, 1, 0);

        TextArea logArea = new TextArea();
        logArea.setPrefRowCount(50);
        logArea.setText("Output");
        logArea.setEditable(false);
        rootNode.add(logArea, 2, 0);

        Button buttonAnalyze = new Button("Analyze");
        rootNode.add(buttonAnalyze, 1, 2);
        GridPane.setHalignment(buttonAnalyze, HPos.LEFT);

        TextField output = new TextField();
        output.setEditable(false);
        rootNode.add(output, 1, 3);

        Printer printer = new Printer(logArea);
        Parser parser = new Parser(printer);

        buttonAnalyze.setOnAction(e -> {
            logArea.clear();
            String result;
            try {
                result = parser.parse(textArea.getText());
                output.setText("OK: " + result);
            } catch (ParseException ex) {
                output.setText("ERROR: " + ex.getMessage());
            }
        });

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
