package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Main extends Application {

    private NumberAxis x=new NumberAxis();
    private NumberAxis y=new NumberAxis();
    private XYChart.Series Series=new XYChart.Series();
    private XYChart.Series appSeries=new XYChart.Series();
    private LineChart<Number,Number> lineChart=new LineChart<Number,Number>(x,y);

    private WAV wav=new WAV("am49.wav");
    private Integer[] data;
    private Byte[] byteData;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(lineChart, 1900, 1800));
        lineChart.getData().add(Series);
        lineChart.getData().add(appSeries);
        primaryStage.show();

        data=wav.readData();
        //byteData=wav.readByteData();

        wav.readHeader();

        for(int i=0;i<data.length;i++) {
            System.out.println("data["+i+"]"+data[i].byteValue());
        }
        /*for(int i=0;i<byteData.length;i++)
            System.out.println(byteData[i]);*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
