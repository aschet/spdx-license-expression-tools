/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: Apache-2.0
 */

package com.github.aschet.spdx.expression;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.LicenseInfoFactory;
import org.spdx.spdxspreadsheet.InvalidLicenseStringException;

import com.github.aschet.spdx.expression.ExpressionSplitting;

public class ExpressionSplittingTest {

	private AnyLicenseInfo expression;

	@Before
	public void setUp() throws Exception {
		expression = LicenseInfoFactory.parseSPDXLicenseString("(((LGPL-3.0+ OR MIT) AND GPL-2.0) OR BSD-3-Clause)");
	}

	@Test
	public void testSplitToConjunctiveSets() throws InvalidLicenseStringException {
		final List<AnyLicenseInfo> expressions = ExpressionSplitting.splitToConjunctiveSets(expression);

		assertEquals(3, expressions.size());
		assertEquals("BSD-3-Clause", expressions.get(0).toString());
		assertEquals("(LGPL-3.0+ AND GPL-2.0)", expressions.get(1).toString());
		assertEquals("(MIT AND GPL-2.0)", expressions.get(2).toString());
	}

	@Test
	public void testSplitToLicenses() throws InvalidLicenseStringException {
		final Set<AnyLicenseInfo> licenses = ExpressionSplitting.splitToLicenses(expression);

		assertEquals(4, licenses.size());
		assertEquals("[BSD-3-Clause, LGPL-3.0+, GPL-2.0, MIT]", licenses.toString());
	}

}