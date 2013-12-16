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
 * The Two-Threshold Basic Sequential Algorithmic Scheme.
 * 
 * @author zunyuan.jy
 *
 * @since 2013-12-16
 */
public class TTSASCluster<T extends Clusterable<T>> extends Clusterer<T> {
	
	private double phi1;
	private double phi2;
	
	public TTSASCluster(double phi1,double phi2){
		super();
		this.phi1 = phi1;
		this.phi2 = phi2;
	}
	
	public void reset(double phi1, double phi2) {
		super.reset();
		this.phi1 = phi1;
		this.phi2 = phi2;
	}
	/**
	 * 通过定义一个“灰度”区域来解决无意义聚类，通过使用两个阈值phi1和phi2来达到目的。
	 * 如果向量x和最近的聚类C的不相似性测度d(x,C)比phi1小，则x被分配到聚类C中；如果
	 * d(x,C)比phi2大，那么生成一个新聚类，x分配到其中。否则，则存在不确定性，x的分配在后面的阶段决定。
	 * @param points 数据集
	 * @param Phi1 小阈值
	 * @param Phi2 大阈值  phi2 > phi1
	 * @return
	 */
	public List<Cluster<T>> cluster(final Collection<T> points,final double Phi1,final double Phi2){
		int m = 0;
		int n = points.size();
		int prev_change = 0;
		int cur_change = 0;
		int exists_change = 0;			//核对当前检查points过程中是否至少存在一个向量已经分类
		boolean clas[] = new boolean[n];
		boolean stopCondition = true;
		boolean isFirst = true;
		double disOfXandCj = 0;
		double disOfXandCk;
		for(int i=0;i<n;i++) clas[i] = false;
		for(int i=0;i<n;i++){
			if(clas[i]){
				stopCondition = true;
				break;
			}
		}
		List<T> ptList = new ArrayList<T>(points);
		List<Cluster<T> > cList = new ArrayList<Cluster<T> >();
		Cluster<T> Ck = null;
		while(stopCondition){
			stopCondition = false;
			isFirst = true;
			for(int i=0;i<n;i++){
				if(!clas[i]&&isFirst&&exists_change==0){	//第一次进for循环
					m++;
					Cluster<T> cm = new Cluster<T>(ptList.get(i));
					cm.addPoint(ptList.get(i));
					cList.add(cm);
					clas[i]=true;
					cur_change++;
					isFirst = false;
				}
				else if(!clas[i]){
					disOfXandCk = Double.MAX_VALUE;
					Iterator<Cluster<T> > cListIt = cList.iterator(); 
					while(cListIt.hasNext()){
						Cluster<T> Cj = cListIt.next();
						disOfXandCj = getDisOfPointAndCluster(ptList.get(i),Cj);
						if(disOfXandCk > disOfXandCj){
							disOfXandCk = disOfXandCj;
							Ck = Cj;
						}
					}
					if(disOfXandCk < Phi1){
						clas[i] = true;
						cList.remove(Ck);
						Ck.addPoint(ptList.get(i));
						final T newCenter = Ck.getCenter().centroidOf(Ck.getPoints());
						Cluster<T> tempCluster = new Cluster<T>(newCenter);
						for(int j=0;j<Ck.getPoints().size();j++){
							tempCluster.addPoint(Ck.getPoints().get(j));
						}
						cList.add(tempCluster);
						cur_change ++;
					}
					else if(disOfXandCk > Phi2){
						m++;
						Cluster<T> cm = new Cluster<T>(ptList.get(i));
						cm.addPoint(ptList.get(i));
						cList.add(cm);
						clas[i]=true;
						cur_change++;
					}
				}
				else if(clas[i]){
					cur_change ++;
				}
			}
			exists_change = Math.abs(cur_change-prev_change);//比较当前检查已经分类的向量数cur_change和上次检查已经分类的向量数prev_change得到
			prev_change = cur_change;
			cur_change = 0;
			for(int i=0;i<n;i++){			//至少存在一个clas[i]为false，退出while的条件
				if(!clas[i]){
					stopCondition = true;
					break;
				}
			}
		}
		return cList;
	}
	/**
	 * 选择不同的测度，有不同的算法。
	 * 这里默认dis(x,C)为点到聚类中心的距离。
	 */
	private double getDisOfPointAndCluster(T t, Cluster<T> cj) {
		return t.distanceFrom(cj.getCenter());
	}
	@Override
	public List<Cluster<T>> cluster() {
		return this.cluster(points, phi1, phi2);
	}

}
