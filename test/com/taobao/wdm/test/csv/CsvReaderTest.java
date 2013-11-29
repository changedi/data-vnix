/**
 * 
 */
package com.taobao.wdm.test.csv;

import com.taobao.wdm.common.dataset.DataSet;
import com.taobao.wdm.common.dataset.Instance;
import com.taobao.wdm.common.io.CsvReader;

/**
 * @author changedi
 *
 * 2013年11月25日
 * 
 */
public class CsvReaderTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			CsvReader reader = new CsvReader("resource/weather.csv");
			DataSet ds = new DataSet();
			reader.read(null, ds);
			ds.setClassIndex(ds.attributesNum()-1);
			System.out.println("read csv file complete!");
			System.out.println(ds.size() + " instance found!");
			System.out.println("class is '"+ds.getAttributeNames().get(ds.getClassIndex())+ "'.");
			System.out.println("header:");
			System.out.print("\t");
			for(int i=0;i<ds.getAttributeNames().size();i++){
				System.out.print(ds.getAttributeNames().get(i));
				if(i!=ds.getAttributeNames().size()-1) 
					System.out.print(",");
			}
			System.out.println();
			System.out.println("data:");
			for(int i=0;i<ds.getInstances().size();i++){
				Instance inst = ds.getInstances().get(i);
				System.out.print("\t");
				for(int j=0;j<inst.getAttributes().size();j++){
					System.out.print(inst.getAttributes().get(j).getValue());
					if(j!=inst.getAttributes().size()-1) 
						System.out.print(",");
				}
				System.out.println();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
