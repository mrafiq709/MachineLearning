package java_machine_learning;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.distance.PearsonCorrelationCoefficient;
import net.sf.javaml.featureselection.ranking.RecursiveFeatureEliminationSVM;
import net.sf.javaml.featureselection.scoring.GainRatio;
import net.sf.javaml.featureselection.subset.GreedyForwardSelection;
import net.sf.javaml.tools.data.FileHandler;

public class JavaMachineLearning {

	public static void main(String[] args) throws IOException {
		Dataset data = FileHandler.loadDataset(new File("iris.data"), 4, ",");
		// System.out.println(data);

		FileHandler.exportDataset(data, new File("javaml-output.txt"));
		data = FileHandler.loadDataset(new File("javaml-output.txt"), 0, "\t");
		// System.out.println(data);

		Clusterer km = new KMeans();
		Dataset[] clusters = km.cluster(data);

		/*
		 * for (Dataset cluster : clusters) { System.out.println("Cluster: " +
		 * cluster); }
		 */

		ClusterEvaluation sse = new SumOfSquaredErrors();
		double score = sse.score(clusters);
		// System.out.println(score);

		Classifier knn = new KNearestNeighbors(5);
		knn.buildClassifier(data);

		CrossValidation cv = new CrossValidation(knn);
		Map<Object, PerformanceMeasure> cvEvalution = cv.crossValidation(data);
		// System.out.println(cvEvalution);

		Dataset testData = FileHandler.loadDataset(new File("iris.data"), 4, ",");
		Map<Object, PerformanceMeasure> testEvalution = EvaluateDataset.testDataset(knn, testData);
		/*
		 * for (Object classVariable : testEvalution.keySet()) {
		 * System.out.println(classVariable + "Class has " +
		 * testEvalution.get(classVariable).getAccuracy()); }
		 */

		GainRatio gainRatio = new GainRatio();
		gainRatio.build(data);
		/*
		 * for (int i = 0; i < gainRatio.noAttributes(); i++) {
		 * System.out.println(gainRatio.score(i)); }
		 */

		RecursiveFeatureEliminationSVM featureRank = new RecursiveFeatureEliminationSVM(0.2);
		featureRank.build(data);

		for (int i = 0; i < featureRank.noAttributes(); i++) {
			System.out.println(featureRank.rank(i));
		}

		GreedyForwardSelection featureSelection = new GreedyForwardSelection(5, new PearsonCorrelationCoefficient());
		featureSelection.build(data);
		System.out.println(featureSelection.selectedAttributes());
	}

}
