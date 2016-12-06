package unsupervised;

/**
 * Implementation of K-Means Clustering Algorithm
 * I am clustering both the train data and test data
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * compartor used in the tree map find the minimum of the eulidean distance
 * @author harshitpareek
 *
 */
class KMMinCompartor implements Comparator<Double>
{

	@Override
	public int compare(Double o1, Double o2) {
		return o1 > o2 ? 1 : o1 == o2 ? 0 : -1;
	}
	
}

/**
 * Train instance class for populating the train data
 * @author harshitpareek
 *
 */
class KMTrainInstance
{
	private int binaryClass; // class in which instance has been classified
	private ArrayList<Double> listOfFeatures; // list of feature values
	public KMTrainInstance()
	{
		this.listOfFeatures = new ArrayList<>();
	}
	public int getBinaryClass() {
		return binaryClass;
	}
	public void setBinaryClass(int binaryClass) {
		this.binaryClass = binaryClass;
	}
	public ArrayList<Double> getListOfFeatures() {
		return listOfFeatures;
	}
	public void setListOfFeatures(ArrayList<Double> listOfFeatures) {
		this.listOfFeatures = listOfFeatures;
	}
	public void addFeature(Double featureValue)
	{
		this.listOfFeatures.add(featureValue);
	}
	public String toString()
	{
		String str;
		str = String.format("class:%d  ", this.binaryClass);
		for(double value : listOfFeatures)
		{
			str += value + "  ";
		}
		return str;
	}
}
/**
 * Test Instance class for populating the test data
 * @author harshitpareek
 *
 */
class KMTestInstance
{
	private int ActualClass; // actual class value in which instance has been classified
	private int predictedClass; // class that is predicted by the classifier
	private ArrayList<Double> listOfFeatures; // list of feature values
	public KMTestInstance()
	{
		this.listOfFeatures = new ArrayList<>();
	}
	public int getActualClass() {
		return ActualClass;
	}
	public void setActualClass(int actualClass) {
		ActualClass = actualClass;
	}
	public int getPredictedClass() {
		return predictedClass;
	}
	public void setPredictedClass(int predictedClass) {
		this.predictedClass = predictedClass;
	}
	public ArrayList<Double> getListOfFeatures() {
		return listOfFeatures;
	}
	public void setListOfFeatures(ArrayList<Double> listOfFeatures) {
		this.listOfFeatures = listOfFeatures;
	}
	public void addFeature(Double featureValue)
	{
		this.listOfFeatures.add(featureValue);
	}
	public String toString()
	{
		String str;
		str = String.format("Acutal class:%d  predicted class: %d  ", this.ActualClass, this.predictedClass);
		for(double value : listOfFeatures)
		{
			str += value + "  ";
		}
		return str;
	}
}
/**
 * class cluster that has the data structure for maintaining the cluster
 * @author harshitpareek
 *
 */
class Cluster
{
	int noOfFeatures; // number of features in the data point
	double centroidPoints[]; // the point which the point at which centroid point of cluster lie
	ArrayList<KMTrainInstance> setOfDataPoints; // collection of data points that are in the given cluster
	public Cluster(int noOfFeatures)
	{
		centroidPoints = new double[noOfFeatures];
		for(int i=0;i<noOfFeatures;i++)
		{
			centroidPoints[i] = Math.random();
		}
		setOfDataPoints = new ArrayList<>();
		this.noOfFeatures = noOfFeatures;
	}
	// getters and setters methods
	public double[] getCentroidPoints() {
		return centroidPoints;
	}
	public int getNoOfFeatures() {
		return noOfFeatures;
	}
	public void setNoOfFeatures(int noOfFeatures) {
		this.noOfFeatures = noOfFeatures;
	}
	public ArrayList<KMTrainInstance> getSetOfDataPoints() {
		return setOfDataPoints;
	}
	public void setSetOfDataPoints(ArrayList<KMTrainInstance> setOfDataPoints) {
		this.setOfDataPoints = setOfDataPoints;
	}
	public void setCentroidPoints(double[] centroidPoints) {
		this.centroidPoints = centroidPoints;
	}
	/** 
	 * add the data point to the setOfDataPoints
	 * @param instance instance that we want to add in to the cluster
	 */
	public void addDataPointInSet(KMTrainInstance instance)
	{
		setOfDataPoints.add(instance);
	}
	/**
	 * print method for the printing the centroid points for the cluster
	 */
	public void printCentroidPoints()
	{
		// pring the centroid points
		System.out.println();
		System.out.println("the centroid point for the given cluster is: ");
		for(int i=0;i<noOfFeatures;i++)
		{
			System.out.print(this.centroidPoints[i] + "   ");
		}
		System.out.println();
		System.out.println("the length for the set is: "+ this.setOfDataPoints.size());
		System.out.println();
	}
}
/**
 * class of KMean Clustering
 * @author harshitpareek
 *
 */
public class KMeansClustering 
{
	public static final int K = 2;    // no of cluster
	public static final int ITERATION = 100; // untill how many times we have to run the clustering if it not converges 
	public static ArrayList<KMTrainInstance> listOfTrainInstances = new ArrayList<>();   // list of instances populated from the training data
	//public static ArrayList<KMTestInstance> listOfTestInstances = new ArrayList<>();    // list of instances populated from the test data
	public static final String SEPARATOR = ",";   // separator for the CSV files
	public static final String fileNameTrain = "trainData.txt";  // name of the train data file
	//public static final String fileNameTest = "testData.txt";   // name of the test data file
	public static String className;   // class Name
	public static ArrayList<String> listOfFeatureName = new ArrayList<>();  // the name of the features
	public static ArrayList<Cluster> listOfClusters = new ArrayList<>(); // list of cluster that are there according to the K
	public static TreeMap<Double, Cluster> minDisMaping = new TreeMap<>(new KMMinCompartor());
	/**
	 * populate the train data
	 */
	public void populateTrainData()
	{
		try
		{
			FileReader fileReader = new FileReader(fileNameTrain); // file reader object
			BufferedReader bufferedReader = new BufferedReader(fileReader); // buffered Reader
			String line; // line by line reading
			// read the first line
			line = bufferedReader.readLine();
			String nameArr[] = line.split(SEPARATOR);
			for(int i=0;i<nameArr.length;i++)
			{
				if(i==0)
				{
					className = nameArr[i];
				}
				else
				{
					listOfFeatureName.add(nameArr[i]);
				}
			}
			while((line = bufferedReader.readLine()) != null)
			{
				String featureArr[] = line.split(SEPARATOR);
				KMTrainInstance newInstance = new KMTrainInstance();
				for(int i=0;i<featureArr.length;i++)
				{
					if(i==0)
					{
						int bClass = Integer.valueOf(featureArr[i]);
						newInstance.setBinaryClass(bClass);
					}
					else
					{
						double featureValue = Double.valueOf(featureArr[i]);
						newInstance.addFeature(featureValue);
					}
				}
				listOfTrainInstances.add(newInstance);
			}
			bufferedReader.close();
			
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * The function to normalize the data of any feature in between 0 to 1
	 * The formula of normalizing i am using is:
	 * z = (x-min(x))/(max(x)-min(x))
	 */
	public void normalizeTrainData()
	{
		// no of features
		int totalNoFeatures = listOfFeatureName.size();
		// the min value and max value for every feature values that are there
		//ArrayList<Double> minValueFeature = new ArrayList<>(totalNoFeatures);
		double minArr[] = new double[totalNoFeatures];
		double maxArr[]	= new double[totalNoFeatures];	
		//ArrayList<Double> maxValueFeature = new ArrayList<>(totalNoFeatures);
		
		for (int i=0;i<totalNoFeatures;i++)
		{
			minArr[i] = Double.MAX_VALUE;
			maxArr[i] = Double.MIN_VALUE;
		}
		
		for(int i=0;i<listOfTrainInstances.size();i++)
		{
			KMTrainInstance newInstance = listOfTrainInstances.get(i);
			for(int j=0;j<newInstance.getListOfFeatures().size();j++)
			{
				double value = newInstance.getListOfFeatures().get(j);
				if(minArr[j] > value)
				{
					minArr[j] = value;
				}
				if(maxArr[j] < value)
				{
					maxArr[j] = value;
				}
			}
		}
		
		for(int i=0;i<listOfTrainInstances.size();i++)
		{
			KMTrainInstance newInstance = listOfTrainInstances.get(i);
			ArrayList<Double> newList = new ArrayList<>();
			for(int j=0;j<newInstance.getListOfFeatures().size();j++)
			{
				double value = newInstance.getListOfFeatures().get(j);
				double z = (value-minArr[j])/(maxArr[j]-minArr[j]);
				newList.add(z);
			}
			listOfTrainInstances.get(i).setListOfFeatures(newList);
		}
		
	}
	/**
	 * print the data
	 */
	public void printData()
	{
		for(KMTrainInstance i : listOfTrainInstances)
		{
			System.out.println(i);
		}
	}
	/**
	 * clustering the data on the basis of euclidean distance
	 */
	public void clusteringData()
	{
		// initalize the clusters with random values
		for(int i=0;i<K;i++)
		{
			Cluster newCluster = new Cluster(listOfFeatureName.size());
			listOfClusters.add(newCluster);
		}
		int counter = ITERATION;
		int flag = -1;
		while(counter > 0)
		{
			
			// initialize the length values for the covergence check
			int lengthSet[] = new int[K];
			for(int i=0;i<K;i++)
			{
				Cluster newCluster = listOfClusters.get(i);
				lengthSet[i] = newCluster.getSetOfDataPoints().size();
			}
			
			// clearing the arrayList of the cluster for the new iteration
			for(int i=0;i<K;i++)
			{
				Cluster newCluster = listOfClusters.get(i);
				newCluster.getSetOfDataPoints().clear();
			}
			
			// for every data point find which cluster is at min distance
			for(int i=0;i<listOfTrainInstances.size();i++)
			{
				KMTrainInstance newInstance = listOfTrainInstances.get(i);
				for(int j=0;j<listOfClusters.size();j++)
				{
					Cluster newCluster = listOfClusters.get(j);
					double distance = evalEuclideanDistance(newCluster, newInstance);
					minDisMaping.put(distance, newCluster);
				}
				Cluster minCluster = minDisMaping.pollFirstEntry().getValue();
				minDisMaping.clear();
				minCluster.addDataPointInSet(newInstance);
			}
			
			// convergence check
			for(int i=0;i<K;i++)
			{
				Cluster newCluster = listOfClusters.get(i);
				if(lengthSet[i] == newCluster.getSetOfDataPoints().size())
				{
					flag = 1;
				}
				else
				{
					flag = 0;
				}
			}
			if(flag == 1)
			{
				break;
			}
			
			// change the centroid points of each of each of the cluster
			for(int i=0;i<K;i++)
			{
				Cluster newCluster = listOfClusters.get(i);
				int length = newCluster.getNoOfFeatures();
				int setLength = newCluster.getSetOfDataPoints().size();
				
				double sum[] = new double[length];
				for(int j=0;j<setLength;j++)
				{
					KMTrainInstance instance = newCluster.getSetOfDataPoints().get(j);
					for(int k=0;k<length;k++)
					{
						sum[k] += instance.getListOfFeatures().get(k);
					}
				}
				for(int p=0;p<length;p++)
				{
					newCluster.centroidPoints[p] = sum[p]/setLength;
				}	
			}
			counter--;
		}
		if(flag == 1)
		{
			System.out.println("the data has converged at:"+(ITERATION-counter));
			printClusters();
		}
		else
		{
			System.out.println("the data has not converged");
		}
	}
	/**
	 * the function to return the euclidean distance
	 * @param c cluster from which we are trying to find the distance
	 * @param instance distance from a perticular data point
	 * @return euclidean distance
	 */
	public double evalEuclideanDistance(Cluster c, KMTrainInstance instance)
	{
		double distance = 0;
		int length = c.getNoOfFeatures();
		for(int i=0;i<length;i++)
		{
			distance += Math.pow((instance.getListOfFeatures().get(i) - c.getCentroidPoints()[i]), 2); // distance between the centroid point and data point
		}
		return Math.sqrt(distance); // returning the squre root of that distance
	}
	/**
	 * print the clusters and their set of data points
	 */
	public void printClusters()
	{
		for(int i=0;i<K;i++)
		{
			Cluster newCluster = listOfClusters.get(i);
			//System.out.print("the cluster is:"+(i+1)+"is");
			System.out.println("the printing of the data for the "+ (i+1) +" cluster :");
			newCluster.printCentroidPoints();
			System.out.println();
		}
	}
	/**
	 * the main function
	 * @param args command line argument
	 */
	public static void main(String args[])
	{
		KMeansClustering clustering = new KMeansClustering();
		clustering.populateTrainData();
		clustering.normalizeTrainData();
		clustering.clusteringData();
		//clustering.printClusters();
		//clustering.printData();
	}
}