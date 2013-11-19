/**
 * 
 */
package com.taobao.wdm.common.io;

import com.taobao.wdm.common.dataset.DataSet;

/**
 * @author changedi
 * 
 * 
 */
public interface Reader {

	void readHeader(Object src, DataSet ds) throws Exception;

	void read(Object src, DataSet ds);
}
