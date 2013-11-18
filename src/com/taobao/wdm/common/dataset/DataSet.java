/**
 * 
 */
package com.taobao.wdm.common.dataset;

import java.util.ArrayList;
import java.util.List;

/**
 * @author changedi
 *
 * 
 */
public class DataSet {

	private 	int 		classIndex = -1	;//-1 means no class
	private List<String> 	attributeNames	;
	private List<Instance>	instances		;
	
	public DataSet() {
		this(new ArrayList<String>(), new ArrayList<Instance>());
	}
	
	public DataSet(List<String> attributeNames){
		this(attributeNames, new ArrayList<Instance>());
	}
	
	public DataSet(List<String> attributeNames, List<Instance> instances) {
		this.attributeNames = attributeNames;
		this.instances = instances;
	}	
	
}
