/**
 * Copyright 2017 Thomas Ascher <thomas.ascher@gmx.at>
 * SPDX-License-Identifier: Apache-2.0
 */

package tascher.spdx.expression;

import java.util.ArrayList;
import java.util.List;

import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.LicenseSet;

import com.bpodgursky.jbool_expressions.And;
import com.bpodgursky.jbool_expressions.Expression;
import com.bpodgursky.jbool_expressions.NExpression;
import com.bpodgursky.jbool_expressions.Or;
import com.bpodgursky.jbool_expressions.Variable;

/**
 * A converter to enable SPDX license expression to be passed to the
 * jbool_expressions library which can be used to simplify license expressions.
 *
 * @author Thomas Ascher
 */
final class Converter {

	/**
	 * Converts a jbool_expressions to a SPDX license expression.
	 *
	 * @param expression
	 *            a jbool_expression
	 * @return a SPDX license expression
	 */
	public static AnyLicenseInfo toAnyLicenseInfo(final Expression<AnyLicenseInfo> expression) {
		Utils.ensureNotNull(expression);

		if (expression instanceof NExpression<?>) {
			final List<AnyLicenseInfo> childExpressions = new ArrayList<>();

			for (final Expression<AnyLicenseInfo> jboolChildExpression : ((NExpression<AnyLicenseInfo>) expression)
					.getChildren()) {
				childExpressions.add(toAnyLicenseInfo(jboolChildExpression));
			}

			if (expression instanceof And<?>) {
				return new ConjunctiveLicenseSetFactory().create(childExpressions);
			} else {
				return new DisjunctiveLicenseSetFactory().create(childExpressions);
			}
		} else if (expression instanceof Variable<?>) {
			return ((Variable<AnyLicenseInfo>) expression).getValue();
		} else {
			throw new IllegalArgumentException("Non supported expression type: " + expression.getExprType());
		}
	}

	/**
	 * Converts a SPDX license expression to a jbool_expressions
	 *
	 * @param expression
	 *            a SPDX license expression
	 * @return a jbool_expression
	 */
	public static Expression<AnyLicenseInfo> toJBoolExpression(final AnyLicenseInfo expression) {
		Utils.ensureNotNull(expression);

		if (TypeInfo.isSet(expression)) {
			final List<Expression<AnyLicenseInfo>> childExpressions = new ArrayList<>();
			for (final AnyLicenseInfo childLicenseExpression : ((LicenseSet) expression).getMembers()) {
				childExpressions.add(toJBoolExpression(childLicenseExpression));
			}

			if (TypeInfo.isConjuntiveSet(expression)) {
				return And.of(childExpressions);
			} else {
				return Or.of(childExpressions);
			}

		} else {
			return Variable.of(expression.clone());
		}
	}

}
