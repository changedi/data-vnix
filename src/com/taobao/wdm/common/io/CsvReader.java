/**
 * 
 */
package com.taobao.wdm.common.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.taobao.wdm.common.dataset.Attribute;
import com.taobao.wdm.common.dataset.AttributeType;
import com.taobao.wdm.common.dataset.DataSet;
import com.taobao.wdm.common.dataset.Instance;
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
	
	private List<CSVRecord> recordList; 

	private boolean hasHeadRead;

	public CsvReader(String fileName) throws Exception {
		this.setFileName(fileName);
		hasHeadRead = false;
		init();
	}

	@Override
	public void read(Object src, DataSet ds) {
		try {
			if(!hasHeadRead)
				readHeader(src, ds);
			//remove head
			recordList.remove(0);
			for (CSVRecord record : recordList) {
				Instance inst = new Instance();
				for (int i = 0; i < record.size(); i++) {
					Attribute attr = new Attribute();
					try{
						attr.setValue(Double.valueOf(record.get(i)));
						attr.setType(AttributeType.NUMERIC);
					}catch(NumberFormatException nfe){
						attr.setValue(record.get(i));
						attr.setType(AttributeType.NOMINAL);
					}					
					inst.addAttribute(attr);
				}				
				ds.add(inst);
			}
			this.recordList = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void readHeader(Object src, DataSet ds) throws IOException {
		CSVRecord record = recordList.get(0);
		List<String> header = new ArrayList<String>();
		for (int i = 0; i < record.size(); i++) {
			header.add(record.get(i));
		}
		ds.setAttributeNames(header);
		hasHeadRead = true;
	}

	@Override
	public void init() throws IOException {
		parser = CSVParser.parse(new File(fileName), CSVFormat.DEFAULT);
		recordList = parser.getRecords();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
