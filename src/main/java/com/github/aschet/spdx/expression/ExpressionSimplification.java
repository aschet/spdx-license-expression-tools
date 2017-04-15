/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: Apache-2.0
 */

package com.github.aschet.spdx.expression;

import org.spdx.rdfparser.license.AnyLicenseInfo;

import com.bpodgursky.jbool_expressions.rules.RuleSet;

/**
 * Simplification operations for SPDX license expressions.
 *
 * @author Thomas Ascher
 */
public final class ExpressionSimplification {

	/**
	 * Simplifies an SPDX license expressions and converts it to the distributed
	 * normal form e.g. (((LGPL-3.0+ OR MIT) AND GPL-2.0) OR BSD-3-Clause) to
	 * (BSD-3-Clause OR (LGPL-3.0+ AND GPL-2.0) OR (MIT AND GPL-2.0)).
	 *
	 * @param expression
	 *            SPDX license expression to simplify
	 * @return a SPDX license expression in distributed normal form
	 */
	static public AnyLicenseInfo simplify(final AnyLicenseInfo expression) {
		return Converter.toAnyLicenseInfo(RuleSet.toDNF(RuleSet.simplify(Converter.toJBoolExpression(expression))));
	}

}