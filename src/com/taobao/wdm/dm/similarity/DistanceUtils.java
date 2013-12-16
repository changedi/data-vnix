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
package com.taobao.wdm.dm.similarity;

import com.taobao.wdm.dm.cluster.Cluster;
import com.taobao.wdm.dm.cluster.Clusterable;

/**
 * @author zunyuan.jy
 * 
 * @since 2013-11-30
 */
public class DistanceUtils {
	/**
	 * Calculates the L<sub>1</sub> (sum of abs) distance between two points.
	 * 
	 * @param p1
	 *            the first point
	 * @param p2
	 *            the second point
	 * @return the L<sub>1</sub> distance between the two points
	 */
	public static double distance1(double[] p1, double[] p2) {
		double sum = 0;
		for (int i = 0; i < p1.length; i++) {
			sum += Math.abs(p1[i] - p2[i]);
		}
		return sum;
	}

	/**
	 * Calculates the L<sub>1</sub> (sum of abs) distance between two points.
	 * 
	 * @param p1
	 *            the first point
	 * @param p2
	 *            the second point
	 * @return the L<sub>1</sub> distance between the two points
	 */
	public static int distance1(int[] p1, int[] p2) {
		int sum = 0;
		for (int i = 0; i < p1.length; i++) {
			sum += Math.abs(p1[i] - p2[i]);
		}
		return sum;
	}

	/**
	 * Calculates the L<sub>2</sub> (Euclidean) distance between two points.
	 * 
	 * @param p1
	 *            the first point
	 * @param p2
	 *            the second point
	 * @return the L<sub>2</sub> distance between the two points
	 */
	public static double distance(double[] p1, double[] p2) {
		double sum = 0;
		for (int i = 0; i < p1.length; i++) {
			final double dp = p1[i] - p2[i];
			sum += dp * dp;
		}
		return Math.sqrt(sum);
	}

	/**
	 * Calculates the L<sub>2</sub> (Euclidean) distance between two points.
	 * 
	 * @param p1
	 *            the first point
	 * @param p2
	 *            the second point
	 * @return the L<sub>2</sub> distance between the two points
	 */
	public static double distance(int[] p1, int[] p2) {
		double sum = 0;
		for (int i = 0; i < p1.length; i++) {
			final double dp = p1[i] - p2[i];
			sum += dp * dp;
		}
		return Math.sqrt(sum);
	}

	/**
	 * Calculates the L<sub>&infin;</sub> (max of abs) distance between two
	 * points.
	 * 
	 * @param p1
	 *            the first point
	 * @param p2
	 *            the second point
	 * @return the L<sub>&infin;</sub> distance between the two points
	 */
	public static double distanceInf(double[] p1, double[] p2) {
		double max = 0;
		for (int i = 0; i < p1.length; i++) {
			max = Math.max(max, Math.abs(p1[i] - p2[i]));
		}
		return max;
	}

	/**
	 * Calculates the L<sub>&infin;</sub> (max of abs) distance between two
	 * points.
	 * 
	 * @param p1
	 *            the first point
	 * @param p2
	 *            the second point
	 * @return the L<sub>&infin;</sub> distance between the two points
	 */
	public static int distanceInf(int[] p1, int[] p2) {
		int max = 0;
		for (int i = 0; i < p1.length; i++) {
			max = Math.max(max, Math.abs(p1[i] - p2[i]));
		}
		return max;
	}
	
}
