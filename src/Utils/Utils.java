package Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Utils {


    private static List<double[]> matrixI;
    private static List<double[]> matrixO;
    public static List<double[]> getInputs()
    {
        if(matrixI==null)
            read();
        return matrixI.subList(0,80);
    }
    public static List<double[]> getOutputs()
    {
        if(matrixO==null)
            read();
        return matrixO.subList(0,80);
    }
    public static List<double[]> getInputsR()
    {
        if(matrixI==null)
            read();
        return matrixI.subList(80,matrixI.size()-1);
    }
    public static List<double[]> getOutputsR()
    {
        if(matrixO==null)
            read();
        return matrixO.subList(80,matrixO.size()-1);
    }
    public static void read()
    {

        String fileName = "C:\\Users\\George\\Desktop\\Curs6\\Lab3AI\\src\\date.txt";
        File file = new File(fileName);

        byte [] fileBytes = new byte[0];

        try {
            fileBytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        char singleChar;
        StringBuilder stringBuilder=new StringBuilder();
        matrixI=new ArrayList<>();
        matrixO=new ArrayList<>();
        double[] vectorI=new double[8];
        double[] vectorR=new double[3];
        int p=0;
        int i=0;
        for(byte b : fileBytes) {
            singleChar = (char) b;
            if(singleChar==',')
            {
                if(p>7)
                {
                    vectorR[i]=Double.parseDouble(stringBuilder.toString());
                    i++;
                }
                else
                {
                    vectorI[p]=Double.parseDouble(stringBuilder.toString());
                    p++;
                }
                stringBuilder.delete(0,stringBuilder.length());
            }
            else
            {
                if(singleChar=='\n')
                {
                    vectorI[0]=1;
                    vectorR[i]=Double.parseDouble(stringBuilder.toString());
                    matrixI.add(vectorI);
                    matrixO.add(vectorR);
                    p=0;
                    i=0;
                    stringBuilder.delete(0,stringBuilder.length());
                    vectorI=new double[8];
                    vectorR=new double[3];
                }
                else
                    stringBuilder.append(singleChar);

            }
        }
    }
}
