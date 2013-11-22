/**
 * 
 */
package com.taobao.wdm.common.io;

import java.io.BufferedReader;

/**
 * @author changedi
 *
 * 2013年11月18日
 * 
 */
public abstract class TextReader implements Reader {
	protected BufferedReader reader;
	
	public abstract void init() throws Exception;
}
