/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: Apache-2.0
 */

package com.github.aschet.spdx.expression;

import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.DisjunctiveLicenseSet;
import org.spdx.rdfparser.license.LicenseSet;

/**
 * Concrete factory for a {@link DisjunctiveLicenseSet}.
 *
 * @author Thomas Ascher
 */
final class DisjunctiveLicenseSetFactory extends LicenseSetFactoryImpl {

	@Override
	public LicenseSet create(final AnyLicenseInfo[] expressions) {
		return new DisjunctiveLicenseSet(expressions);
	}

}