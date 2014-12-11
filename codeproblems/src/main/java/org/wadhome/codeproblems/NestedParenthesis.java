package org.wadhome.codeproblems;

import java.util.ArrayList;
import java.util.List;

public class NestedParenthesis
{

	List<String> entries;

	public String showAllValidConfigurations(int numPairs)
	{
		entries = new ArrayList<String>();
		accumulateEntries(numPairs, "", 0, 0);
		return format(entries);
	}

	void accumulateEntries(
			int totalPairsRequested,
			String current,
			int numLeftsUsed,
			int numRightsUsed)
	{
		if (numLeftsUsed > totalPairsRequested || numRightsUsed > totalPairsRequested)
			// Invalid situation detected, just drop this entry.
			return;

		if (numLeftsUsed == totalPairsRequested && numRightsUsed == totalPairsRequested)
			// We got a valid entry, let's keep it!
			entries.add(current);
		else
		{
			// We can always try another left paren.
			accumulateEntries(
					totalPairsRequested,
					current + "(",
					numLeftsUsed + 1,
					numRightsUsed);

			// Only try a right paren if there are more left parens already used.
			if (numLeftsUsed > numRightsUsed)
				accumulateEntries(
						totalPairsRequested,
						current + ")",
						numLeftsUsed,
						numRightsUsed + 1);
		}
	}

	String format(List<String> entries)
	{
		StringBuilder builder = new StringBuilder();
		for (String entry : entries)
		{
			if (builder.length() > 0)
				builder.append(",");
			builder.append(entry);
		}
		return builder.toString();
	}
}
