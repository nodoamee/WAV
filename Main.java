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
    //private WAV wav=new WAV("400Hz.WAV");
    //private WAV wav=new WAV("500Hz.wav");

    Player player=new Player();

    private Integer[] data;
    private Byte[] byteData;
    private Short[] soundData;
    private DFT d;
    private int count=0;

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

        d=new DFT(WAV.dataInf.samplingRate,soundData);

        player.start();
        //player.setVoice(data);

        /*for(int i=0;i<data.length;i++) {
            System.out.println("data["+i+"]"+data[i].byteValue());
        }*/

        /*for(int i=0;i<soundData.length;i++) {
            System.out.println("soundData[" + i + "]:" + soundData[i]);
            Series.getData().add(new XYChart.Data(i, soundData[i]));
        }*/

        for(int i=0;i<WAV.dataInf.samplingRate/2;i++)
            Series.getData().add(new XYChart.Data(i, sqrt(sq(d.an(i))+sq(d.bn(i)))));



        /*for(int i=0;i<byteData.length;i++)
            System.out.println(byteData[i]);*/


        /*while( true )
        {
            try
            {
                // 音声コピー
                player.setVoice(data);

                // 100ms停止
                Thread.sleep( 100 );
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // １０秒ループ
            count++;
            if( count > ( 10 * 10 ) )
            {
                player.end();
            }

            // 脱出
            if( !player.g_bPlayer)
                break;
        }*/
    }

    private double sq(double i)
    {
        return i*i;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop()
    {
        player.end();
    }
}
