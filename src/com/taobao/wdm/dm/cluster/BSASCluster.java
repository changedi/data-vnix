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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Basic Sequential Algorithmic Scheme. 适用于致密聚类.
 * 
 * @author zunyuan.jy
 * 
 * @since 2013-12-13
 */
public class BSASCluster<T extends Clusterable<T>> extends Clusterer<T> {

	private double phi;
	private int q;

	public BSASCluster(double phi, int q) {
		super();
		this.phi = phi;
		this.q = q;
	}

	public void reset(double phi, int q) {
		super.reset();
		this.phi = phi;
		this.q = q;
	}

	/**
	 * Basic Sequential Algorithmic Scheme
	 * 考虑样本空间中每个向量，根据向量到已有的聚类中心的距离，将它分配到一个已有聚类中，或者一个新生成的聚类中。 time complexity is
	 * O(N) BSAS算法对整个数据集只进行一次扫描。
	 * 
	 * @param points
	 *            待聚类的向量
	 * @param Phi
	 *            用户定义的不相似性阈值
	 * @param q
	 *            用户定义的允许的最大聚类数
	 * @return
	 */
	public List<Cluster<T>> cluster(final Collection<T> points,
			final double Phi, final int q) {
		int m = 0;
		int n = points.size();
		double disOfXandCj = 0;
		double disOfXandCk;
		List<T> ptList = new ArrayList<T>(points);
		Cluster<T> C = new Cluster<T>(ptList.get(m));
		C.addPoint(ptList.get(m));
		Cluster<T> Ck = C;
		List<Cluster<T>> cList = new ArrayList<Cluster<T>>();
		cList.add(C);
		for (int i = 1; i < n; i++) {
			disOfXandCk = Double.MAX_VALUE;
			Iterator<Cluster<T>> cListIt = cList.iterator();
			while (cListIt.hasNext()) {
				Cluster<T> Cj = cListIt.next();
				disOfXandCj = getDisOfPointAndCluster(ptList.get(i), Cj);
				if (disOfXandCk > disOfXandCj) {
					disOfXandCk = disOfXandCj;
					Ck = Cj;
				}
			}
			if (disOfXandCk > Phi && m < q) { // 不满足条件，则产生新的聚类
				m++;
				Cluster<T> cm = new Cluster<T>(ptList.get(i));
				cm.addPoint(ptList.get(i));
				cList.add(cm);
			} else { // 满足条件的将点加入已有聚类，并更新聚类中心
				if (cList.contains(Ck))
					cList.remove(Ck);
				Ck.addPoint(ptList.get(i));
				final T newCenter = Ck.getCenter().centroidOf(Ck.getPoints());
				Cluster<T> tempCluster = new Cluster<T>(newCenter);
				for (int j = 0; j < Ck.getPoints().size(); j++) {
					tempCluster.addPoint(Ck.getPoints().get(j));
				}
				cList.add(tempCluster);
			}
		}
		return cList;
	}

	/**
	 * 选择不同的测度，有不同的算法。 这里默认dis(x,C)为点到聚类中心的距离。
	 */
	private double getDisOfPointAndCluster(T t, Cluster<T> cj) {
		return t.distanceFrom(cj.getCenter());
	}

	@Override
	public List<Cluster<T>> cluster() {
		return this.cluster(points, phi, q);
	}

}
