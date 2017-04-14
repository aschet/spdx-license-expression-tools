/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: Apache-2.0
 */

package tascher.spdx.expression;

import java.util.Collection;

import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.LicenseSet;

/**
 * A matcher for licenses contained in SPDX documents. Has to be used in
 * combination with {@link ExpressionFiltering}. Matching of a
 * {@link LicenseSet} is not supported.
 *
 * @author Thomas Ascher
 */
public interface LicenseMatcher {

	/**
	 * The array of licenses the matcher matches inputs against.
	 *
	 * @return array of licenses
	 */
	AnyLicenseInfo[] getLicenses();

	/**
	 * Determines the way the matcher treats operators of licenses.
	 *
	 * @return filtering for operators
	 */
	ExpressionFiltering.OperatorFilter getOperatorMatchingMode();

	/**
	 * Test if the matcher operates in inverted mode.
	 *
	 * @return true if the matcher operates in inverted mode
	 */
	boolean isInverted();

	/**
	 * Tests if the given licenses matches the internal license list.
	 *
	 * @param license
	 *            license to perform match against
	 * @return true if the given license matches the internal license list
	 */
	boolean matches(AnyLicenseInfo license);

	/**
	 * Sets if the matcher operates in inverted mode. In this mode the match
	 * results will be inverted. This is useful to filter all licenses, except
	 * the ones known by the matcher.
	 *
	 * @param inverted
	 *            true to switch the matcher to inverted mode
	 */
	void setInverted(boolean inverted);

	/**
	 * Set the license to match against.
	 *
	 * @param license
	 *            license to match against
	 */
	void setLicense(AnyLicenseInfo license);

	/**
	 * Set the license to match against.
	 *
	 * @param licenses
	 *            licenses to match against
	 */
	void setLicenses(AnyLicenseInfo[] licenses);

	/**
	 * Set the license to match against.
	 *
	 * @param licenses
	 *            licenses to match against
	 */
	void setLicenses(Collection<AnyLicenseInfo> licenses);

	/**
	 * Sets the way the matcher treats operators during the matching operation.
	 * The matcher will apply a filtering on operators to allow fuzzy matching
	 * e.g. match LGPL-3.0+ even if only LGPL-3.0 is present in the matchers
	 * license list.
	 *
	 * @param operatorMatchingMode
	 *            filtering for operators
	 */
	void setOperatorMatchingMode(ExpressionFiltering.OperatorFilter operatorMatchingMode);

}