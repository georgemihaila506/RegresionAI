package MachineLearning;

import Model.Chromosome;
import Utils.Functions;
import Utils.Utils;

import java.util.*;
import java.util.function.Function;

public class Evo extends AbstractSolve {
    private List<Chromosome> population = new ArrayList<>();

    private List < List <Double> > inputs = new ArrayList<>();

    private List < Double > outputs = new ArrayList<>();
    private List<double[]> fInputs;
    private List<double[]> fOutputs;

    private int popSize,nrGen = 1000;
    private Double eps = 0.044;
    private Double leftSide,rightSide;

    public void setLeftSide(Double leftSide) {
        this.leftSide = leftSide;
    }

    public void setRightSide(Double rightSide) {
        this.rightSide = rightSide;
    }

    private void initPopulation(){

        population.clear();

        Random random = new Random(System.nanoTime());

        for(int i = 1; i <= popSize; ++i){
            Chromosome chromosome = new Chromosome();

            for(int var = 0;  var <= 7; ++var)
                chromosome.getFunctionArgument().add(random.nextDouble()* (rightSide - leftSide) + leftSide);

            population.add(chromosome);
        }

    }

    private double evaluate(Chromosome chromosome){

        double squaredError = 0;

        for(int line = 0; line < inputs.size(); ++line){

            List <Double> trainingRow = inputs.get(line);

            double calculated = chromosome.getFunctionArgument().get(7);// free term

            for(int var = 0; var < 7; ++var){
                calculated += chromosome.getFunctionArgument().get(var) * trainingRow.get(var);
            }

            double desired = outputs.get(line);

            squaredError += (calculated - desired) * (calculated - desired);
        }

        return squaredError;
    }

    private void evaluatePop(){

        for(Chromosome chromosome : population){
            chromosome.setFitness(evaluate(chromosome));
        }
    }

    private Chromosome getWorst(){

        int index = 0;

        for(int i = 1; i < population.size(); ++i)
            if(population.get(index).getFitness() < population.get(i).getFitness())index = i;

        return population.get(index);
    }

    private Chromosome select(int fromWhat){

        Random random = new Random(System.nanoTime());
        BitSet bitSet = new BitSet(popSize + 1);

        int index = random.nextInt(popSize);

        for(int i = 1; i <= fromWhat; ++i){

            bitSet.set(index);

            int enemy = random.nextInt(popSize);

            if(bitSet.get(enemy)){
                --i;
                continue;
            }

            if(population.get(index).getFitness() > population.get(enemy).getFitness())index = enemy;
        }

        return population.get(index);
    }

    private  List < Integer > getRandom(int size,int splitNr){

        List < Integer > list = new ArrayList<>();

        Random random = new Random(System.nanoTime());

        BitSet bitSet = new BitSet(size + 1);

        for(int i = 1;i <= splitNr; ++i){
            int number = random.nextInt(size - 1);

            if(bitSet.get(number) || number == 0){
                --i;
                continue;
            }

            bitSet.set(number);

            list.add(number);
        }

        list.sort(Integer::compare);

        return list;
    }

    private Chromosome crossOver(Chromosome mom, Chromosome dad){

        List <Integer> Points = getRandom(mom.getFunctionArgument().size(),1);

        Chromosome child = new Chromosome();

        boolean firstParentTime = false;

        int left = 0;

        for(int point : Points){

            firstParentTime = !firstParentTime;

            if(firstParentTime){
                child.getFunctionArgument().addAll(mom.getFunctionArgument().subList(left,point)) ;
                left = point;
                continue;
            }

            child.getFunctionArgument().addAll(dad.getFunctionArgument().subList(left,point));
            left = point;
        }

        firstParentTime = !firstParentTime;

        if(firstParentTime){
            child.getFunctionArgument().addAll(mom.getFunctionArgument().subList(left,mom.getFunctionArgument().size())) ;
            return  child;
        }

        child.getFunctionArgument().addAll(dad.getFunctionArgument().subList(left,dad.getFunctionArgument().size()));

        return  child;
    }

    private Chromosome mutate(Chromosome child){

        Random random = new Random(System.nanoTime());

        for(int i = 0; i < child.getFunctionArgument().size(); ++i) {
            child.getFunctionArgument().set(i, child.getFunctionArgument().get(i) + random.nextDouble() < 0.8 ? eps : -eps * eps);
        }

        return  child;
    }

    public void setPopSize(int popSize) {
        this.popSize = popSize;
    }

    private Function< List <Double>, Double> getFunc(List <Double> list){

        outputs = list;

        initPopulation();

        evaluatePop();


        for(int i = 1; i <= nrGen;++i){

            Chromosome mom = select(200);
            Chromosome dad = select(200);


            Chromosome first = mutate(crossOver(mom,dad));
            Chromosome second = mutate(crossOver(dad,mom));

            first.setFitness(evaluate(first));
            second.setFitness(evaluate(second));

            if(first.getFitness() > second.getFitness()) first = second;

            Chromosome worst = getWorst();

            if(worst.getFitness() <= first.getFitness())continue;

            worst.setFunctionArgument(first.getFunctionArgument());
            worst.setFitness(first.getFitness());
        }


        int index = 0;

        for(int i = 1; i < population.size(); ++i){
            if(population.get(index).getFitness() <= population.get(i).getFitness())continue;
            index = i;
        }

        return Functions.createFunction(population.get(index));
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
            toIntroduce.add(fInputs.get(i)[1]);
            toIntroduce.add(fInputs.get(i)[2]);
            toIntroduce.add(fInputs.get(i)[3]);
            toIntroduce.add(fInputs.get(i)[4]);
            toIntroduce.add(fInputs.get(i)[5]);
            toIntroduce.add(fInputs.get(i)[6]);
            toIntroduce.add(fInputs.get(i)[7]);
            inputs.add(toIntroduce);
        }
        Function<List <Double>,Double> fct = getFunc(y1Out);
        Function <List <Double>,Double>fct2 = getFunc(y2Out);
        Function <List<Double>,Double> fct3 = getFunc(y3Out);

        return (list)-> Arrays.asList(
                fct.apply(list),
                fct2.apply(list),
                fct3.apply(list)
        );
    }
    @Override
    public List<Double> solve(List<Double> finputs) {
        population.clear();
        fInputs= Utils.getInputs();
        fOutputs=Utils.getOutputs();
        inputs=new ArrayList<>();
        Function < List<Double>,List<Double>> myFunction=solveByFunction();
        return myFunction.apply(finputs);
    }
}
