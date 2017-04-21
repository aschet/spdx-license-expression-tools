/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: Apache-2.0
 */

package com.github.aschet.spdx.expression;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.ConjunctiveLicenseSet;
import org.spdx.rdfparser.license.LicenseSet;

/**
 * Splitting operations for SPDX license expressions.
 *
 * @author Thomas Ascher
 */
public final class ExpressionSplitting {

	/**
	 * Split an SPDX license expression in a list of
	 * {@link ConjunctiveLicenseSet} instances. The expression is translated to
	 * the distributed normal form first.
	 *
	 * @param expression
	 *            SPDX license expression to be splitted
	 * @return a list of {@link ConjunctiveLicenseSet} instances
	 */
	public static List<AnyLicenseInfo> splitToConjunctiveSets(final AnyLicenseInfo expression) {
		Utils.ensureNotNull(expression);

		final List<AnyLicenseInfo> childExpressions = new ArrayList<>();
		final AnyLicenseInfo expressionDNF = ExpressionSimplification.simplify(expression);
		if (TypeInfo.isDisjuntiveSet(expressionDNF)) {
			for (final AnyLicenseInfo childLicenseInfo : ((LicenseSet)expressionDNF).getMembers()) {
				childExpressions.add(childLicenseInfo);
			}
		} else {
			childExpressions.add(expressionDNF);
		}

		return childExpressions;
	}

	/**
	 * Extracts all licenses present in an SPDX license expression.
	 *
	 * @param expression
	 *            SPDX license expressions to extract the licenses from
	 * @return a set of licenses present in the input expression
	 */
	public static Set<AnyLicenseInfo> splitToLicenses(final AnyLicenseInfo expression) {
		final Set<AnyLicenseInfo> licenses = new LinkedHashSet<>();

		for (final AnyLicenseInfo conjunctiveSet : splitToConjunctiveSets(expression)) {
			if (TypeInfo.isSet(conjunctiveSet)) {
				for (final AnyLicenseInfo license : ((LicenseSet) conjunctiveSet).getMembers()) {
					licenses.add(license);
				}
			} else {
				licenses.add(conjunctiveSet);
			}
		}

		return licenses;
	}

}