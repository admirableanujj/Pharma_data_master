package src;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Test {
	static String inputFileName = null;
	static String outputFileName = null;
	public static void main(String[] args) {
		//String currentDirectory;
		//File file = new File(".");
		//currentDirectory = file.getAbsolutePath();
		//System.out.println("Current working directory : "+currentDirectory);
		
		inputFileName = args[0];
		outputFileName = args[1];

		ArrayList<InputData> inputDataGiven = new ArrayList<InputData>();
		try {

			inputDataGiven = Test.csvFileRead(inputFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<OutPut> sortedData = new ArrayList<OutPut>();
		sortedData = Test.sortAndRemoveDuplicate(inputDataGiven);
		Collections.sort(sortedData);
		//for (OutPut outPut : sortedData) {
		//	System.out.println(outPut.getDrug_name() + "," + 			//	outPut.getNum_prescriber() + "," + outPut.getTotalCost());
		//}
		try {
			boolean success = Test.csvFileWrite(sortedData);
			if (success) {
				System.out.println("Success");
			} else {
				System.out.println("Not Success");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ArrayList<InputData> csvFileRead(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = null;
		ArrayList<InputData> dataGiven = new ArrayList<InputData>();
		boolean firstLine = true;
		while ((line = br.readLine()) != null) {

			String[] values = line.split(",");
			if (!firstLine) {
				InputData inputObject = new InputData(values[0], values[1], values[2], values[3],
						Double.parseDouble(values[4]));
				dataGiven.add(inputObject);
			} else {
				firstLine = false;
			}
			// for (String str : values) {
			// System.out.println(str);
			// }
		}
		br.close();
		return dataGiven;

	}

	public static boolean csvFileWrite(ArrayList<OutPut> outPutData) throws IOException {
		try {
			System.out.println(outputFileName);
			FileWriter writer = new FileWriter(outputFileName, true);
		writer.write("drug_name,num_prescriber,total_cost");
		writer.write("\r\n"); // write new line	
		for (OutPut outPut : outPutData) {
		writer.write(outPut.getDrug_name() + "," + 					outPut.getNum_prescriber() + "," + outPut.getTotalCost());
				writer.write("\r\n"); // write new line
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static ArrayList<OutPut> sortAndRemoveDuplicate(ArrayList<InputData> inputDataGiven) {
		ArrayList<OutPut> sortedData = new ArrayList<OutPut>();
		ArrayList<String> drugName = new ArrayList<String>();
		for (InputData inputData : inputDataGiven) {
			if (drugName.isEmpty()) {
				drugName.add(inputData.drugName);
			} else {
				if (drugName.contains(inputData.drugName)) {
					continue;
				} else {
					drugName.add(inputData.drugName);
				}
			}

		}
		Collections.sort(drugName);

		for (String name : drugName) {
			ArrayList<InputData> tempData = new ArrayList<InputData>();
			for (InputData inputData : inputDataGiven) {

				if (inputData.getDrugName().equalsIgnoreCase(name)) {
					tempData.add(inputData);

				}

			}
			OutPut dataOut = new OutPut();
			dataOut.setDrug_name(name);
			dataOut.setNum_prescriber(tempData.size());
			double totalCost = 0;
			for (InputData inputData2 : tempData) {
				totalCost = totalCost + inputData2.getDrugCost();
			}
			dataOut.setTotalCost(totalCost);
			sortedData.add(dataOut);
		}
		return sortedData;
	}

}

class InputData {

	String id;
	String prescriberLastName;
	String prescriberFirstName;
	String drugName;
	double drugCost;

	public InputData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InputData(String id, String prescriberLastName, String prescriberFirstName, String drugName,
			double drugCost) {
		super();
		this.id = id;
		this.prescriberLastName = prescriberLastName;
		this.prescriberFirstName = prescriberFirstName;
		this.drugName = drugName;
		this.drugCost = drugCost;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrescriberLastName() {
		return prescriberLastName;
	}

	public void setPrescriberLastName(String prescriberLastName) {
		this.prescriberLastName = prescriberLastName;
	}

	public String getPrescriberFirstName() {
		return prescriberFirstName;
	}

	public void setPrescriberFirstName(String prescriberFirstName) {
		this.prescriberFirstName = prescriberFirstName;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public double getDrugCost() {
		return drugCost;
	}

	public void setDrugCost(double drugCost) {
		this.drugCost = drugCost;
	}

}

class OutPut implements Comparable<OutPut> {

	String drug_name;
	int num_prescriber;
	double totalCost;

	public OutPut() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OutPut(String drug_name, int num_prescriber, double totalCost) {
		super();
		this.drug_name = drug_name;
		this.num_prescriber = num_prescriber;
		this.totalCost = totalCost;
	}

	public String getDrug_name() {
		return drug_name;
	}

	public void setDrug_name(String drug_name) {
		this.drug_name = drug_name;
	}

	public int getNum_prescriber() {
		return num_prescriber;
	}

	public void setNum_prescriber(int num_prescriber) {
		this.num_prescriber = num_prescriber;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public int compareTo(OutPut opt) {
		if (totalCost == opt.totalCost)
			return 0;
		else if (totalCost < opt.totalCost)
			return 1;
		else
			return -1;
	}
}
