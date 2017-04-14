/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: Apache-2.0
 */

package tascher.spdx.expression;

import java.util.ArrayList;
import java.util.List;

import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.ConjunctiveLicenseSet;
import org.spdx.rdfparser.license.DisjunctiveLicenseSet;
import org.spdx.rdfparser.license.LicenseSet;
import org.spdx.rdfparser.license.SpdxNoneLicense;

/**
 * Merging operations for SPDX license expressions.
 *
 * @author Thomas Ascher
 */
public final class ExpressionMerging {

	/**
	 * Join SPDX license expressions via an {@link ConjunctiveLicenseSet}. Non
	 * valid licenses will be filtered out.
	 *
	 * @param expressions
	 *            SPDX license expressions to join
	 * @return a {@link ConjunctiveLicenseSet} of SPDX license expressions or
	 *         {@link SpdxNoneLicense} if no valid license if present
	 */
	public static AnyLicenseInfo andJoin(final AnyLicenseInfo... expressions) {
		return join(new ConjunctiveLicenseSetFactory(), expressions);
	}

	/**
	 * Join SPDX license expressions.
	 *
	 * @param setFactory
	 *            factory to create the resulting {@link LicenseSet} type
	 * @param expressions
	 *            SPDX license expressions to join
	 * @return a concrete set of SPDX license expressions or
	 *         {@link SpdxNoneLicense} if no valid license if present
	 */
	private static AnyLicenseInfo join(final LicenseSetFactory setFactory, final AnyLicenseInfo... expressions) {
		Utils.ensureNotNull(setFactory, expressions);

		final List<AnyLicenseInfo> validExpressions = new ArrayList<>();
		for (final AnyLicenseInfo expression : expressions) {
			if (TypeInfo.isValid(expression)) {
				validExpressions.add(expression);
			}
		}

		if (validExpressions.isEmpty()) {
			return new SpdxNoneLicense();
		} else if (validExpressions.size() == 1) {
			return validExpressions.get(0);
		} else {
			return setFactory.create(validExpressions);
		}
	}

	/**
	 * Join SPDX license expressions via an {@link DisjunctiveLicenseSet}. Non
	 * valid licenses will be filtered out.
	 *
	 * @param expressions
	 *            SPDX license expressions to join
	 * @return a {@link DisjunctiveLicenseSet} of SPDX license expressions or
	 *         {@link SpdxNoneLicense} if no valid license if present
	 */
	public static AnyLicenseInfo orJoin(final AnyLicenseInfo... expressions) {
		return join(new DisjunctiveLicenseSetFactory(), expressions);
	}

}
