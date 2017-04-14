/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: Apache-2.0
 */

package tascher.spdx.expression;

import java.util.ArrayList;
import java.util.List;

import org.spdx.rdfparser.InvalidSPDXAnalysisException;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.LicenseSet;
import org.spdx.rdfparser.license.OrLaterOperator;
import org.spdx.rdfparser.license.SimpleLicensingInfo;
import org.spdx.rdfparser.license.SpdxNoneLicense;
import org.spdx.rdfparser.license.WithExceptionOperator;

/**
 * Filtering operations for SPDX license operations.
 *
 * @author Thomas Ascher
 */
public final class ExpressionFiltering {

	/**
	 * Filter or operator filtering. This can to be used to remove the
	 * {@link OrLaterOperator} or the {@link WithExceptionOperator} from and
	 * SPDX license expression.
	 *
	 * @author Thomas Ascher
	 */
	public enum OperatorFilter {
		FILTER_ALL, FILTER_NONE, FILTER_OR_LATER, FILTER_WITH_EXCEPTION
	}

	/**
	 * Creates a matcher for license filtering.
	 *
	 * @return concrete matcher for license filtering
	 */
	public static LicenseMatcher createLicenseMatcher() {
		return new LicenseMatcherImpl();
	}

	/**
	 * Filters licenses from an SPDX license expression.
	 *
	 * @param expression
	 *            SPDX license expression to filter from
	 * @param matcher
	 *            a matcher to specify which licenses to filter
	 * @return filtered SPDX license expression
	 */
	public static AnyLicenseInfo filterLicenses(final AnyLicenseInfo expression, final LicenseMatcher matcher) {
		return filterLicensesImpl(expression.clone(), matcher);
	}

	/**
	 * Filters licenses from an SPDX license expression.
	 *
	 * @param expression
	 *            SPDX license expression to filter from
	 * @param matcher
	 *            a matcher to specify which licenses to filter
	 * @return filtered SPDX license expression
	 */
	private static AnyLicenseInfo filterLicensesImpl(final AnyLicenseInfo expression, final LicenseMatcher matcher) {
		if (!TypeInfo.isSet(expression)) {
			if (matcher.matches(expression)) {
				return new SpdxNoneLicense();
			} else {
				return expression;
			}
		} else {
			final LicenseSet set = (LicenseSet) expression;
			final List<AnyLicenseInfo> modifiedSet = new ArrayList<>();

			for (final AnyLicenseInfo childExpressions : set.getMembers()) {
				final AnyLicenseInfo modifiedChildExpression = filterLicensesImpl(childExpressions, matcher);
				if (TypeInfo.isValid(modifiedChildExpression)) {
					modifiedSet.add(modifiedChildExpression);
				}
			}

			if (modifiedSet.isEmpty()) {
				return new SpdxNoneLicense();
			} else {
				try {
					if (modifiedSet.size() > 1) {
						set.setMembers(Utils.toArray(modifiedSet));
						return set;
					} else {
						return modifiedSet.get(0);
					}
				} catch (final InvalidSPDXAnalysisException e) {
					return new SpdxNoneLicense();
				}
			}
		}
	}

	/**
	 * Filters operators from an SPDX license expressions based on the given
	 * filter option.
	 *
	 * @param expression
	 *            SPDX license expression to filter from
	 * @param filter
	 *            filter option for operator removal
	 * @return filtered SPDX license expression
	 */
	public static AnyLicenseInfo filterOperators(final AnyLicenseInfo expression, final OperatorFilter filter) {
		return filterOperatorsImpl(expression.clone(), filter);
	}

	/**
	 * Filters operators from an SPDX license expressions based on the given
	 * filter option.
	 *
	 * @param expression
	 *            SPDX license expression to filter from
	 * @param filter
	 *            filter option for operator removal
	 * @return filtered SPDX license expression
	 */
	private static AnyLicenseInfo filterOperatorsImpl(final AnyLicenseInfo expression, final OperatorFilter filter) {
		if (filter == OperatorFilter.FILTER_NONE) {
			return expression;
		} else {
			if (TypeInfo.isSet(expression)) {
				final LicenseSet set = (LicenseSet) expression;
				final List<AnyLicenseInfo> modifiedChildExpressions = new ArrayList<>();

				for (final AnyLicenseInfo childExpression : ((LicenseSet) expression).getMembers()) {
					modifiedChildExpressions.add(filterOperatorsImpl(childExpression, filter));
				}

				try {
					set.setMembers(Utils.toArray(modifiedChildExpressions));
				} catch (final InvalidSPDXAnalysisException e) {
				}

				return set;
			} else {
				if (TypeInfo.isWithExceptionOperator(expression)) {
					final WithExceptionOperator operator = (WithExceptionOperator) expression;
					if (filter == OperatorFilter.FILTER_ALL || filter == OperatorFilter.FILTER_WITH_EXCEPTION) {
						return filterOperatorsImpl(operator.getLicense(), filter);
					} else {
						try {
							operator.setLicense(filterOperatorsImpl(operator.getLicense(), filter));
						} catch (final InvalidSPDXAnalysisException e) {
						}
						return operator;
					}

				} else if (TypeInfo.isOrLaterOperator(expression)) {
					final OrLaterOperator operator = (OrLaterOperator) expression;
					if (filter == OperatorFilter.FILTER_ALL || filter == OperatorFilter.FILTER_OR_LATER) {
						return filterOperatorsImpl(operator.getLicense(), filter);
					} else {
						try {
							operator.setLicense(
									(SimpleLicensingInfo) filterOperatorsImpl(operator.getLicense(), filter));
						} catch (final InvalidSPDXAnalysisException e) {
						}
						return operator;
					}
				} else {
					return expression;
				}
			}
		}
	}

}