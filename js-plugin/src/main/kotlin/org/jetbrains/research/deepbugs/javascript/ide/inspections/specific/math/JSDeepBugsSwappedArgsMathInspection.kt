package org.jetbrains.research.deepbugs.javascript.ide.inspections.specific.math

import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.lang.javascript.psi.JSCallExpression
import org.jetbrains.research.deepbugs.common.datatypes.DataType
import org.jetbrains.research.deepbugs.common.ide.problem.BugDescriptor
import org.jetbrains.research.deepbugs.common.ide.quickfixes.FlipFunctionArgumentsQuickFix
import org.jetbrains.research.deepbugs.javascript.JSResourceBundle
import org.jetbrains.research.deepbugs.javascript.extraction.asIdentifierString
import org.jetbrains.research.deepbugs.javascript.ide.quickfixes.JSIgnoreExpressionQuickFix
import org.jetbrains.research.deepbugs.javascript.model.specific.JSSpecificModel
import org.jetbrains.research.keras.runner.nn.model.sequential.Perceptron

class JSDeepBugsSwappedArgsMathInspection : JSDeepBugsMathCallExprInspection(2) {
    override val model: Perceptron?
        get() = JSSpecificModel.math.swappedArgsModel

    override val ignore: List<String> = listOf("min", "max").map { it.asIdentifierString() }

    override fun createProblemDescriptor(node: JSCallExpression, data: DataType): ProblemDescriptor =
        BugDescriptor(node, createTooltip(node), listOf(
            JSIgnoreExpressionQuickFix(data, node.text),
            FlipFunctionArgumentsQuickFix(JSResourceBundle.message("deepbugs.javascript.flip.args.family"))
        ))

    override fun createTooltip(node: JSCallExpression, vararg params: Any): String =
        JSResourceBundle.message("deepbugs.javascript.math.swapped.args.inspection.warning")

    override fun getShortName() = "JSDeepBugsSwappedArgsMath"

}
