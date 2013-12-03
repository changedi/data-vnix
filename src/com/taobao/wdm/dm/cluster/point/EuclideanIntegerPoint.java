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
package com.taobao.wdm.dm.cluster.point;

import java.io.Serializable;
import java.util.Collection;

import com.taobao.wdm.dm.cluster.Clusterable;
import com.taobao.wdm.dm.similarity.DistanceUtils;

/**
 * A simple implementation of {@link Clusterable} for points with integer
 * coordinates.
 * 
 * @author zunyuan.jy
 * 
 * @since 2013-11-30
 */
public class EuclideanIntegerPoint implements
		Clusterable<EuclideanIntegerPoint>, Serializable {

	/** Serializable version identifier. */
	private static final long serialVersionUID = 3946024775784901369L;

	/** Point coordinates. */
	private final int[] point;

	/**
	 * Build an instance wrapping an integer array.
	 * <p>
	 * The wrapped array is referenced, it is <em>not</em> copied.
	 * </p>
	 * 
	 * @param point
	 *            the n-dimensional point in integer space
	 */
	public EuclideanIntegerPoint(final int[] point) {
		this.point = point;
	}

	/**
	 * Get the n-dimensional point in integer space.
	 * 
	 * @return a reference (not a copy!) to the wrapped array
	 */
	public int[] getPoint() {
		return point;
	}

	public double distanceFrom(final EuclideanIntegerPoint p) {
		return DistanceUtils.distance(point, p.getPoint());
	}

	public EuclideanIntegerPoint centroidOf(
			final Collection<EuclideanIntegerPoint> points) {
		int[] centroid = new int[getPoint().length];
		for (EuclideanIntegerPoint p : points) {
			for (int i = 0; i < centroid.length; i++) {
				centroid[i] += p.getPoint()[i];
			}
		}
		for (int i = 0; i < centroid.length; i++) {
			centroid[i] /= points.size();
		}
		return new EuclideanIntegerPoint(centroid);
	}

	public boolean equals(final Object other) {
		if (!(other instanceof EuclideanIntegerPoint)) {
			return false;
		}
		final int[] otherPoint = ((EuclideanIntegerPoint) other).getPoint();
		if (point.length != otherPoint.length) {
			return false;
		}
		for (int i = 0; i < point.length; i++) {
			if (point[i] != otherPoint[i]) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		int hashCode = 0;
		for (Integer i : point) {
			hashCode += i.hashCode() * 13 + 7;
		}
		return hashCode;
	}

	public String toString() {
		String ans = "(";
		for (int i = 0; i < point.length; i++) {
			if (i != point.length - 1)
				ans += point[i] + ",";
			else
				ans += point[i];

		}
		ans += ")";
		return ans;
	}

}
