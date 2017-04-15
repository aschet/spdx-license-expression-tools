/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: Apache-2.0
 */

package com.github.aschet.spdx.expression;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.LicenseInfoFactory;
import org.spdx.spdxspreadsheet.InvalidLicenseStringException;

import com.github.aschet.spdx.expression.ExpressionSimplification;

public class ExpressionSimplificationTest {

	@Test
	public void testSimplification() throws InvalidLicenseStringException {
		final AnyLicenseInfo expression = LicenseInfoFactory
				.parseSPDXLicenseString("(((LGPL-3.0+ OR MIT) AND GPL-2.0) OR BSD-3-Clause)");
		final AnyLicenseInfo simplifiedExpression = ExpressionSimplification.simplify(expression);

		assertEquals("(BSD-3-Clause OR (LGPL-3.0+ AND GPL-2.0) OR (MIT AND GPL-2.0))", simplifiedExpression.toString());
	}

}