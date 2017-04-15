/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: Apache-2.0
 */

package com.github.aschet.spdx.expression;

import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.ConjunctiveLicenseSet;
import org.spdx.rdfparser.license.LicenseSet;

/**
 * Concrete factory for a {@link ConjunctiveLicenseSet}.
 *
 * @author Thomas Ascher
 */
final class ConjunctiveLicenseSetFactory extends LicenseSetFactoryImpl {

	@Override
	public LicenseSet create(final AnyLicenseInfo[] expressions) {
		return new ConjunctiveLicenseSet(expressions);
	}

}