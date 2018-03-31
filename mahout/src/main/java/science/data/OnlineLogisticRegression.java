package science.data;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import org.apache.mahout.classifier.sgd.CsvRecordFactory;
import org.apache.mahout.classifier.sgd.LogisticModelParameters;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;

public class OnlineLogisticRegression {

	public static void main(String[] args) throws IOException {
		String inputFile = "data/weather.numeric.csv";
		String outputFile = "model/model";
		java.util.List<String> predictorList = Arrays.asList("outlook", "temparature", "humidity", "windy", "play");
		java.util.List<String> typeList = Arrays.asList("w", "n", "n", "w", "w");
		/*
		 * w = denotes Nominal Features, n = denotes Numeric Features
		 */
		LogisticModelParameters lmp = new LogisticModelParameters();
		lmp.setTargetVariable("play"); // class Variable "play"
		lmp.setMaxTargetCategories(2); // 2 bcz [yes/no]
		lmp.setNumFeatures(4); // Remaining 4 Features
		lmp.setUseBias(false);
		lmp.setTypeMap(predictorList, typeList);
		lmp.setLearningRate(0.5);

		int passes = 50;
		org.apache.mahout.classifier.sgd.OnlineLogisticRegression olr;
		CsvRecordFactory csv = lmp.getCsvRecordFactory();
		olr = lmp.createRegression();

		int k = 0;
		for (int pass = 0; pass < passes; pass++) {
			BufferedReader in = new BufferedReader(new FileReader(inputFile));
			csv.firstLine(in.readLine()); // Header of the Features

			String line = in.readLine(); // Seceond Line of the File, that is
											// features
			while (line != null) {
				System.out.println(line);
				Vector input = new RandomAccessSparseVector(lmp.getNumFeatures());
				int targetValue = csv.processLine(line, input);
				olr.train(targetValue, input);
				line = in.readLine();

			}
			in.close();
		}

		OutputStream modelOutput = new FileOutputStream(outputFile);
		try {
			lmp.saveTo(modelOutput);
		} finally {
			modelOutput.close();
		}

	}

}
