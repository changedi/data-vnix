/**
 * 
 */
package com.taobao.wdm.common.dataset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author changedi
 *
 * 
 */
public class Instance implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2370875777824913896L;
	
	private List<Attribute> attributes;
	
	public Instance(){
		this(new ArrayList<Attribute>());		
	}
	
	public Instance(List<Attribute> attributes){
		this.setAttributes(attributes);
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	public boolean addAttribute(Attribute attr){
		return this.attributes.add(attr);
	}
	
	
}
