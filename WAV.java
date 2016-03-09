package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abedaigorou on 16/03/08.
 */
public class WAV
{
    private InputStream wavIn;
    private List<Byte> datalList=new ArrayList<Byte>();
    private Byte[] data;

    public WAV(String fileName)
    {
        try {
            wavIn = new BufferedInputStream(new FileInputStream(fileName));
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public Byte[] readData()
    {
        try {
            for(int d=0;d!=-1;d=wavIn.read()) {
                int i=0;
                datalList.add((byte) d);
                /*System.out.println((byte)d);
                System.out.println(datalList.get(i));*/
                i++;
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        data= datalList.toArray(new Byte[0]);

        return data;
    }

    public void readHeader()
    {
        do {
            int c;
            if ((c = checkHeader("RIFF")) != -1) {//RIFFヘッダ確認
                dataInf.fileSize=readBytes(4,c)+8;
                System.out.println("ファイルサイズ:"+dataInf.fileSize+"byte");
            } else
                break;

            if(checkHeader("WAVE")!=-1)
                System.out.println("WAVEデータ確認");
            else
                break;

            if((c=checkHeader("fmt "))!=-1) {
                dataInf.fmtBytes = readBytes(4, c);
                System.out.println("fmtチャンクサイズ:"+dataInf.fmtBytes+"byte");
                System.out.println("fmtC"+c+"data"+data[c]);

                dataInf.fmtID=readBytes(2,c+=4);
                System.out.println("フォーマットID:"+dataInf.fmtID);
                System.out.println("IDC"+c+"data"+data[c]);

                dataInf.ch=readBytes(2,c+=2);
                System.out.println("チャンネル数:"+dataInf.ch);
                System.out.println("CHC"+c+"data"+data[c]);

                dataInf.samplingRate=readBytes(4,c+=2);
                System.out.println("サンプリングレート:"+dataInf.samplingRate);
                System.out.println("reC"+c+"data"+data[c]);

                dataInf.dataSpeed=readBytes(4,c+=4);
                System.out.println("データ速度:"+dataInf.dataSpeed);
                System.out.println("dataC"+c+"data"+data[c]);

                //dataInf.blockSize=readBytes(2,c+16);

            }
            else
                break;



        }while(false);
    }

    private int readBytes(int byteNum,int indexNum)//リトルエンディアンでindexNum番目からbyteNumバイト読み込む
    {
        indexNum+=byteNum;
        int bytes=0;
        int j=0;
        for (int i = 2*(byteNum-1); i >= 0; i -= 2)
            bytes += (data[indexNum + j--] * Math.pow(16, i));
        return bytes;
    }

    private int checkHeader(String cd)//data配列を走査してヘッダを見つける
    {
        int count = 0;
        for(int d=0;d<data.length;d++) {
            if (cd.charAt(count) == data[d])
                count++;
            else
                count=0;
            if(count == cd.length())
                return d;
        }
        return -1;
    }

    public static class dataInf
    {
        public static int fileSize;
        public static int fmtBytes;
        public static int fmtID;
        public static int ch;
        public static int samplingRate;
        public static int dataSpeed;
        public static int blockSize;
        public static int bitParSample;
    }
}
