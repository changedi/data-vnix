/*
 * Copyright (C) 2013 changedi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.taobao.wdm.test.dm.cluster;

import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

import com.taobao.wdm.dm.cluster.Cluster;
import com.taobao.wdm.dm.cluster.HierachicalCluster;
import com.taobao.wdm.dm.cluster.point.EuclideanIntegerPoint;
import com.taobao.wdm.dm.cluster.point.LevenshteinStringPoint;

/**
 * @author zunyuan.jy
 * 
 * @since 2013-12-2
 */
public class HierachicalClusterTest extends TestCase {

	@Test
	public void testCluster() {
		int[][] source = { { 1 }, { 2 }, { 5 }, { 7 }, { 9 }, { 10 } };

		HierachicalCluster<EuclideanIntegerPoint> cl = new HierachicalCluster<EuclideanIntegerPoint>(
				6, 1);
		for (int i = 0; i < source.length; i++) {
			EuclideanIntegerPoint eip = new EuclideanIntegerPoint(source[i]);
			cl.addPoint(eip);
		}
		// cl.cluster(r, 6, 1);
		// cl.cluster(r, 3);
		List<Cluster<EuclideanIntegerPoint>> list = cl.cluster();
		for (Cluster<EuclideanIntegerPoint> cluster : list) {
			System.out.println(cluster.getPoints().size());
			assertEquals(true, cluster.getPoints().size() >= 1);
		}
	}

	@Test
	public void testClusterByLevenshteinDistance() {
		String[] source = { "hhxxff034", "hhxxff062", "hxfzxy12", "hxfzxy003",
				"hhxxff051", "hhxxff035", "hxfzxy13" };
		HierachicalCluster<LevenshteinStringPoint> cl = new HierachicalCluster<LevenshteinStringPoint>(
				source.length, 4);
		for (int i = 0; i < source.length; i++) {
			LevenshteinStringPoint lsp = new LevenshteinStringPoint(source[i]);
			cl.addPoint(lsp);
		}
		List<Cluster<LevenshteinStringPoint>> list = cl.cluster();
		for (Cluster<LevenshteinStringPoint> cluster : list) {
			System.out.println("c:"+cluster.getPoints().size());
			assertEquals(true, cluster.getPoints().size() >= 1);
		}
	}
}
