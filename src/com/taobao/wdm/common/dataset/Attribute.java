/**
 * 
 */
package com.taobao.wdm.common.dataset;

import java.io.Serializable;

/**
 * @author changedi
 * 
 *         attribute in one instance, think as columns of a dataset's meta.
 * 
 */
public class Attribute implements Serializable{

	private static final long serialVersionUID = 5588926947855669891L;

	private Object value;

	private AttributeType type;

	public Attribute(AttributeType type, Object value) {
		this.type = type;
		this.value = value;
	}

	public Attribute() {
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public AttributeType getType() {
		return type;
	}

	public void setType(AttributeType type) {
		this.type = type;
	}

}
