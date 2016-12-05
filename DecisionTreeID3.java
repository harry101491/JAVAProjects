package supervised;

import java.io.*;
import java.util.*;

/**
 * Decision Tree Supervised Learning Algorithm(ID3)
 * Entropy Formula = - sigma(P(x(i))log(p(x(i))));
 * Information Gain formula G(A) = E(S) - sigma(E(child)) 
 * I am using the test and train provided for the assignment. It is able to classify the data into class 0 which is Republican and class 1 which is Democrat 
 * @author harshitpareek
 */

class MinComparison implements Comparator<Double>
{

	@Override
	public int compare(Double o1, Double o2) {
		return o1 > o2 ? 1 : o1 == o2 ? 0 : -1;
	}
	
}
// train instance class
class DTTrainInstance
{
	private int binaryClass; // class in which instance has been classified
	private ArrayList<Double> listOfFeatureValues; // list of feature values
	public DTTrainInstance()
	{
		this.listOfFeatureValues = new ArrayList<>();
	}
	public int getBinaryClass() {
		return binaryClass;
	}
	public void setBinaryClass(int binaryClass) {
		this.binaryClass = binaryClass;
	}
	public ArrayList<Double> getListOfFeatures() {
		return listOfFeatureValues;
	}
	public void setListOfFeatures(ArrayList<Double> listOfFeatures) {
		this.listOfFeatureValues = listOfFeatures;
	}
	public void addFeature(Double featureValue)
	{
		this.listOfFeatureValues.add(featureValue);
	}
	public String toString()
	{
		String str;
		str = String.format("class:%d  ", this.binaryClass);
		for(double value : listOfFeatureValues)
		{
			str += value + "  ";
		}
		return str;
	}
}
// test instance class
class DTTestInstance
{
	private int ActualClass; // actual class value in which instance has been classified
	private int predictedClass; // class that is predicted by the classifier
	private ArrayList<Double> listOfFeatureValues; // list of feature values
	public DTTestInstance()
	{
		this.listOfFeatureValues = new ArrayList<>();
		this.predictedClass = -1;
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
		return listOfFeatureValues;
	}
	public void setListOfFeatures(ArrayList<Double> listOfFeatures) {
		this.listOfFeatureValues = listOfFeatures;
	}
	public void addFeature(Double featureValue)
	{
		this.listOfFeatureValues.add(featureValue);
	}
	public String toString()
	{
		String str;
		str = String.format("Acutal class:%d  predicted class: %d  ", this.ActualClass, this.predictedClass);
		for(double value : listOfFeatureValues)
		{
			str += value + "  ";
		}
		return str;
	}
}

class DTNode
{
	String featureName; // name of the feature
	int featureNumber; // number assigned for that feature between 0-8 for this data set
	ArrayList<DTNode> listOfChildren; // list of child nodes for the current node
	DTNode parent; // parent of current node
	double entropyOfSet; // entropy for the current Set inside this node
	Set<DTTrainInstance> setOfInstance; // set of instances that are for a node
	int noOfPositives; // number of positives
	int noOfNegatives; // number of negatives
	
	public DTNode()
	{
		this.listOfChildren = new ArrayList<>();
		this.parent = null;
		this.entropyOfSet = 0;
		this.setOfInstance = null;
		this.noOfPositives = 0;
		this.noOfNegatives = 0;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public int getFeatureNumber() {
		return featureNumber;
	}

	public void setFeatureNumber(int featureNumber) {
		this.featureNumber = featureNumber;
	}

	public ArrayList<DTNode> getListOfChildren() {
		return listOfChildren;
	}

	public void setListOfChildren(ArrayList<DTNode> listOfChildren) {
		this.listOfChildren = listOfChildren;
	}

	public DTNode getParent() {
		return parent;
	}

	public void setParent(DTNode parent) {
		this.parent = parent;
	}

	public double getEntropyOfSet() {
		return entropyOfSet;
	}

	public void setEntropyOfSet(double entropyOfSet) {
		this.entropyOfSet = entropyOfSet;
	}

	public Set<DTTrainInstance> getSetOfInstance() {
		return setOfInstance;
	}

	public void setSetOfInstance(Set<DTTrainInstance> setOfInstance) {
		this.setOfInstance = setOfInstance;
	}

	public int getNoOfPositives() {
		return noOfPositives;
	}

	public void setNoOfPositives(int noOfPositives) {
		this.noOfPositives = noOfPositives;
	}

	public int getNoOfNegatives() {
		return noOfNegatives;
	}

	public void setNoOfNegatives(int noOfNegatives) {
		this.noOfNegatives = noOfNegatives;
	}
	/**
	 * compute the positive in the set
	 * @return number of positive
	 */
	public void computePlusMinus()
	{
		for(DTTrainInstance instance : this.setOfInstance)
		{
			if(instance.getBinaryClass() == 1)
			{
				this.noOfPositives++;
			}
			else
			{
				this.noOfNegatives++;
			}
		}
	}
}

public class DecisionTreeID3
{
	public static final int INITIAL_SIZE = 16; // Initial size of Priority queue
	public static final double LOG_2 = Math.log(2);
	public static ArrayList<DTTrainInstance> listOfTrainInstances = new ArrayList<>(); // list of instances populated from the training data
	public static ArrayList<DTTestInstance> listOfTestInstances = new ArrayList<>(); // list of instances populated from the test data
	public static final String SEPARATOR = ","; // separator for the CSV files
	public static final String fileNameTrain = "trainData.txt"; // name of the train data file
	public static final String fileNameTest = "testData.txt"; // name of the test data file
	public static String className; // class Name
	public static ArrayList<String> listOfFeatureName = new ArrayList<>(); // the name of the features
	public static Map<Integer, Double> mapingOfTheta = new HashMap<>();
	public static double ENTROPY_OF_SET; // entropy of whole data set
	public static Set<DTTrainInstance> wholeSet = new HashSet<>();
	/**
	 * populate the train data from the text file
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
				DTTrainInstance newInstance = new DTTrainInstance();
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
			DTTrainInstance newInstance = listOfTrainInstances.get(i);
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
			DTTrainInstance newInstance = listOfTrainInstances.get(i);
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
	 * populate test data
	 */
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
				DTTestInstance newInstance = new DTTestInstance();
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
			DTTestInstance newInstance = listOfTestInstances.get(i);
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
			DTTestInstance newInstance = listOfTestInstances.get(i);
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
	/**
	 * finding theta for the every feature as the median of the test values after sorting
	 */
	public void findingTheataForFeatures()
	{
		Map<Integer, ArrayList<Double>> mapingOfQueue = new HashMap<Integer, ArrayList<Double>>();
		for(int i=0;i<listOfFeatureName.size();i++)
		{
			mapingOfQueue.put(i, new ArrayList<>());
		}
		
		for(int i=0;i<listOfTrainInstances.size();i++)
		{
			DTTrainInstance newInstance = listOfTrainInstances.get(i);
			for(int j=0;j<newInstance.getListOfFeatures().size();j++)
			{
				double value = newInstance.getListOfFeatures().get(j);
				mapingOfQueue.get(j).add(value);
			}
		}
		
		for(Map.Entry<Integer, ArrayList<Double>> entry : mapingOfQueue.entrySet())
		{
			int length = listOfTrainInstances.size();
			ArrayList<Double> list = entry.getValue();
			Collections.sort(list);
			double meanValue;
			if(length % 2 == 0)
			{
				meanValue = (list.get(length/2) + list.get((length/2) - 1)) / 2;
			}
			else
			{
				meanValue = list.get(length/2);
			}
			mapingOfTheta.put(entry.getKey(), meanValue);
		}
	}
	public void printMap()
	{
		for(Map.Entry<Integer, Double> entry : mapingOfTheta.entrySet())
		{
			for(int i=0;i<listOfFeatureName.size();i++)
			{
				if(entry.getKey() == i)
				{
					System.out.println("the threshold value for feature Name "+listOfFeatureName.get(i)+" is: "+entry.getValue());
				}
			}
		}
	}
	/**
	 * general entropy function for the set given
	 * @param set the set of instances for which we have to find the entropy
	 * @return returns the entropy for that set
	 */
	public double entropyOfSet(Set<DTTrainInstance> set)
	{
		int totalNumberOfInstance = set.size();
		//System.out.println("the value of total no instances "+ totalNumberOfInstance);
		int positives = findPositiveInstances(set);
		//System.out.println("the number of positives are:"+ positives);
		int negatives = totalNumberOfInstance - positives;
		//System.out.println("the number of negatives are:"+ negatives);
		double pPositive = ((double)positives)/totalNumberOfInstance;
		//System.out.println("the value of + probability is:"+ pPositive);
		double pNegative = ((double)negatives)/totalNumberOfInstance;
		//System.out.println("the value of - probability is:"+ pNegative);
		double positive = -(pPositive*(Math.log(pPositive)/LOG_2));
		//System.out.println("the value of + log is:"+ positive);
		double negative = -(pNegative*(Math.log(pNegative)/LOG_2));
		//System.out.println("the value of - log is:"+ negative);
		return (positive+negative);
	}
	public void entropyOfWholeSet(Set<DTTrainInstance> set)
	{
		set = wholeSet;
		for(int i=0;i<listOfTrainInstances.size();i++)
		{
			DTTrainInstance newInstance = listOfTrainInstances.get(i);
			set.add(newInstance);
		}
		ENTROPY_OF_SET = entropyOfSet(set);
	}
	/**
	 * the function to find the number of positives
	 * @param set for a given set it return the no positive value in it
	 * @return returns the count of positives
	 */
	public int findPositiveInstances(Set<DTTrainInstance> set)
	{
		if(set.isEmpty())
		{
			return -1;
		}
		else
		{
			int counter = 0;
			for(DTTrainInstance newInstance : set)
			{
				if(newInstance.getBinaryClass() == 1)
				{
					counter++;
				}
			}
			return counter;
		}
	}
	public int findTheBestFeature(ArrayList<DTNode> list)
	{
		
		
		return 0;
	}
	/**
	 * print test data
	 */
	/*void printTrainInstance()
	{
		for(TrainInstance i : listOfTrainInstances)
		{
			System.out.println(i);
		}
	}*/
	public static void main(String args[])
	{
		DecisionTreeID3 id3 = new DecisionTreeID3();
		id3.populateTrainData();
		id3.populateTestData();
		id3.normalizeTrainData();
		id3.normalizeTestData();
		// creating the root node
		DTNode root = new DTNode();
		Set<DTTrainInstance> set = new HashSet<>();
		for(int i=0;i<listOfTrainInstances.size();i++)
		{
			set.add(listOfTrainInstances.get(i));
		}
		root.setSetOfInstance(set);
		root.setEntropyOfSet(ENTROPY_OF_SET);
		root.setParent(null);
		//System.out.println("the value of entropy of whole set is:"+ entropy);
		//id3.findingTheataForFeatures();
		//id3.printMap();
		
		//id3.printTrainInstance();
	}
}

/*class DTNode
{
	Attribute attribute; // attribute for every node
	ArrayList<DTNode> listOfChild; // list of child nodes for the current node
	DTNode parent; // parent of current node
	double entropy; // entropy for the current node
	//double gain; // gain for the current node
	double totalExamples; // total number of examples
	
	public DTNode()
	{
		this.attribute = null;
		this.listOfChild = new ArrayList<>();
		this.parent = null;
		this.entropy = 0.0;
		//this.gain = 0.0;
	}
	// setter and getter methods for the class data members
	public Attribute getAttribute()
	{
		return this.attribute;
	}
	public void setAttribute(Attribute attribute)
	{
		this.attribute = attribute;
	}
	public DTNode getParent()
	{
		return this.parent;
	}
	public void setParent(DTNode parent)
	{
		this.parent = parent;
	}
	public ArrayList<DTNode> getListOfChild()
	{
		return this.listOfChild;
	}
	public void setListOfChild(DTNode child)
	{
		this.listOfChild.add(child);
	}
	public double getEntropy()
	{
		return this.entropy;
	}
	public void setEntropy(double entropy)
	{
		this.entropy = entropy;
	}
	/*public double getGain()
	{
		return this.gain;
	}
	public void setGain(double gain)
	{
		this.gain = gain;
	}*/
/*
}
class Values
{
	String name; // name of Value
	double noOfPositives; // number of positive examples
	double noOfNegatives; // number of negative examples
	double total; // total number
	ArrayList<Integer> tupleList; // list of tuple number that belongs to this value
	public Values()
	{
		noOfNegatives = 0;
		noOfPositives = 0;
		total = noOfNegatives + noOfPositives;
		tupleList = new ArrayList<>();
	}
	@Override
	public boolean equals(Object o)
	{
		 if (o == this) return true;
	        if (!(o instanceof Values)) {
	            return false;
	        }

	        Values attr = (Values)o;

	        return this.name.equals(attr.getName());
	}
	@Override
	public int hashCode() 
	{
	    int result = 17;
	    result = 31 * result + name.hashCode();
	    result = 31 * result + tupleList.hashCode();
	    return result;
	}
	// setter and getter method
	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public double getPositives()
	{
		return this.noOfPositives;
	}
	public void setPositives(double noOfPositives)
	{
		this.noOfPositives = noOfPositives;
	}
	public double getNegatives()
	{
		return this.noOfNegatives;
	}
	public void setNegatives(double noOfNegatives)
	{
		this.noOfNegatives = noOfNegatives;
	}
	public ArrayList<Integer> getTupleList()
	{
		return this.tupleList;
	}
	public void setTupleIndex(int index)
	{
		this.tupleList.add(index);
	}
}
class Attribute
{
	String name; // name of the attribute like Overcast in this case
	Set<Values> values; // number of possible values Attribute can take
	
	public Attribute()
	{
		this.name = null;
		this.values = new HashSet<>();
	}
	// setter and getter method
	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Set<Values> getValues()
	{
		return this.values;
	}
	public void setValues(Values value)
	{
		this.values.add(value);
	}
}
class Tuples
{
	ArrayList<String> listOfValues; // list of values 
	boolean binaryClass; // class either + or -
	public Tuples()
	{
		this.listOfValues = new ArrayList<>();
	}
	// setter and getter method
	public ArrayList<String> getListOfValues()
	{
		return this.listOfValues;
	}
	public void setListOfValues(String value)
	{
		this.listOfValues.add(value);
	}
	public boolean getBinaryClass()
	{
		return this.binaryClass;
	}
	public void setBinaryClass(boolean binaryClass)
	{
		this.binaryClass = binaryClass;
	}
}
public class DecisionTreeID3 
{
	public static double ROOT_NODE_ENTROPY;  // root node entropy
	public static double LOG_2 = Math.log(2); // log of 2 value
	public static final int SIZE_QUEUE = 16; // size of the queue used 
	ArrayList<Attribute> listOfAttribute = new ArrayList<>(); // list of class Attribute
	ArrayList<Tuples> listOfTuples = new ArrayList<>(); // list of class Tuple
	static final String SEPARATOR = ","; // separator for the given file
	static final String FILE_NAME = "trainDataDTID3.txt"; // training data file 
	DTNode root = new DTNode(); // root node
	/**
	 * main function
	 * @param args
	 */
/*
	public static void main(String args[])
	{
		DecisionTreeID3 tree = new DecisionTreeID3();
		try
		{
			FileReader fileReader = new FileReader(FILE_NAME); // file reading
			BufferedReader bufferedReader = new BufferedReader(fileReader); // buffered reader for reading line
			tree.populateAttributes(bufferedReader); // populating the Attributes
			tree.populateTuples(bufferedReader); // populating the Tuples
			tree.populateValues();
			//tree.printData(); // printing the data
			bufferedReader.close(); // buffered close
		}
		catch(FileNotFoundException notFound)
		{
			notFound.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		ROOT_NODE_ENTROPY = tree.calculateTotalEntropy(); // entropy of the whole data
		// now we make the tree of DTNode
		tree.root.setEntropy(ROOT_NODE_ENTROPY);
		tree.root.setParent(null);
		//System.out.print("the total entropy is : "+ ROOT_NODE_ENTROPY);
	}
	/**
	 * calculate the total entropy
	 * @return returns the total entropy of the whole data
	 */
/*	public double calculateTotalEntropy()
	{
		double noOfPositive = 0;
		double noOfNegative = 0;
		for(Tuples t : listOfTuples)
		{
			if(t.getBinaryClass() == true)
			{
				noOfPositive++;
			}
			else
			{
				noOfNegative++;
			}
		}
		return entropy(noOfPositive, noOfNegative);
	}
	/**
	 *  calculate the entropy
	 * @param noOfPositive number of positive examples
	 * @param noOfNegative number of negative examples
	 * @return
	 */
/*	public double entropy(double noOfPositive, double noOfNegative)
	{
		double total = noOfPositive + noOfNegative;
		double positiveProb = noOfPositive/total;
		double negativeProb = noOfNegative/total;
		double positive = -(positiveProb*(Math.log(positiveProb)/LOG_2));
		double negative = -(negativeProb*(Math.log(negativeProb)/LOG_2));
		return positive + negative;
	}
	/**
	 * find the best the attribute for split with highest gain
	 * @param root the root of the Decision Tree
	 * @return attribute which is to be taken to split
	 */
/*	public Attribute bestAttributeToSplit(DTNode root)
	{
		PriorityQueue<Double> queue = new PriorityQueue<>(SIZE_QUEUE, new Comparison());
		for(int i=0;i<listOfAttribute.size();i++)
		{
			//double gain = ROOT_NODE_ENTROPY - ();
		}
		return null;
	}
	/**
	 * create the tree structure by attaching the given node
	 * @param node
	 */
/*	public void createTree(DTNode node)
	{
		
	}
	/**
	 * printing of the data that is populated
	 */
/*	public void printData()
	{
		for(Attribute attr : listOfAttribute)
		{
			System.out.print(attr.getName() + "   ");
			for(Values value : attr.getValues())
			{
				System.out.print(value.getName() + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
		// printing of tuples
		for(Tuples tup : listOfTuples)
		{
			for(String value : tup.getListOfValues())
			{
				System.out.print(value + " ");
			}
			System.out.print(tup.getBinaryClass());
			System.out.println();
		}
	}
	/**
	 * populate the Values by list of tuple and list of attributes
	 */
/*	public void populateValues()
	{
		for(int i=0;i < listOfTuples.size();i++)
		{
			Tuples t = listOfTuples.get(i);
			for(int j=0;j<t.listOfValues.size();j++)
			{
				Set<Values> values = listOfAttribute.get(j).getValues();
				for(Values v : values)
				{
					if(v.getName().equals(t.listOfValues.get(j)))
					{
						if(t.binaryClass == true)
						{
							v.noOfPositives++;
						}
						else
						{
							v.noOfNegatives++;
						}
						v.setTupleIndex(i);
					}
				}
			}
		}
	}
	/**
	 *  populate the tuples that are present in the text file
	 * @param bufferedReader buffered reader for the reading and populating the tuples
	 */
/*	public void populateTuples(BufferedReader bufferedReader)
	{
		String line;
		try
		{
			while((line = bufferedReader.readLine()) != null)
			{
				String arr1[] = line.split(SEPARATOR);
				Tuples newTuple = new Tuples(); // make a new tuple
				for(int i=0;i<arr1.length-1;i++)
				{
					Values value = new Values();
					value.setName(arr1[i]);
					listOfAttribute.get(i).setValues(value); // populating the set in the Attribute assuming they are going in same order
					newTuple.setListOfValues(arr1[i]); // populating tuple
				}
				if(arr1[arr1.length-1].equals("yes"))
				{
					newTuple.setBinaryClass(true);
				}
				else
				{
					newTuple.setBinaryClass(false);
				}
				listOfTuples.add(newTuple);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}	
	}
	/**
	 * populate the attributes that are present in the text file
	 * @param bufferedReader buffered reader for the reading and populating the attributes
	 */
/*	public void populateAttributes(BufferedReader bufferedReader)
	{
		String line;
		try
		{
			line = bufferedReader.readLine();
			//ArrayList<String> attributes = new ArrayList<>();
			String arr[] = line.split(SEPARATOR);
			for(int i=0;i<arr.length-1;i++)
			{
				Attribute newAttribute = new Attribute();
				newAttribute.setName(arr[i]);
				listOfAttribute.add(newAttribute);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}*/