package org.jetbrains.research.deepbugs.python.ide.quickfixes

import com.intellij.codeInsight.intention.PriorityAction
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.icons.AllIcons
import com.intellij.openapi.command.undo.BasicUndoableAction
import com.intellij.openapi.command.undo.UndoManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Iconable
import org.jetbrains.research.deepbugs.common.datatypes.DataType
import org.jetbrains.research.deepbugs.common.ide.fus.collectors.counter.DeepBugsCounterCollector
import org.jetbrains.research.deepbugs.python.PyDeepBugsConfig
import org.jetbrains.research.deepbugs.python.PyResourceBundle
import javax.swing.Icon

class PyIgnoreExpressionQuickFix(private val expr: DataType, private val displayText: String) : LocalQuickFix, Iconable, PriorityAction {
    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val ignore = object : BasicUndoableAction(descriptor.psiElement?.containingFile?.virtualFile) {
            override fun redo() = PyDeepBugsConfig.ignoreExpression(expr)
            override fun undo() = PyDeepBugsConfig.considerExpression(expr)
        }
        ignore.redo()
        DeepBugsCounterCollector.checkDisabled(project, PyDeepBugsConfig.get().userDisabledChecks.size)
        UndoManager.getInstance(project).undoableActionPerformed(ignore)
    }

    override fun getIcon(flags: Int): Icon = AllIcons.Actions.Cancel
    override fun getPriority(): PriorityAction.Priority = PriorityAction.Priority.LOW

    override fun getFamilyName(): String = PyResourceBundle.message("deepbugs.python.ignore.family")
    override fun getName(): String = PyResourceBundle.message("deepbugs.python.ignore.quickfix", displayText)
}
