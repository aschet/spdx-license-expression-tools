/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: Apache-2.0
 */

package tascher.spdx.expression;

import java.util.Collection;

import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.LicenseSet;

/**
 * Common base class for concrete for all implementations derived from
 * {@link LicenseSet}.
 *
 * @author Thomas Ascher
 */
abstract class LicenseSetFactoryImpl implements LicenseSetFactory {

	@Override
	public LicenseSet create(final Collection<AnyLicenseInfo> expressions) {
		return create(Utils.toArray(expressions));
	}

}