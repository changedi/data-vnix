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

	private 	int 		classIndex  = -1;//-1 means no class
	private List<String> 	attributeNames	;
	private List<Instance>	instances		;
	private		int 		size	    = 0	;
	private		int 		columnNum	= 0	;
	
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
	
	public boolean add(Instance inst){
		size++;
		return this.instances.add(inst);
	}
	
	public void setClassIndex(int index){
		this.classIndex = index;
	}
	
	public int getClassIndex(){
		return this.classIndex;
	}

	public List<String> getAttributeNames() {
		return attributeNames;
	}

	public void setAttributeNames(List<String> attributeNames) {
		this.attributeNames = attributeNames;
		this.columnNum = attributeNames.size();
	}

	public List<Instance> getInstances() {
		return instances;
	}

	public void setInstances(List<Instance> instances) {
		this.instances = instances;
		this.size = instances.size();
	}

	public int size() {
		return size;
	}

	public int attributesNum() {
		return columnNum;
	}
	
}
