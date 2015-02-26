package org.wadhome.codeproblems;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Given a set, output all subsets
public class Subsets
{

	public static final BigInteger ONE = BigInteger.ONE;
	public static final BigInteger ZERO = BigInteger.ZERO;

	public static Set<Set<String>> getAllSubsets(Set<String> set)
	{
		List<String> orderedElements = new ArrayList<String>(set);
		Set<Set<String>> subsets = new HashSet<Set<String>>();

		int numBits = orderedElements.size();
		BigInteger allBitsHigh = ONE.shiftLeft(numBits).subtract(ONE);

		for (BigInteger i = ZERO; i.compareTo(allBitsHigh) <= 0; i = i.add(ONE))
		{
			Set<String> subset = new HashSet<String>();
			BigInteger temp = i;
			int elementPosition = 0;
			while (temp.compareTo(ZERO) > 0)
			{
				if ((temp.and(ONE).compareTo(ONE)) == 0)
					subset.add(orderedElements.get(elementPosition));
				temp = temp.shiftRight(1);
				elementPosition++;
			}
			subsets.add(subset);
		}
		return subsets;
	}
}
