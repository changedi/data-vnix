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
		this.attributes = new ArrayList<Attribute>();
		
	}
	
	
}
