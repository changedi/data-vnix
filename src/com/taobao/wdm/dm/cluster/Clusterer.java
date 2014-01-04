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
package com.taobao.wdm.dm.cluster;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zunyuan.jy
 * 
 * @since 2013-11-30
 */
public abstract class Clusterer<T extends Clusterable<T>> {

	protected List<T> points;

	public Clusterer() {
		points = new ArrayList<T>();
	}

	public void addPoint(T point) {
		this.points.add(point);
	}

	public void reset() {
		this.points.clear();
	}

	public abstract List<Cluster<T>> cluster();
}
