package weka_cluster_test;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;;

public class WekaClusterTest {

	Instances cpu = null;
	SimpleKMeans kmeans;

	public void loadArff(String arffInput) {
		// DatabaseLoader source = null;
		DataSource source = null;
		try {
			source = new DataSource(arffInput);
			cpu = source.getDataSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void clusterData() {
		kmeans = new SimpleKMeans();
		kmeans.setSeed(10);

		try {
			kmeans.setPreserveInstancesOrder(true);
			kmeans.setNumClusters(10);
			kmeans.buildClusterer(cpu);
			int[] assignments = kmeans.getAssignments();
			int i = 0;
			for (int clusterNum : assignments) {
				System.out.printf("Instace %d -> Cluster %d\n", i, clusterNum);
				i++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		WekaClusterTest test = new WekaClusterTest();
		test.loadArff("C:\\Users\\RAFIQ\\Desktop\\data.arff");
		test.clusterData();
	}
}
