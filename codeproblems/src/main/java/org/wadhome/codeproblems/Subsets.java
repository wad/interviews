package org.wadhome.codeproblems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Given a set, output all subsets
public class Subsets
{

	public static Set<Set<String>> getAllSubsets(Set<String> set)
	{
		List<String> orderedElements = new ArrayList<String>(set);
		Set<Set<String>> subsets = new HashSet<Set<String>>();

		int numBits = orderedElements.size();
		int allBitsHigh = (1 << numBits) - 1;

		for (int i = 0; i <= allBitsHigh; i++)
		{
			Set<String> subset = new HashSet<String>();
			int temp = i;
			int elementPosition = 0;
			while (temp > 0)
			{
				if ((temp & 1) == 1)
					subset.add(orderedElements.get(elementPosition));
				temp >>= 1;
				elementPosition++;
			}
			subsets.add(subset);
		}
		return subsets;
	}
}
