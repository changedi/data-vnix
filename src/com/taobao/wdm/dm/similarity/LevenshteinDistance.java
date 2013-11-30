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

/**
 * DP for LevenshteinDistance.
 * @see http://en.wikipedia.org/wiki/Levenshtein_distance
 * 
 * @author changedi
 * 
 */
public class LevenshteinDistance {

	public static int distance(String s, String t) {
		if (s == null || s.length() == 0)
			return t == null ? 0 : t.length();
		if (t == null || t.length() == 0)
			return s.length();
		if (s.equals(t))
			return 0;
		int v0[] = new int[t.length() + 1];
		int v1[] = new int[t.length() + 1];

		for (int i = 0; i < v0.length; i++) {
			v0[i] = i;
		}
		for (int i = 0; i < s.length(); i++) {
			v1[0] = i + 1;
			for (int j = 0; j < t.length(); j++) {
				int cost = (s.charAt(i) == t.charAt(j)) ? 0 : 1;
				v1[j + 1] = Math.min(Math.min(v1[j] + 1, v0[j + 1] + 1), v0[j]
						+ cost);
			}
			for (int j = 0; j < v0.length; j++) {
				v0[j] = v1[j];
			}
		}
		return v1[t.length()];
	}
}
