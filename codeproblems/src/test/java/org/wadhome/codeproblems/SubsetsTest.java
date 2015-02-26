package org.wadhome.codeproblems;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class SubsetsTest
{

	@Test
	public void testEmptySet()
	{
		Set<String> set = new HashSet<String>();
		Set<Set<String>> subsets = Subsets.getAllSubsets(set);
		Assert.assertEquals(1, subsets.size());
	}

	@Test
	public void testSingle()
	{
		Set<String> set = new HashSet<String>();
		set.add("a");
		Set<Set<String>> subsets = Subsets.getAllSubsets(set);
		System.out.println(subsets);
		Assert.assertEquals(2, subsets.size());
	}

	@Test
	public void testTwo()
	{
		Set<String> set = new HashSet<String>();
		set.add("a");
		set.add("b");
		Set<Set<String>> subsets = Subsets.getAllSubsets(set);
		System.out.println(subsets);
		Assert.assertEquals(4, subsets.size());
	}

	@Test
	public void testThree()
	{
		Set<String> set = new HashSet<String>();
		set.add("a");
		set.add("b");
		set.add("c");
		Set<Set<String>> subsets = Subsets.getAllSubsets(set);
		System.out.println(subsets);
		Assert.assertEquals(8, subsets.size());
	}
}
