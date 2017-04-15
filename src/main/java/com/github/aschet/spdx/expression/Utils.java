/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: Apache-2.0
 */

package com.github.aschet.spdx.expression;

import java.security.InvalidParameterException;
import java.util.Collection;

import org.spdx.rdfparser.license.AnyLicenseInfo;

/**
 * Various utility methods.
 *
 * @author Thomas Ascher
 */
final class Utils {

	/**
	 * Tests if a given parameter is not null and causes an
	 * {@link InvalidParameterException} if null is detected.
	 *
	 * @param parameters
	 *            parameters to test for null
	 */
	public static void ensureNotNull(final Object... parameters) {
		for (final Object parameter : parameters) {
			if (parameter == null) {
				throw new InvalidParameterException("Parameter is not allowed to be of value null.");
			}
		}
	}

	/**
	 * Tests if a given parameter is not a
	 * {@link org.spdx.rdfparser.license.LicenseSet} and causes an
	 * {@link InvalidParameterException} if a set is detected.
	 *
	 * @param expression
	 *            license expression to test
	 */
	public static void ensureNotSet(final AnyLicenseInfo expression) {
		if (TypeInfo.isSet(expression)) {
			throw new InvalidParameterException("License sets are not supported by this operation.");
		}
	}

	/**
	 * Converts a collection of SPDX license expressions to an array.
	 *
	 * @param expressions
	 *            collection of SPDX license expressions to convert
	 * @return an array of SPDX license expressions
	 */
	public static AnyLicenseInfo[] toArray(final Collection<AnyLicenseInfo> expressions) {
		return expressions.toArray(new AnyLicenseInfo[expressions.size()]);
	}

}