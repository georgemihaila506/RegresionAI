package MachineLearning;

import Utils.Functions;
import Utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class GradientDescent extends AbstractSolve {

    private int epoch = 10;

    private List <Double> functionFact = new ArrayList<>();


    private double learningRate =0.001;

    private List<double[]> fInputs;
    private List<double[]> fOutputs;
    private List<Double> outputs;
    private List<List<Double>> inputs;

    private void initializeFact(){
        functionFact.clear();
        functionFact.addAll(Arrays.asList(0d,0d,0d,0d,0d,0d,0d,0d));
    }

    private Function<List<Double>,Double> processFunction(List<Double> dOutputs){
        this.outputs=dOutputs;

        initializeFact();

        Function<List<Double>,Double> func  = Functions.createFunction(functionFact);

        for(int j = 1; j <= epoch; ++j) {

            for (int i = 0; i < inputs.size(); ++i) {

                List<Double> newFunctionFact = new ArrayList<>();

                for (int k = 0; k < 8; ++k) {

                    double error = calculateError(k, func);

                    double toPut = functionFact.get(k);

                    toPut = toPut + learningRate * error;

                    newFunctionFact.add(toPut);
                }

                functionFact = newFunctionFact;

                func = Functions.createFunction(functionFact);

            }
        }

        List<Double>list  = new ArrayList<>();

        list.addAll(functionFact);

        return Functions.getOnlyPositiveFunction(list);
    }

    private double calculateError(int index,Function <List <Double>, Double> fct){

        double sum = 0;

        if(index != 7){

            for(int indexOutput = 0; indexOutput < outputs.size(); ++indexOutput){

                sum = sum + (outputs.get(indexOutput) - fct.apply(inputs.get(indexOutput))) * inputs.get(indexOutput).get(index);

            }

            return 1.0 / (learningRate * sum);
        }

        for(int indexOutput = 0; indexOutput < outputs.size(); ++indexOutput){

            sum = sum + (outputs.get(indexOutput) - fct.apply(inputs.get(indexOutput)));

        }


        return (1.0 / inputs.size()) * sum;

    }

    public Function < List<Double>,List<Double> > solveByFunction()
    {
        List<Double> y1Out = new ArrayList<>(),y2Out = new ArrayList<>(),y3Out = new ArrayList<>();

        for(int i=0;i<fOutputs.size();i++)
        {
            y1Out.add(fOutputs.get(i)[0]);
            y2Out.add(fOutputs.get(i)[1]);
            y3Out.add(fOutputs.get(i)[2]);
        }
        for(int i=0;i<fInputs.size();i++)
        {
            List<Double> toIntroduce=new ArrayList<>();
            //toIntroduce.add(fInputs.get(i)[0]);
            toIntroduce.add(fInputs.get(i)[1]);
            toIntroduce.add(fInputs.get(i)[2]);
            toIntroduce.add(fInputs.get(i)[3]);
            toIntroduce.add(fInputs.get(i)[4]);
            toIntroduce.add(fInputs.get(i)[5]);
            toIntroduce.add(fInputs.get(i)[6]);
            toIntroduce.add(fInputs.get(i)[7]);
            inputs.add(toIntroduce);
        }
        Function<List <Double>,Double> fct = processFunction(y1Out);
        Function <List <Double>,Double>fct2 = processFunction(y2Out);
        Function <List<Double>,Double> fct3 = processFunction(y3Out);

        return (list)->Arrays.asList(
                fct.apply(list),
                fct2.apply(list),
                fct3.apply(list)
        );
    }
    @Override
    public List<Double> solve(List<Double> finputs) {
        initializeFact();
        fInputs=Utils.getInputs();
        fOutputs=Utils.getOutputs();
        inputs=new ArrayList<>();
        Function < List<Double>,List<Double>> myFunction=solveByFunction();
        return myFunction.apply(finputs);
    }
}
