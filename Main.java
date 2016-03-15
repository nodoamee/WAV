package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import static java.lang.StrictMath.sqrt;

public class Main extends Application {

    private NumberAxis x=new NumberAxis();
    private NumberAxis y=new NumberAxis();
    private XYChart.Series Series=new XYChart.Series();
    private XYChart.Series appSeries=new XYChart.Series();
    private LineChart<Number,Number> lineChart=new LineChart<Number,Number>(x,y);

    private WAV wav=new WAV("am49.wav");//ド
    //private WAV wav=new WAV("am50.wav");//レ
    //private WAV wav=new WAV("am27.wav");//ドレミファソ
    private Integer[] data;
    private Byte[] byteData;
    private Integer[] soundData;
    private DFT d;

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
        soundData=wav.getSoundData();

        d=new DFT(soundData.length,soundData);

        /*for(int i=0;i<data.length;i++) {
            System.out.println("data["+i+"]"+data[i].byteValue());
        }*/

        /*for(int i=0;i<soundData.length;i++) {
            System.out.println("soundData[" + i + "]:" + soundData[i]);
            Series.getData().add(new XYChart.Data(i, soundData[i]));
        }*/

        for(int i=0;i<soundData.length/2;i++)
            Series.getData().add(new XYChart.Data(i, sqrt(sq(d.an(i))+sq(d.bn(i)))));



        /*for(int i=0;i<byteData.length;i++)
            System.out.println(byteData[i]);*/
    }

    private double sq(double i)
    {
        return i*i;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
