package utilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.opencsv.CSVReader;

public class CSVFileReader {

	public static List<String> keyCount;

	public static void main(String[] args) throws IOException {

		String filePath = "C:\\Users\\panorama\\eclipse-workspace\\APIDemoRestAssured\\testData\\TestExecutionDriver.csv";

		CSVFileReader csvFileReader = new CSVFileReader(filePath);
		System.out.println(csvFileReader.openCSVReader().keySet());

	}

	String csvFilePath;

	public CSVFileReader(String filePath) {
		this.csvFilePath = filePath;

	}

	public int rowCount() throws IOException {
		FileReader fileReader = new FileReader(csvFilePath);
		CSVReader csvReader = new CSVReader(fileReader);
		int rowC = csvReader.readAll().size();
		return rowC;

	}

	public LinkedHashMap<String, String> openCSVReader() {
		keyCount = new ArrayList<String>();
		int testCaseCount = 0;

		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

		try {

			// Create an object of filereader
			// class with CSV file as a parameter.
			FileReader filereader = new FileReader(csvFilePath);

			// create csvReader object passing
			// file reader as a parameter
			CSVReader csvReader = new CSVReader(filereader);

			String[] headers = null;
			int length = rowCount();

			for (int i = 0; i < length; i++) {
				if (i == 0) {
					headers = csvReader.readNext();

				} else {

					String[] data = csvReader.readNext();
					String execution = data[2];
					String suiteCategory = data[3];

					if (execution.equalsIgnoreCase("Y") && suiteCategory.contains("")) {
						testCaseCount = testCaseCount + 1;
						for (int j = 0; j < data.length; j++) {
							String key = headers[j] + "_" + testCaseCount;
							String value = data[j];

//	        			System.out.println("KEY="+key+" , "+"Value="+value);
							map.put(key, value);
							if (!keyCount.contains(String.valueOf(testCaseCount))) {
								keyCount.add(String.valueOf(testCaseCount));
							}
						}
					}

				}
			}

			csvReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;

	}

}
