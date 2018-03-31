package linear_regression;

import java.io.BufferedReader;
import java.io.FileReader;

import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;

public class Linear {

	public static void main(String[] args) throws Exception {

		BufferedReader buffer = null;
		buffer = new BufferedReader(new FileReader("D:/linear-train.arff"));

		Instances train = new Instances(buffer);
		train.setClassIndex(train.numAttributes() - 1);

		buffer.close();

		LinearRegression ln = new LinearRegression();
		ln.buildClassifier(train);

		System.out.println(ln);

	}
}
