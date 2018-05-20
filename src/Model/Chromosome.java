package Model;

import java.util.ArrayList;
import java.util.List;

public class Chromosome {
    private double fitness;
    private List<Double> functionArgument=new ArrayList<>();
    public double getFitness()
    {
        return fitness;
    }
    public void setFitness(double fitness)
    {
        this.fitness=fitness;
    }
    public List<Double> getFunctionArgument() {
        return functionArgument;
    }
    public void setFunctionArgument(List<Double> functionArgument) {
        this.functionArgument = functionArgument;
    }
    @Override
    public String toString(){
        return functionArgument.toString() + " " + fitness;
    }
}
