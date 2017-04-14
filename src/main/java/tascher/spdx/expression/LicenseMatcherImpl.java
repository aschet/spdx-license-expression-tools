/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: Apache-2.0
 */

package tascher.spdx.expression;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.spdx.rdfparser.license.AnyLicenseInfo;

import tascher.spdx.expression.ExpressionFiltering.OperatorFilter;

/**
 * Concrete license matcher implementation.
 *
 * @author Thomas Ascher
 */
class LicenseMatcherImpl implements LicenseMatcher {

	/**
	 * Status of the inverted mode.
	 */
	private boolean inverted = false;

	/**
	 * Array of licenses to match against.
	 */
	private AnyLicenseInfo[] licenses;

	/**
	 * A transformed variant of the internal license array with operator
	 * filtering applied.
	 */
	private final Set<AnyLicenseInfo> licensesToMatch = new LinkedHashSet<>();

	/**
	 * Status of the operator filtering for fuzzy matching.
	 */
	private ExpressionFiltering.OperatorFilter operatorMatchingMode = OperatorFilter.FILTER_NONE;

	@Override
	public AnyLicenseInfo[] getLicenses() {
		return licenses;
	}

	@Override
	public OperatorFilter getOperatorMatchingMode() {
		return operatorMatchingMode;
	}

	@Override
	public boolean isInverted() {
		return inverted;
	}

	@Override
	public boolean matches(final AnyLicenseInfo license) {
		Utils.ensureNotNull(license);
		Utils.ensureNotSet(license);

		boolean result = matchesImpl(license);
		if (isInverted()) {
			result = !result;
		}
		return result;
	}

	/**
	 * Perform actual matching operation.
	 *
	 * @param license
	 *            license to perform match against
	 * @return true if the given license matches the internal license list
	 */
	private boolean matchesImpl(final AnyLicenseInfo license) {
		final AnyLicenseInfo filterdLicense = ExpressionFiltering.filterOperators(license, operatorMatchingMode);

		for (final AnyLicenseInfo licenseToMatch : licensesToMatch) {
			if (licenseToMatch.toString().equals(filterdLicense.toString())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void setInverted(final boolean inverted) {
		this.inverted = inverted;
	}

	@Override
	public void setLicense(final AnyLicenseInfo license) {
		Utils.ensureNotNull(license);

		final AnyLicenseInfo[] licenses = new AnyLicenseInfo[1];
		licenses[0] = license;
		setLicenses(licenses);
	}

	@Override
	public void setLicenses(final AnyLicenseInfo[] licenses) {
		Utils.ensureNotNull((Object[]) licenses);

		this.licenses = licenses;
		updateMatchingSet();
	}

	@Override
	public void setLicenses(final Collection<AnyLicenseInfo> licenses) {
		Utils.ensureNotNull(licenses);

		setLicenses(Utils.toArray(licenses));
	}

	@Override
	public void setOperatorMatchingMode(final OperatorFilter operatorMatchingMode) {
		this.operatorMatchingMode = operatorMatchingMode;
		updateMatchingSet();
	}

	/**
	 * Apply filtering operations on internal license array for fuzzy operator
	 * matching.
	 */
	private void updateMatchingSet() {
		licensesToMatch.clear();

		if (licenses != null) {
			for (final AnyLicenseInfo license : licenses) {
				licensesToMatch.add(ExpressionFiltering.filterOperators(license, operatorMatchingMode));
			}
		}
	}
}
