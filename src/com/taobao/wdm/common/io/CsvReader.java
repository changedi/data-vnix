/**
 * 
 */
package com.taobao.wdm.common.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.taobao.wdm.common.dataset.DataSet;
import com.taobao.wdm.csv.CSVFormat;
import com.taobao.wdm.csv.CSVParser;
import com.taobao.wdm.csv.CSVRecord;

/**
 * @author changedi
 * 
 *         2013年11月18日
 * 
 */
public class CsvReader extends TextReader {

	private String fileName;
	
	private CSVParser parser;

	public CsvReader(String fileName) throws Exception {
		this.setFileName(fileName);
		init();
	}

	@Override
	public void read(Object src, DataSet ds) {

	}

	@Override
	public void readHeader(Object src, DataSet ds) throws IOException {
		CSVRecord record = parser.getRecords().get(0);
		List<String> header = new ArrayList<String>();
		for(int i=0;i<record.size();i++){
			header.add(record.get(i));
		}
		ds.setAttributeNames(header);
	}

	@Override
	public void init() throws IOException {
		parser = CSVParser.parse(new File(fileName), CSVFormat.DEFAULT);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
