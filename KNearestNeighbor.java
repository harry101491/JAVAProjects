package supervised;

import java.io.*;
import java.util.*;


// min and max comparators
class KNNMinCompartor implements Comparator<Double>
{

	@Override
	public int compare(Double o1, Double o2) {
		return o1 > o2 ? 1 : o1 == o2 ? 0 : -1;
	}
	
}
class KNNMaxComparator implements Comparator<Double>
{
	@Override
	public int compare(Double o1, Double o2) {
		return o1 < o2 ? 1 : o1 == o2 ? 0 : -1;
	}
}
// instance class
class TrainInstance
{
	private int binaryClass; // class in which instance has been classified
	private ArrayList<Double> listOfFeatures; // list of feature values
	public TrainInstance()
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
class TestInstance
{
	private int ActualClass; // actual class value in which instance has been classified
	private int predictedClass; // class that is predicted by the classifier
	private ArrayList<Double> listOfFeatures; // list of feature values
	public TestInstance()
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
public class KNearestNeighbor 
{
	public static final int INITIAL_SIZE = 16; // Initial size of Priority queue
	
	public static final int K = 3; // value of k nearest neighbor that can be changed as required
	
	public static ArrayList<TrainInstance> listOfTrainInstances = new ArrayList<>(); // list of instances populated from the training data
	
	public static ArrayList<TestInstance> listOfTestInstances = new ArrayList<>(); // list of instances populated from the test data
	
	public static final String SEPARATOR = ","; // separator for the CSV files
	
	public static final String fileNameTrain = "trainData.txt"; // name of the train data file
	
	public static final String fileNameTest = "testData.txt"; // name of the test data file
	
	public static String className; // class Name
	
	public static ArrayList<String> listOfFeatureName = new ArrayList<>(); // the name of the features
	
	TreeMap<Double, TrainInstance> treeMap = new TreeMap<>(new KNNMinCompartor()); // the tree map for double value and train instance
	
	//PriorityQueue<Double> minQueue = new PriorityQueue<>(INITIAL_SIZE, new KNNMinCompartor());
	//PriorityQueue<Double> maxQueue = new PriorityQueue<>(INITIAL_SIZE, new KNNMaxComparator());
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
				TrainInstance newInstance = new TrainInstance();
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
			TrainInstance newInstance = listOfTrainInstances.get(i);
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
			TrainInstance newInstance = listOfTrainInstances.get(i);
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
	 * The function to normalize the data of any feature in between 0 to 1
	 * The formula of normalizing i am using is:
	 * z = (x-min(x))/(max(x)-min(x))
	 */
	public void normalizeTestData()
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
		
		for(int i=0;i<listOfTestInstances.size();i++)
		{
			TestInstance newInstance = listOfTestInstances.get(i);
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
		
		for(int i=0;i<listOfTestInstances.size();i++)
		{
			TestInstance newInstance = listOfTestInstances.get(i);
			ArrayList<Double> newList = new ArrayList<>();
			for(int j=0;j<newInstance.getListOfFeatures().size();j++)
			{
				double value = newInstance.getListOfFeatures().get(j);
				double z = (value-minArr[j])/(maxArr[j]-minArr[j]);
				newList.add(z);
			}
			listOfTestInstances.get(i).setListOfFeatures(newList);
		}
		
	}
	public void populateTestData()
	{
		try
		{
			FileReader fileReader = new FileReader(fileNameTest); // file reader object
			BufferedReader bufferedReader = new BufferedReader(fileReader); // buffered Reader
			String line; // line by line reading
			// read the first line
			line = bufferedReader.readLine();
			while((line = bufferedReader.readLine()) != null)
			{
				String featureArr[] = line.split(SEPARATOR);
				TestInstance newInstance = new TestInstance();
				for(int i=0;i<featureArr.length;i++)
				{
					if(i==0)
					{
						int bClass = Integer.valueOf(featureArr[i]);
						newInstance.setActualClass(bClass);
					}
					else
					{
						double featureValue = Double.valueOf(featureArr[i]);
						newInstance.addFeature(featureValue);
					}
				}
				listOfTestInstances.add(newInstance);
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
	public void knnClassifier()
	{
		// implementing the logic of the knn classifier
		for(int i=0;i<listOfTestInstances.size();i++)
		{
			int positive = 0;
			int negitive = 0;
			for(int j=0;j<listOfTrainInstances.size();j++)
			{
				double distance = euclideanDistance(listOfTrainInstances.get(j), listOfTestInstances.get(i));
				//System.out.println("distance is: "+ distance);
				treeMap.put(distance, listOfTrainInstances.get(j));
			}
			for(int j=0;j<K;j++)
			{
				Map.Entry<Double, TrainInstance> entry = treeMap.pollFirstEntry();
				//System.out.println("the min "+ j +"distance is:"+ entry.getKey());
				if (entry.getValue().getBinaryClass() == 1)
				{
					positive++;
				}
				else
				{
					negitive++;
				}
			}
			treeMap.clear();
			// voting
			if(positive > negitive)
			{
				listOfTestInstances.get(i).setPredictedClass(1);
			}
			else
			{
				listOfTestInstances.get(i).setPredictedClass(0);
			}
		}
	}
	public double euclideanDistance(TrainInstance train, TestInstance test)
	{
		double distance = 0;
		ArrayList<Double> trainFeatureList = train.getListOfFeatures();
		ArrayList<Double> testFeatureList = test.getListOfFeatures();
		if(trainFeatureList.size() != testFeatureList.size())
		{
			return -1;
		}
		else
		{
			for(int i = 0; i<trainFeatureList.size();i++)
			{
				distance += Math.pow((trainFeatureList.get(i)-testFeatureList.get(i)), 2);
			}
			return Math.sqrt(distance);
		}
	}
	public double accuracy()
	{
		int equal = 0;
		int total = listOfTestInstances.size();
		for(int i=0;i<listOfTestInstances.size();i++)
		{
			if(listOfTestInstances.get(i).getActualClass() == listOfTestInstances.get(i).getPredictedClass())
			{
				equal++;
			}
		}
		return ((double)equal/total)*100;
	}
	void printTrainInstance()
	{
		for(TrainInstance i : listOfTrainInstances)
		{
			System.out.println(i);
		}
	}
	public static void main(String args[])
	{
		KNearestNeighbor knn = new KNearestNeighbor();
		knn.populateTrainData();
		knn.populateTestData();
		knn.normalizeTrainData();
		knn.normalizeTestData();
		//knn.printTrainInstance();
		knn.knnClassifier();
		double value = knn.accuracy();
		System.out.println("the accuracy is:" + value);
	}
}