/**
 * 
 */
package com.taobao.wdm.test.dm.similarity;

import com.taobao.wdm.dm.similarity.*;
import junit.framework.TestCase;


/**
 * @author zunyuan.jy
 *
 * @since 2013-11-30
 */
public class LevenshteinDistanceTest  extends TestCase{
	public void testDistance(){
		String ori = "urbar";
		String tar = "turban";
		assertEquals(2,LevenshteinDistance.distance(ori, tar));
	}
}
