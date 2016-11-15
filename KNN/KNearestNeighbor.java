package supervised;

import java.io.*;
import java.util.*;


class ResultCompartor implements Comparator<Double>
{

	@Override
	public int compare(Double o1, Double o2) {
		return o1 > o2 ? 1 : o1 == o2 ? 0 : -1;
	}
	
}
public class KNearestNeighbor 
{
	public static final int SIZE = 16; // fixed size of Priority queue
	public static void main(String args[])
	{
		// testing some text file which contains data for the k-nearest neighbor
		String fileName = "trainDataKNN.txt";
		// csv file field separator
		final String separator = ",";
		// hash map containing the map for class and array list of feature values 
		HashMap<String, ArrayList<ArrayList<Double>>> mapData = new HashMap<>();
		// priorityQueue for finding the minimum of the each class
		PriorityQueue<Double> resultQueue = new PriorityQueue<>(SIZE, new ResultCompartor());
		// priorityQueue for finding the minimum between the best values of each class
		PriorityQueue<Double> bestQueue = new PriorityQueue<>(SIZE, new ResultCompartor());
		// result HashMap
		HashMap<Double, String> resultMap = new HashMap<>();
		// test instance 
		double test[] = {0.65, 0.78, 0.21, 0.29, 0.58};
		try
		{
			// file reader object
			FileReader fileReader = new FileReader(fileName);
			// buffered reader object
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			
			ArrayList<Double> features;
			
			//ArrayList<ArrayList<Double>> featuresArray;
			
			while((line = bufferedReader.readLine()) != null)
			{
				String arr[] = line.split(separator);
				features = new ArrayList<>();
				for(int i=0;i<arr.length-1;i++)
				{
					double featureValue = Double.valueOf(arr[i]);
					features.add(featureValue);
				}
				String classification = arr[arr.length-1];
				if(mapData.containsKey(classification))
				{
					mapData.get(classification).add(features);
				}
				else
				{
					ArrayList<ArrayList<Double>> featuresArray = new ArrayList<>();
					featuresArray.add(features);
					mapData.put(classification,featuresArray);
				}
			}
			for(Map.Entry<String, ArrayList<ArrayList<Double>>> entry : mapData.entrySet())
			{
				String key = entry.getKey();
				ArrayList<ArrayList<Double>> lists = mapData.get(key);
				for(ArrayList<Double> list : lists)
				{
					// assuming that all features list have same value without exception
					if(list.size() != test.length)
					{
						break;
					}
					else
					{
						double sum = 0;
						for(int i = 0; i<list.size();i++)
						{
							sum += Math.pow((list.get(i)-test[i]), 2);
						}
						double equlidianDistance = Math.sqrt(sum);
						resultQueue.add(equlidianDistance);
					}
				}
				double bestValue = resultQueue.peek();
				resultQueue.clear();
				bestQueue.add(bestValue);
				resultMap.put(bestValue, key);
			}
			double resultValue = bestQueue.peek();
			System.out.print("resultClass is : "+ resultMap.get(resultValue));
			/*for(Map.Entry<String, ArrayList<Double>> entry : mapData.entrySet())
			{
				String key = entry.getKey();
				ArrayList<Double> list = mapData.get(key);
				if(list.size() != test.length)
				{
					break;
				}
				else
				{
					double sum = 0;
					for(int i = 0; i<list.size();i++)
					{
						sum += Math.pow((list.get(i)-test[i]), 2);
					}
					double equlidianDistance = Math.sqrt(sum);
					resultQueue.add(equlidianDistance);
					resultMap.put(equlidianDistance, key);
				}
			}
			System.out.print("resultClass is : "+ resultMap.get(resultQueue.peek()));*/
			bufferedReader.close();
		}
		catch(FileNotFoundException fileNotfound)
		{
			fileNotfound.printStackTrace();
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}
	}
}
