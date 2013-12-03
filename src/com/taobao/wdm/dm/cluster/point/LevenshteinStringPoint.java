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
import com.taobao.wdm.dm.similarity.LevenshteinDistance;

/**
 * @author zunyuan.jy
 * 
 * @since 2013-12-2
 */
public class LevenshteinStringPoint implements
		Clusterable<LevenshteinStringPoint>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2868824577759455756L;
	private String point;

	/**
	 * @return the point
	 */
	public String getPoint() {
		return point;
	}

	public LevenshteinStringPoint(String point) {
		super();
		this.point = point;
	}

	@Override
	public LevenshteinStringPoint centroidOf(
			final Collection<LevenshteinStringPoint> p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double distanceFrom(final LevenshteinStringPoint p) {
		return LevenshteinDistance.distance(point, p.getPoint());
	}

	public int hashCode() {
		return this.getPoint().hashCode();
	}

	public boolean equals(final Object other) {
		if (!(other instanceof LevenshteinStringPoint)) {
			return false;
		}
		return this.getPoint().equals(other);
	}

}
