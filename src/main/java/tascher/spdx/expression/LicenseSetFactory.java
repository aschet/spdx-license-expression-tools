/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: Apache-2.0
 */

package tascher.spdx.expression;

import java.util.Collection;

import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.LicenseSet;

/**
 * Common factory interface for all implementations derived from
 * {@link LicenseSet}.
 *
 * @author Thomas Ascher
 */
interface LicenseSetFactory {

	/**
	 * Creates a concrete {@link LicenseSet}.
	 *
	 * @param expressions
	 *            an array of SPDX license expressions
	 * @return a set of SPDX license expressions
	 */
	LicenseSet create(AnyLicenseInfo[] expressions);

	/**
	 * Creates a concrete {@link LicenseSet}.
	 *
	 * @param expressions
	 *            a {@link Collection} SPDX license expressions
	 * @return a set of SPDX license expressions
	 */
	LicenseSet create(Collection<AnyLicenseInfo> expressions);

}