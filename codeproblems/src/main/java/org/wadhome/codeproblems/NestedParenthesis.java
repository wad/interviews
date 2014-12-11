package org.wadhome.codeproblems;

import java.util.ArrayList;
import java.util.List;

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
		if (numLeftsUsed > totalPairsRequested || numRightsUsed > totalPairsRequested)
			// Invalid situation detected, just drop this entry.
			return;

		if (numLeftsUsed == totalPairsRequested && numRightsUsed == totalPairsRequested)
			// We got a valid entry, let's keep it!
			entries.add(entry);
		else
		{
			// We can always try another left paren.
			buildEntries(entry + "(", numLeftsUsed + 1, numRightsUsed);

			// Only try a right paren if there are more left parens already in this entry.
			if (numLeftsUsed > numRightsUsed)
				buildEntries(entry + ")", numLeftsUsed, numRightsUsed + 1);
		}
	}
}
