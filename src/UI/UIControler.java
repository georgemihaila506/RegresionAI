package UI;

import MachineLearning.AbstractSolve;
import MachineLearning.GradientDescent;
import MachineLearning.LeastSquares;
import MachineLearning.Evo;
import Utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UIControler implements Initializable {
    @FXML
    TextField x1;
    @FXML
    TextField x2;
    @FXML
    TextField x3;
    @FXML
    TextField x4;
    @FXML
    TextField x5;
    @FXML
    TextField x6;
    @FXML
    TextField x7;
    @FXML
    Label y1;
    @FXML
    Label y2;
    @FXML
    Label y3;
    private Evo ssga;
    private LeastSquares leastSquares;
    private GradientDescent gradientDescent;

    private List<Double> getFromTextField()
    {
        List<Double> toReturn=new ArrayList<>();
        toReturn.add(Double.parseDouble(x1.getText()));
        toReturn.add(Double.parseDouble(x2.getText()));
        toReturn.add(Double.parseDouble(x3.getText()));
        toReturn.add(Double.parseDouble(x4.getText()));
        toReturn.add(Double.parseDouble(x5.getText()));
        toReturn.add(Double.parseDouble(x6.getText()));
        toReturn.add(Double.parseDouble(x7.getText()));
        return toReturn;
    }
    private void solveB(AbstractSolve abstractSolve)
    {
        List<double[]> inputs=Utils.getInputsR();
        List<double[]> outputs=Utils.getOutputsR();
        for(int i=0;i<inputs.size();i++)
        {
            List<Double> forInputs=new ArrayList<>();
            for(int j=0;j<7;j++)
            {
                forInputs.add(inputs.get(i)[j]);
            }
            abstractSolve.solve(forInputs).forEach(x-> System.out.print(x+" "));
            System.out.print("Trebuia:"+outputs.get(i)[0]+" "+outputs.get(i)[1]+" "+outputs.get(i)[2]);
            System.out.println();
        }
    }
    @FXML
    void handleSSGA()
    {
        if(ssga==null) {
            ssga = new Evo();
            ssga.setLeftSide(-1000d);

            ssga.setRightSide(1000d);

            ssga.setPopSize(10000);
        }
        solveB(ssga);
        List<Double> toPut=ssga.solve(getFromTextField());
        y1.setText(String.valueOf(toPut.get(0)).substring(0,5));
        y2.setText(String.valueOf(toPut.get(1)).substring(0,5));
        y3.setText(String.valueOf(toPut.get(2)).substring(0,5));
    }
    @FXML
    void handleS()
    {
        if(leastSquares==null) {
            leastSquares = new LeastSquares();
        }
        solveB(leastSquares);
        List<Double> toPut=leastSquares.solve(getFromTextField());
        y1.setText(String.valueOf(toPut.get(0)).substring(0,5));
        y2.setText(String.valueOf(toPut.get(1)).substring(0,5));
        y3.setText(String.valueOf(toPut.get(2)).substring(0,5));
    }
    @FXML
    void handleG()
    {
        if(gradientDescent==null)
        {
            gradientDescent=new GradientDescent();
        }
        solveB(gradientDescent);
        List<Double> toPut=gradientDescent.solve(getFromTextField());
        y1.setText(String.valueOf(toPut.get(0)).substring(0,5));
        y2.setText(String.valueOf(toPut.get(1)).substring(0,5));
        y3.setText(String.valueOf(toPut.get(2)).substring(0,5));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        x1.setText("0");
        x2.setText("0");
        x3.setText("0");
        x4.setText("0");
        x5.setText("0");
        x6.setText("0");
        x7.setText("0");
    }
}
