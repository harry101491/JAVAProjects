package unsupervised;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * The implimentation of the Agglomerative Hierarchical Clustering by using the distance as the taking the average of all the points in the 
 * current set of given cluster.
 */


/**
 * compartor used in the find the minimum of the eulidean distance
 * @author harshitpareek
 *
 */
class AHMinCompartor implements Comparator<Double>
{

	@Override
	public int compare(Double o1, Double o2) {
		return o1 > o2 ? 1 : o1 == o2 ? 0 : -1;
	}
	
}
class AHTrainInstance
{
	private int binaryClass; // class in which instance has been classified
	private ArrayList<Double> listOfFeatures; // list of feature values
	public AHTrainInstance()
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
class AHCluster 
{
	static int noOfFeatures; // number of features in the data
	double centroidPoint[]; // the centroid point in the cluster
	ArrayList<AHTrainInstance> setOfDataPoints; // collection of data points that are in the given cluster
	
	/**
	 * this cluster is different from the K means as N number of clusters will be formed as compared to K clustered in the K-means
	 * @param newInstance the instance that is the only data point in the cluster
	 */
	public AHCluster(AHTrainInstance newInstance)
	{
		// the number of features
		AHCluster.noOfFeatures = newInstance.getListOfFeatures().size();
		this.centroidPoint = new double[AHCluster.noOfFeatures];
		this.setOfDataPoints = new ArrayList<>();
		// initializing the centroid point to be the only instance in the set
		for(int i=0;i<AHCluster.noOfFeatures;i++)
		{
			this.centroidPoint[i] = newInstance.getListOfFeatures().get(i);
		}
		// the set of Data points only contain the one instance
		this.setOfDataPoints.add(newInstance);
	}
	// implementation of the equals and hashcode function
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(centroidPoint);
		result = prime * result + ((setOfDataPoints == null) ? 0 : setOfDataPoints.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AHCluster other = (AHCluster) obj;
		if (!Arrays.equals(centroidPoint, other.centroidPoint))
			return false;
		if (setOfDataPoints == null) {
			if (other.setOfDataPoints != null)
				return false;
		} else if (!setOfDataPoints.equals(other.setOfDataPoints))
			return false;
		return true;
	}
	// getters and setters methods 
	public double[] getCentroidPoint() {
		return centroidPoint;
	}
	public void setCentroidPoint(double[] centroidPoint) {
		this.centroidPoint = centroidPoint;
	}
	public ArrayList<AHTrainInstance> getSetOfDataPoints() {
		return setOfDataPoints;
	}
	public void setSetOfDataPoints(ArrayList<AHTrainInstance> setOfDataPoints) {
		this.setOfDataPoints = setOfDataPoints;
	}
	/**
	 * add the newCluster in to given cluster
	 * @param newCluster newCluster that is to be added in to the this cluster
	 */
	public void addCluster(AHCluster newCluster)
	{
		// changing the cluster centroid with the new centroid
		for(int i=0;i<AHCluster.noOfFeatures;i++)
		{
			this.centroidPoint[i] = (this.centroidPoint[i] + newCluster.centroidPoint[i])/2;
		}
		// adding the data points from the newCluster to current cluster
		for(int i=0;i<newCluster.setOfDataPoints.size();i++)
		{
			AHTrainInstance instance = newCluster.setOfDataPoints.get(i);
			this.setOfDataPoints.add(instance);
		}
	}
	/**
	 * print the cluster
	 */
	public void printCluster()
	{
		for(int i=0;i<noOfFeatures;i++)
		{
			System.out.print(this.centroidPoint[i] + "   ");
		}
		System.out.println("the length for the set is: "+ this.setOfDataPoints.size());
		System.out.println();
	}
}
/**
 * Implementation of Agglomerative Hierarchical Clusting algorithm
 * @author harshitpareek
 *
 */
public class AgglomerativeHierarchicalClustering 
{
	public static final int K = 2; // number of cluster that you want at the convergence
	public static ArrayList<AHTrainInstance> listOfTrainInstances = new ArrayList<>();   // list of instances populated from the training data
	public static final String SEPARATOR = ",";   // separator for the CSV files
	public static final String fileNameTrain = "trainData.txt";  // name of the train data file
	public static String className;   // class Name
	public static ArrayList<String> listOfFeatureName = new ArrayList<>();  // the name of the features
	public static ArrayList<AHCluster> listOfClusters = new ArrayList<>(); // list of cluster that are there according to the K
	public static TreeMap<Double, AHCluster> minMapTree = new TreeMap<>(new AHMinCompartor()); // tree map between the double and AHCluster
	
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
				AHTrainInstance newInstance = new AHTrainInstance();
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
			AHTrainInstance newInstance = listOfTrainInstances.get(i);
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
			AHTrainInstance newInstance = listOfTrainInstances.get(i);
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
	 * populate the list of clusters where every cluster is a data point
	 */
	public void populateListOfCluster()
	{
		listOfClusters.clear();
		for(int i=0;i<listOfTrainInstances.size();i++)
		{
			AHTrainInstance newInstance = listOfTrainInstances.get(i);
			AHCluster newCluster = new AHCluster(newInstance);
			listOfClusters.add(newCluster);
		}
	}
	/**
	 * clustering the data points untill the number of clusters remains to be K(2) for this case
	 */
	public void clustering()
	{
		populateListOfCluster(); // populating the data
		// running for one time to see how much clusters get added and what the algorithm unfolds
		// condition for covergence as we are trying to stop the clustering for two clusters only
		while(listOfClusters.size() != K)
		{
			for(int i=0;i<listOfClusters.size();i++)
			{
				AHCluster cluster1 = listOfClusters.get(i); // getting the first cluster
				for(int j=0;j<listOfClusters.size();j++)
				{
					AHCluster cluster2 = listOfClusters.get(j); // getting the second cluster
					if(!cluster1.equals(cluster2))
					{
						double distance = evalDistance(cluster1, cluster2); // find the distance between two cluster
						minMapTree.put(distance, cluster2); // put in the tree Map
					}
				}
				AHCluster minCluster = minMapTree.pollFirstEntry().getValue(); // remove the first entry
				minMapTree.clear(); // clear the tree map
				addClusters(cluster1, minCluster); // add two clusters
			}
		}
	}
	/**
	 * add two clusters given as cluster 1 and cluster 2
	 * @param cluster1 the first cluster
	 * @param cluster2 the second cluster
	 */
	public void addClusters(AHCluster cluster1, AHCluster cluster2)
	{
		cluster1.addCluster(cluster2);
		listOfClusters.remove(cluster2);
	}
	/**
	 * evaluate the distance between two clusters by their respective centroids.
	 * I am using the method in which it find the minimum distance between centroids of two clusters. Other methods can be used 
	 * like farthest distance and average methods.
	 * @param cluster1 first cluster
	 * @param cluster2 second cluster
	 * @return distance between the centroids of two clusters
	 */
	public double evalDistance(AHCluster cluster1, AHCluster cluster2)
	{
		double sum = 0.0; // distance calculated between the centroid points between two clusters
		int length = AHCluster.noOfFeatures;
		for(int i=0;i<length;i++)
		{
			sum += Math.pow((cluster1.centroidPoint[i]-cluster2.centroidPoint[i]), 2);
		}
		return Math.sqrt(sum);
	}
	/**
	 * print the clusters present in the list of clusters
	 */
	public void printClusters()
	{
		System.out.println("The clusters in the data points");
		System.out.println("the value of length is: "+ listOfClusters.size());
		for(int i=0;i<listOfClusters.size();i++)
		{
			AHCluster newCluster = listOfClusters.get(i);
			System.out.println("the cluster at the "+(i+1)+" th position is:");
			newCluster.printCluster();
		}
	}
	/**
	 * Number of instances that are positive and Number of instances that are negitive
	 * @return number of positive instances
	 */
	public double noOfPositives()
	{
		int positives = 0;
		for(int i=0;i<listOfTrainInstances.size();i++)
		{
			AHTrainInstance instance = listOfTrainInstances.get(i);
			if(instance.getBinaryClass() == 1)
			{
				positives++;
			}
		}
		return positives;
	}
	/**
	 * In this method to find the accuracy I have first calculated the number of positive examples in the train data set
	 * and the number of negative in the train dataset and then I find the max length in the list of cluster set data points.
	 * then find the accuracy which is the 100-error.
	 * @return returns the accuracy
	 */
	public double accuracy()
	{
		double positives = noOfPositives();
		double negatives = (double)listOfTrainInstances.size() - positives;
		double maxValue = (double)Integer.MIN_VALUE;
		
		if(listOfClusters.size() == K)
		{
			for(int i=0;i<listOfClusters.size();i++)
			{
				AHCluster newCluster = listOfClusters.get(i);
				if(maxValue < newCluster.setOfDataPoints.size())
				{
					maxValue = newCluster.setOfDataPoints.size();
				}
			}
		}
		double error = 0;
		
		if(positives > negatives)
		{
			error = ((positives - maxValue)/positives) * 100;
		}
		else
		{
			error = ((negatives - maxValue)/negatives) * 100;
		}
		
		if(error > 0)
		{
			return (100-error);
		}
		else
		{
			return (error-100);
		}
	}
	/**
	 * print the data
	 */
	public void printData()
	{
		for(AHTrainInstance i : listOfTrainInstances)
		{
			System.out.println(i);
		}
	}

	/**
	 * main function
	 * @param args command line arguments
	 */
	public static void main(String args[])
	{
		AgglomerativeHierarchicalClustering clustering = new AgglomerativeHierarchicalClustering();
		clustering.populateTrainData();
		clustering.normalizeTrainData();
		clustering.clustering();
		double result = clustering.accuracy();
		System.out.println("the value of accuracy is: "+result);
		clustering.printClusters();
		//clustering.populateListOfCluster();
		//clustering.printClusters();
		//clustering.printData();
	}
}