/*
*   Jacek "dzik" Góraj, jckgrj@gmail.com
 */
package chartadvancedscatter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChartAdvancedScatter extends Application {

    private static final String SERIES_NAME = "Data Series: Elastic rod";
    private static final String X_AXIS_TITLE = "X Axis";
    private static final String Y_AXIS_TITLE = "Y Axis";

    private ScatterChart<Number, Number> createChart(ElasticRodChart chart) {
        ScatterChart<Number, Number> scatterChart = new ScatterChart<Number, Number>(getXAxis(), getYAxis());
        scatterChart.getData().add(getSeries(chart));
        scatterChart.setMinSize(500, 500);
        scatterChart.setMaxSize(500, 500);
        return scatterChart;
    }

    private NumberAxis getXAxis() {
        final NumberAxis x = new NumberAxis(-1, 1, 0.1);
        x.setSide(Side.BOTTOM);
        x.setLabel(X_AXIS_TITLE);
        return x;
    }

    private NumberAxis getYAxis() {
        final NumberAxis y = new NumberAxis(-1, 1, 0.1);
        y.setSide(Side.RIGHT);
        y.setLabel(Y_AXIS_TITLE);
        return y;
    }

    private XYChart.Series<Number, Number> getSeries(ElasticRodChart chart) {
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName(SERIES_NAME);
        int N = 258;
        for (int i = 0; i < N; i++) {
            series.getData().add(
                    new XYChart.Data<Number, Number>(chart.getXCoordinate(i), chart.getYCoordinate(i)));
        }
        return series;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ElasticRodChart chart = new ElasticRodChart(1.0, 0.55);
        //grid
        final GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 650, 650);
        scene.getStylesheets().add("chartadvancedscatter/Chart.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Elastic Rod - Jacek Góraj");
        grid.add(createChart(chart), 0, 0);
        VBox hbox = new VBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #F0BC63;");
        grid.add(hbox, 1, 0);
        Button btn = new Button();
        btn.setText("Simulate");
        Label tetaLabel = new Label("Teta[0]:");
        final TextField tetaInput = new TextField("0.32");
        Label vLabel = new Label("v[0]:");
        final TextField vInput = new TextField("1.0");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                ElasticRodChart chart = new ElasticRodChart(Double.parseDouble(tetaInput.getText()), Double.parseDouble(vInput.getText()));
                grid.getChildren().set(0, createChart(chart));
                System.out.println("Values changed!");
            }
        });
        hbox.getChildren().addAll(tetaLabel, tetaInput, vLabel, vInput, btn);
        grid.setGridLinesVisible(true);
        primaryStage.show();
    }

}
