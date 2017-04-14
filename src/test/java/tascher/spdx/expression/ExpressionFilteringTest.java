/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: Apache-2.0
 */

package tascher.spdx.expression;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.LicenseInfoFactory;
import org.spdx.spdxspreadsheet.InvalidLicenseStringException;

import tascher.spdx.expression.ExpressionFiltering.OperatorFilter;

public class ExpressionFilteringTest {

	@Test
	public void testLicenseFiltering() throws InvalidLicenseStringException {
		final AnyLicenseInfo expression = LicenseInfoFactory
				.parseSPDXLicenseString("((LGPL-3.0+ AND MIT) AND BSD-3-Clause)");

		final LicenseMatcher matcher = ExpressionFiltering.createLicenseMatcher();
		matcher.setLicense(LicenseInfoFactory.parseSPDXLicenseString("LGPL-3.0"));
		matcher.setOperatorMatchingMode(ExpressionFiltering.OperatorFilter.FILTER_ALL);

		assertEquals("(MIT AND BSD-3-Clause)", ExpressionFiltering.filterLicenses(expression, matcher).toString());

		matcher.setInverted(true);

		assertEquals("LGPL-3.0+", ExpressionFiltering.filterLicenses(expression, matcher).toString());
	}

	@Test
	public void testOperatorFiltering() throws InvalidLicenseStringException {
		final AnyLicenseInfo expression = LicenseInfoFactory
				.parseSPDXLicenseString("LGPL-3.0+ WITH DigiRule-FOSS-exception AND MIT");

		final AnyLicenseInfo filteredExpression = ExpressionFiltering.filterOperators(expression, OperatorFilter.FILTER_ALL);

		assertEquals("(LGPL-3.0 AND MIT)", filteredExpression.toString());
	}
}
