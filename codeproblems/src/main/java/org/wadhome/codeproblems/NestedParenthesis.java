package org.wadhome.codeproblems;

import java.util.ArrayList;
import java.util.List;

// Given n, emit all valid configuration of n pairs of parenthesis
public class NestedParenthesis
{

	List<String> entries;
	int totalPairsRequested;

	public String showAllValidConfigurations(int numPairs)
	{
		entries = new ArrayList<String>();
		totalPairsRequested = numPairs;
		buildEntries("", 0, 0);
		return entries.toString();
	}

	void buildEntries(String entry, int numLeftsUsed, int numRightsUsed)
	{
		if (numLeftsUsed > totalPairsRequested || numRightsUsed > numLeftsUsed)
			// Invalid situation detected, just drop this entry.
			return;

		if (numRightsUsed == totalPairsRequested)
			// We got a valid entry, let's keep it!
			entries.add(entry);
		else
		{
			buildEntries(entry + "(", numLeftsUsed + 1, numRightsUsed);
			buildEntries(entry + ")", numLeftsUsed, numRightsUsed + 1);
		}
	}
}
