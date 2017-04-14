/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: Apache-2.0
 */

package tascher.spdx.expression;

import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.ConjunctiveLicenseSet;
import org.spdx.rdfparser.license.DisjunctiveLicenseSet;
import org.spdx.rdfparser.license.LicenseSet;
import org.spdx.rdfparser.license.OrLaterOperator;
import org.spdx.rdfparser.license.SpdxNoAssertionLicense;
import org.spdx.rdfparser.license.SpdxNoneLicense;
import org.spdx.rdfparser.license.WithExceptionOperator;

/**
 * Various type information queries for SPDX license expressions.
 *
 * @author Thomas Ascher
 */
public final class TypeInfo {

	/**
	 * Tests if an SPDX license expressions is a {@link ConjunctiveLicenseSet}.
	 *
	 * @param expression
	 *            SPDX license expressions to test
	 * @return true if the test succeeds
	 */
	public static boolean isConjuntiveSet(final AnyLicenseInfo expression) {
		return expression instanceof ConjunctiveLicenseSet;
	}

	/**
	 * Tests if an SPDX license expressions is a {@link DisjunctiveLicenseSet}.
	 *
	 * @param expression
	 *            SPDX license expressions to test
	 * @return true if the test succeeds
	 */
	public static boolean isDisjuntiveSet(final AnyLicenseInfo expression) {
		return expression instanceof DisjunctiveLicenseSet;
	}

	/**
	 * Tests if an SPDX license expressions is a {@link OrLaterOperator}.
	 *
	 * @param expression
	 *            SPDX license expressions to test
	 * @return true if the test succeeds
	 */
	public static boolean isOrLaterOperator(final AnyLicenseInfo expression) {
		return expression instanceof OrLaterOperator;
	}

	/**
	 * Tests if an SPDX license expressions is a {@link LicenseSet}.
	 *
	 * @param expression
	 *            SPDX license expressions to test
	 * @return true if the test succeeds
	 */
	public static boolean isSet(final AnyLicenseInfo expression) {
		return expression instanceof LicenseSet;
	}

	/**
	 * Tests if an SPDX license expressions is not a {@link SpdxNoneLicense} or
	 * a {@link SpdxNoAssertionLicense}.
	 *
	 * @param expression
	 *            SPDX license expressions to test
	 * @return true if the test succeeds
	 */
	public static boolean isValid(final AnyLicenseInfo expression) {
		return !(expression == null || expression instanceof SpdxNoneLicense
				|| expression instanceof SpdxNoAssertionLicense);
	}

	/**
	 * Tests if an SPDX license expressions is a {@link WithExceptionOperator}.
	 *
	 * @param expression
	 *            SPDX license expressions to test
	 * @return true if the test succeeds
	 */
	public static boolean isWithExceptionOperator(final AnyLicenseInfo expression) {
		return expression instanceof WithExceptionOperator;
	}

}