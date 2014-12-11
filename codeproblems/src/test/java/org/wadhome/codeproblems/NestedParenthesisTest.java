package org.wadhome.codeproblems;

import org.junit.Assert;
import org.junit.Test;

public class NestedParenthesisTest
{
	@Test
	public void testIt()
	{
		NestedParenthesis np = new NestedParenthesis();
		Assert.assertEquals("[]", np.showAllValidConfigurations(0));
		Assert.assertEquals("[()]", np.showAllValidConfigurations(1));
		Assert.assertEquals("[(()), ()()]", np.showAllValidConfigurations(2));
		Assert.assertEquals("[((())), (()()), (())(), ()(()), ()()()]", np.showAllValidConfigurations(3));
		Assert.assertEquals("[(((()))), ((()())), ((())()), ((()))(), (()(())), (()()()), (()())(), (())(()), (())()(), ()((())), ()(()()), ()(())(), ()()(()), ()()()()]", np.showAllValidConfigurations(4));
	}
}
