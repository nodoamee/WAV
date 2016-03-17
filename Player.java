package sample;

import javax.sound.sampled.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by abedaigorou on 16/03/16.
 */

public class Player extends Thread
{
    private static final int BITS = 16;
    private static final int HZ = 16000;
    private static final int MONO = 1;

    // リニアPCM 16bit 16000hz x １秒
    private byte[] voice = new byte[ HZ * BITS / 8 * MONO ];

    private SourceDataLine source;

    public boolean g_bPlayer = false;

    // コンストラクタ
    Player()
    {
        g_bPlayer = true;

        try
        {
            // オーディオフォーマットの指定
            AudioFormat linear = new AudioFormat(HZ, BITS, MONO, true, true );
            //AudioFormat linear=new AudioFormat(new AudioFormat.Encoding("PCM_SIGNED"),HZ,BITS,MONO,2,HZ,false);
            System.out.println(linear.toString());

            // ソースデータラインを取得
            DataLine.Info info = new DataLine.Info( SourceDataLine.class, linear );
            source = (SourceDataLine) AudioSystem.getLine(info);

            // ソースデータラインを開く
            source.open( linear );

            // スピーカー出力開始
            source.start();

        } catch (LineUnavailableException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // スレッド実行
    public void run()
    {
        while( true )
        {
            if( !g_bPlayer ) return;

            // スピーカーに音声データを出力
            source.write(voice, WAV.dataInf.dataPoint+4 ,(voice.length-WAV.dataInf.dataPoint-5));
            // 一応、ウエイト
            try{
                Thread.sleep( 100 );
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // データ設定
    public void setVoice( byte[] b )
    {
        voice = b;
    }

    public void setVoice(Integer[] integers)
    {
        for(int i=0;i<integers.length;i++) {
            voice[i] = integers[i].byteValue();
            //System.out.println("voice["+i+"]:"+integers[i].byteValue());
        }
    }

    // 終了
    public void end()
    {
        g_bPlayer = false;

        // ソースデータラインを停止
        source.stop();

        // ソースデータラインを閉じる
        source.close();
    }
}