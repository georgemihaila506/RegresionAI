package Utils;

import Model.Chromosome;

import java.util.List;
import java.util.function.Function;

public class Functions {
    public static Function< List<Double>,Double> createFunction(Chromosome chromosome){

        List <Double> l = chromosome.getFunctionArgument();

        return (list)->{

            Double result  = l.get(7);//free term

            for(int i = 0; i < 7; ++i) result += l.get(i) * list.get(i);

            return result;
        };

    }
    public static Function< List<Double>,Double> createFunction(List<Double> l){

        return (list)->{

            double result  = l.get(7);//free term

            for(int i = 0; i < 7; ++i) result += l.get(i) * list.get(i);

            return result;
        };
    }
    public static Function< List<Double>,Double> getOnlyPositiveFunction(List<Double> l){


        return (list)->{

            double result  = l.get(7);//free term

            for(int i = 0; i < 7; ++i) result += l.get(i) * list.get(i);

            return Math.abs(result);
        };
    }
}
