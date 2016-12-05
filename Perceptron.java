package supervised;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Implementation of Perceptron Neural Network
 *   
 * @author harshitpareek
 *
 */

// instance class which will contain the feature values and class for that particular values
class PTrainInstance
{
	private int binaryClass; // class in which instance has been classified
	private ArrayList<Double> listOfFeatures; // list of feature values
	public PTrainInstance()
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

// class test instance

class PTestInstance 
{
	private int ActualClass; // actual class value in which instance has been classified
	private int predictedClass; // class that is predicted by the classifier
	private ArrayList<Double> listOfFeatures; // list of feature values
	public PTestInstance()
	{
		this.listOfFeatures = new ArrayList<>();
		predictedClass = -1;
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
public class Perceptron 
{
	public static final int EPOCH = 10000;
	public static final int THRESHOLD = 0; // threshold value is 0 if we include the bias in the equation
	public static double LEARNING_RATE = 0.1; // learning rate which will deduce the change in the weights and bias (initial)
	public static int NUMBER_OF_FEATURES; // number of features
	public static final String fileNameTrain = "trainData.txt"; // name of the train file
	public static final String fileNameTest = "testData.txt"; // name of the test file
	public static final String SEPARATOR = ","; // separator for the CSV files
    public static String className; // class Name
	public static ArrayList<String> listOfFeatureName = new ArrayList<>(); // the name of the features
	public static double BIAS = 0.0; // bias (initial value of bias)
	public ArrayList<PTrainInstance> listOfTrainInstances = new ArrayList<>(); // training data
	public ArrayList<PTestInstance> listOfTestInstances = new ArrayList<>(); // test data
	public double weightArray[];  // weight array (initial value to be 0.0)
	
	/**
	 * initializes the weight array
	 */
	public void initializeWeightArray()
	{
		weightArray = new double[NUMBER_OF_FEATURES + 1];
		for(int i=0;i<NUMBER_OF_FEATURES + 1;i++)
		{
			weightArray[i] = 0.0;
		}
	}
	/**
	 *  print the weight array
	 */
	public void printWeigthArray()
	{
		for(int i=0;i<NUMBER_OF_FEATURES+1;i++)
		{
			System.out.print(weightArray[i] + "   ");
		}
	}
	/**
	 * generating the training instances for the perceptron
	 * @return return the array list containing all the training instances 
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
			NUMBER_OF_FEATURES = listOfFeatureName.size();
			while((line = bufferedReader.readLine()) != null)
			{
				String featureArr[] = line.split(SEPARATOR);
				PTrainInstance newInstance = new PTrainInstance();
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
			PTrainInstance newInstance = listOfTrainInstances.get(i);
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
			PTrainInstance newInstance = listOfTrainInstances.get(i);
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
				PTestInstance newInstance = new PTestInstance();
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
			PTestInstance newInstance = listOfTestInstances.get(i);
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
			PTestInstance newInstance = listOfTestInstances.get(i);
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
	public void trainPerceptron()
	{
		double costFunction = 0.0;
		int counter = EPOCH;
		do
		{
			int pValue = 0;
			int error = 0;
			for(int i=0;i<listOfTrainInstances.size();i++)
			{
				PTrainInstance newInstance = listOfTrainInstances.get(i);
				pValue = predictedValue(newInstance); 
				// change the weight, bias and learning rate
				error = newInstance.getBinaryClass() - pValue;
				for(int k=0;k<weightArray.length;k++)
				{
					// change in bias
					if(k == 0)
					{
						double newBias = weightArray[k] + (LEARNING_RATE * (error));
						weightArray[k] = newBias;
					}
					else
					{
						double newWeight = weightArray[k] + (LEARNING_RATE * (error) * newInstance.getListOfFeatures().get(k-1));
						weightArray[k] = newWeight;
					}
				}
				costFunction += Math.pow(error, 2);
				// decrease the learning rate by some factor
				//LEARNING_RATE = LEARNING_RATE - 0.01;
			}
			counter--;
		}while(counter > 0 && costFunction != 0);
		
		/*if(costFunction == 0)
		{
			System.out.println(" it converge in this number of epoch "+ (EPOCH - counter));
		}
		else
		{
			System.out.println(" it does not converge in this number of epoch runs all the epoch ");
		}*/
	}
	/**
	 * the predicted Value returned by the sign activation function
	 * @param newInstance is of type PTrainInstance
	 * @return returns the predicted value
	 */
	public int predictedValue(PTrainInstance newInstance)
	{
		double sum = 0.0;
		sum += weightArray[0] * 1;
		for (int i=0;i<newInstance.getListOfFeatures().size();i++)
		{
			sum += weightArray[i+1] * newInstance.getListOfFeatures().get(i);
		}
		// activation function sign function for this we can use sigmoid function also
		if(sum >= 0)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	/**
	 * the predicted Value returned by the sign activation function
	 * @param newInstance is of type PTestInstance
	 * @return returns the predicted value
	 */
	public int predictedValue(PTestInstance newInstance)
	{
		double sum = 0.0;
		sum += weightArray[0] * 1;
		for (int i=0;i<newInstance.getListOfFeatures().size();i++)
		{
			sum += weightArray[i+1] * newInstance.getListOfFeatures().get(i);
		}
		// activation function sign function for this we can use sigmoid function also
		if(sum >= 0)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	/**
	 * test the data for the given weight vector to see the accuracy
	 */
	public void testPerceptron()
	{
		for(int i=0;i<listOfTestInstances.size();i++)
		{
			PTestInstance newInstance = listOfTestInstances.get(i);
			int pValue = predictedValue(newInstance);
			newInstance.setPredictedClass(pValue);
		}
	}
	/**
	 * accuracy of the classifier
	 * @return the value of accuracy of the classifier
	 */
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
	/*
	public void trainingOfData()
	{
		// initialize the weight array
		weightArray.add(BIAS);
		for(int i=1;i<=TrainingInstance.NUMBER_OF_FEATURES;i++)
		{
			weightArray.add(Double.valueOf((new DecimalFormat("#0.0").format(0))));
		}
		// training the data
		int counter = REPETATION;
		do
		{
			for(TrainingInstance t : trainingData)
			{
				double predicted = predictedValue(t);
				
				double error = predicted - t.getClassValue();
				System.out.println("the value of error is:" + error);
				if(error == 0)
				{
					break;
				}
				if(predicted != t.classValue)
				{
					for(int i=1;i<weightArray.size();i++)
					{
						double newWeight = weightArray.get(i) + LEARNING_RATE*((t.getClassValue())*(t.listOfFeatures.get(i)));
						System.out.println("the newWeight is :" + newWeight + " at :" + i);
						weightArray.set(i,Double.valueOf((new DecimalFormat("#0.0").format(newWeight))));
					}
				}
			}
			counter--;
		}while(counter != 0);
	}
	*/
	/**
	 * 
	 * @param t
	 * @return
	 */
	/*public double predictedValue(TrainingInstance t)
	{
		double value = 0.0;
		for(int i=0;i<weightArray.size();i++)
		{
			value += (t.listOfFeatures.get(i)*weightArray.get(i));
		}
		return value;
	}*/
	/**
	 * print the training instance data
	 */
	void printTrainInstance()
	{
		for(PTrainInstance i : listOfTrainInstances)
		{
			System.out.println(i);
		}
	}

	/**
	 * main function
	 * @param args command line argument
	 */
	public static void main(String args[])
	{
		Perceptron p = new Perceptron();
		p.populateTrainData();
		p.populateTestData();
		p.normalizeTrainData();
		p.normalizeTestData();
		p.initializeWeightArray();
		p.trainPerceptron();
		p.testPerceptron();
		double value = p.accuracy();
		System.out.println("the accuracy of the classifier is:" + value);
		//p.printWeigthArray();
		//p.printTrainInstance();
	}
}
