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
 * Modified Basic Sequential Algorithmic Scheme
 * 
 * @author zunyuan.jy
 * 
 * @since 2013-12-13
 */
public class MBSASCluster<T extends Clusterable<T>> extends Clusterer<T> {

	private double phi;
	private int q;

	public MBSASCluster(double phi, int q) {
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
	 * Modified Basic Sequential Algorithmic Scheme
	 * 对BSAS的改进，算法包括两个阶段：第一阶段是将points里的一些向量分配到聚类中形成类；
	 * 第二阶段中，没有被分配的向量第二次参与算法，并被分配到合适的类中。 time complexity is O(N)
	 * MBSAS算法对整个数据集进行两次扫描。
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
			}
		}
		for (int i = 0; i < n; i++) { // 第二次扫描，将没有分配的向量就近分配
			Iterator<Cluster<T>> cListIt = cList.iterator();
			boolean hasX = false;
			while (cListIt.hasNext()) {
				if (cListIt.next().getPoints().contains(ptList.get(i))) {
					hasX = true;
					break;
				}
			}
			if (!hasX) { // 如果points[i]没有分配到一个聚类中
				disOfXandCk = Double.MAX_VALUE;
				cListIt = cList.iterator();
				while (cListIt.hasNext()) {
					Cluster<T> Cj = cListIt.next();
					disOfXandCj = getDisOfPointAndCluster(ptList.get(i), Cj);
					if (disOfXandCk > disOfXandCj) {
						disOfXandCk = disOfXandCj;
						Ck = Cj;
					}
				}
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
