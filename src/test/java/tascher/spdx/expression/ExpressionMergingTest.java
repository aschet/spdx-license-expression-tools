/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: Apache-2.0
 */

package tascher.spdx.expression;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.LicenseInfoFactory;
import org.spdx.spdxspreadsheet.InvalidLicenseStringException;

public class ExpressionMergingTest {

	private AnyLicenseInfo expression1;

	private AnyLicenseInfo expression2;

	@Before
	public void setUp() throws Exception {
		expression1 = LicenseInfoFactory.parseSPDXLicenseString("LGPL-3.0+ AND MIT");
		expression2 = LicenseInfoFactory.parseSPDXLicenseString("BSD-3-Clause");
	}

	@Test
	public void testAndJoin() throws InvalidLicenseStringException {
		final AnyLicenseInfo expression = ExpressionMerging.andJoin(expression1, expression2);
		assertEquals("((LGPL-3.0+ AND MIT) AND BSD-3-Clause)", expression.toString());
	}

	@Test
	public void testOrJoin() throws InvalidLicenseStringException {
		final AnyLicenseInfo expression = ExpressionMerging.orJoin(expression1, expression2);
		assertEquals("((LGPL-3.0+ AND MIT) OR BSD-3-Clause)", expression.toString());
	}

}