/**
 * 
 */
package com.taobao.wdm.test.csv;

import java.io.File;
import java.io.IOException;

import com.taobao.wdm.csv.CSVFormat;
import com.taobao.wdm.csv.CSVParser;
import com.taobao.wdm.csv.CSVRecord;

/**
 * @author changedi
 *
 * 2013年11月18日
 * 
 */
public class CsvParserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File csvData = new File("resource/weather.csv");
		try {
			CSVParser parser = CSVParser.parse(csvData, CSVFormat.RFC4180);
			for (CSVRecord csvRecord : parser) {
				System.out.println(csvRecord.getRecordNumber());
				System.out.println(csvRecord);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
