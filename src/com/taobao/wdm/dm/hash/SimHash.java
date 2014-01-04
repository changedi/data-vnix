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
package com.taobao.wdm.dm.hash;

/**
 * SimHash:http://www.cs.princeton.edu/courses/archive/spr04/cos598B/bib/CharikarEstim.pdf .
 * 
 * @author changedi
 * 
 *         2014年1月4日
 * 
 */
public class SimHash {	

	public int hash32(HashObject[] args) {
		int r = 0;
		int f[] = new int[32];
		for (HashObject arg : args) {
			int hashCode = arg.object.hashCode();
			for (int i = 0; i < 32; i++) {
				if ((hashCode & (1 << i)) != 0) {
					f[i] += arg.weight;
				} else {
					f[i] -= arg.weight;
				}
			}
		}
		for (int i = 0; i < f.length; i++) {
			r += f[i] > 0 ? 1 : 0;
			r <<= 1;
		}
		return r;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimHash sh = new SimHash();
		int n = 3;
		HashObject []hos = new HashObject[n];
		for(int i=0;i<n;i++){
			hos[i] = new HashObject("axc",1);
		}
		System.out.println(sh.hash32(hos));
	}

}
class HashObject {
	Object object;
	int weight;
	public HashObject(Object obj, int w){
		object = obj;
		weight = w;
	}
}