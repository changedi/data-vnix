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

import com.taobao.wdm.common.collection.DisjointSets;

public class HierachicalCluster<T extends Clusterable<T>> extends Clusterer<T> {
	private List<Cluster<T>> clusterList;
	private List<T> points;
	private DisjointSets ds;
	private static final int MAX = Integer.MAX_VALUE;
	private int n; // 原始样本点个数
	private int cc;
	private double maxDis; // 聚类结束条件：点相似度小于maxDis结束
	private int maxClusterNum; // 聚类结束条件：聚类到maxClusterNum个结束
	private double[][] r;

	// private double ori[] = {1,2,5,7,9,10};

	public HierachicalCluster(int num, double maxDis) {
		ds = new DisjointSets(num);
		n = num;
		cc = n;
		clusterList = new ArrayList<Cluster<T>>();
		points = new ArrayList<T>();
		this.maxDis = maxDis;
	}

	public void addPoint(T point) {
		this.points.add(point);
	}

	/**
	 * this method provides a hierachical way for clustering data.
	 * 
	 * @param r
	 *            denote the distance matrix
	 * @param n
	 *            denote the sample num(distance matrix's row number)
	 * @param dis
	 *            denote the threshold to stop clustering
	 */
	private void cluster(double[][] r, int n, double dis) {
		int mx = 0, my = 0;
		double vmin = MAX;
		for (int i = 0; i < n; i++) { // 寻找最小距离所在的行列
			for (int j = 0; j < n; j++) {
				if (j > i) {
					if (vmin > r[i][j]) {
						vmin = r[i][j];
						mx = i;
						my = j;
					}
				}
			}
		}
		if (vmin > dis) {
			return;
		}
		ds.union(ds.find(mx), ds.find(my)); // 将最小距离所在的行列实例聚类合并
		double o1[] = r[mx];
		double o2[] = r[my];
		double v[] = new double[n];
		double vv[] = new double[n];
		for (int i = 0; i < n; i++) {
			double tm = Math.min(o1[i], o2[i]);
			if (tm != 0)
				v[i] = tm;
			else
				v[i] = MAX;
			vv[i] = MAX;
		}
		r[mx] = v;
		r[my] = vv;
		for (int i = 0; i < n; i++) { // 更新距离矩阵
			r[i][mx] = v[i];
			r[i][my] = vv[i];
		}
		cluster(r, n, dis); // 继续聚类，递归直至所有簇之间距离小于dis值
	}

	/**
	 * 
	 * @param r
	 * @param cnum
	 *            denote the number of final clusters
	 */
	private void cluster(double[][] r, int cnum) {
		/*
		 * if(cc< cnum) System.err.println("聚类数大于实例数");
		 */
		while (cc > cnum) {// 继续聚类，循环直至聚类个数等于cnum
			int mx = 0, my = 0;
			double vmin = MAX;
			for (int i = 0; i < n; i++) { // 寻找最小距离所在的行列
				for (int j = 0; j < n; j++) {
					if (j > i) {
						if (vmin > r[i][j]) {
							vmin = r[i][j];
							mx = i;
							my = j;
						}
					}
				}
			}
			ds.union(ds.find(mx), ds.find(my)); // 将最小距离所在的行列实例聚类合并
			double o1[] = r[mx];
			double o2[] = r[my];
			double v[] = new double[n];
			double vv[] = new double[n];
			for (int i = 0; i < n; i++) {
				double tm = Math.min(o1[i], o2[i]);
				if (tm != 0)
					v[i] = tm;
				else
					v[i] = MAX;
				vv[i] = MAX;
			}
			r[mx] = v;
			r[my] = vv;
			for (int i = 0; i < n; i++) { // 更新距离矩阵
				r[i][mx] = v[i];
				r[i][my] = vv[i];
			}
			cc--;
		}
	}

	public static void main(String args[]) {

	}

	@Override
	public List<Cluster<T>> cluster() {
		calculateR();
		if (maxDis != 0) {
			this.cluster(r, n, maxDis);
		} else if (maxClusterNum != 0) {
			this.cluster(r, maxClusterNum);
		}
		Cluster<T>[] clusters = new Cluster[n];
		for (int i = 0; i < n; i++) {
			int index = ds.find(i);
			if (clusters[index] == null) {
				clusters[index] = new Cluster<T>(points.get(i));
			} else {
				clusters[index].addPoint(points.get(i));
			}
		}
		for (int i = 0; i < n; i++) {
			if (clusters[i] != null) {
				clusterList.add(clusters[i]);
			}
		}
		return clusterList;
	}

	private void calculateR() {
		r = new double[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j)
					r[i][j] = 0;
				else {
					r[i][j] = points.get(i).distanceFrom(points.get(j));
				}
			}
		}
	}
}
