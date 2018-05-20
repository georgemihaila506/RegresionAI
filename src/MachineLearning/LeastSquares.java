package MachineLearning;

import Jama.Matrix;
import Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class LeastSquares extends AbstractSolve {


    private Matrix solveFile()
    {
        List<double[]> toInputs= Utils.getInputs();
        List<double[]> toOutputs= Utils.getOutputs();

        double[][] inputsV=new double[toInputs.size()][8];
        double[][] outputsV=new double[toOutputs.size()][3];
        for(int i=0;i<toInputs.size();i++)
        {
            inputsV[i]=toInputs.get(i);
            outputsV[i]=toOutputs.get(i);
        }
        Matrix inputs=new Matrix(inputsV);
        Matrix outputs=new Matrix(outputsV);
        Matrix newM = inputs.transpose().times(inputs);
        if(newM.det()!=0)
        {
            newM=newM.inverse().times(inputs.transpose()).times(outputs);

        }
        return newM;
    }
    public List<Double> solve(List<Double> inputs)
    {
        Matrix matrix=solveFile();
        List<Double> outputs=new ArrayList<>();
        for(int j=0;j<3;j++) {
            double result=1;
            for (int i = 1; i < 7; ++i) {

                result = matrix.get(0, j);
                result += inputs.get(i) * matrix.get(i, j);
            }
            outputs.add(Math.abs(result));
            }
        return outputs;
    }

}
