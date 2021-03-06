package org.jetbrains.research.deepbugs.python.datatypes

import com.jetbrains.python.psi.PyBinaryExpression
import com.jetbrains.python.psi.PyCallExpression
import com.jetbrains.python.psi.resolve.PyResolveContext
import org.jetbrains.research.deepbugs.common.datatypes.BinOp
import org.jetbrains.research.deepbugs.common.datatypes.Call
import org.jetbrains.research.deepbugs.python.extraction.*

fun PyCallExpression.collect(): Call? {
    val name = callee?.extractNodeName() ?: return null

    val args = ArrayList<String>()
    val argTypes = ArrayList<String>()
    for (arg in arguments) {
        args.add(arg.extractNodeName() ?: return null)
        argTypes.add(arg.extractNodeType())
    }

    val base = extractNodeBase()

    val resolved = multiResolveCalleeFunction(PyResolveContext.defaultContext()).firstOrNull()
    var params = resolved?.parameterList?.parameters?.toList()
    if (!params.isNullOrEmpty() && params.first().isSelf && params.size > args.size)
        params = params.drop(1)
    val paramNames = MutableList(args.size) { "" }
    paramNames.forEachIndexed { idx, _ ->
        paramNames[idx] = params?.getOrNull(idx)?.extractNodeName() ?: ""
    }
    return Call(name, args, base, argTypes, paramNames)
}

fun PyBinaryExpression.collect(): BinOp? {
    val leftName = leftExpression?.extractNodeName() ?: return null
    val rightName = rightExpression?.extractNodeName() ?: return null
    val op = extractOperatorText() ?: return null
    val leftType = leftExpression?.extractNodeType() ?: return null
    val rightType = rightExpression?.extractNodeType() ?: return null
    val parentNode = parent.javaClass.simpleName ?: ""
    val grandParentNode = parent.parent.javaClass.simpleName ?: ""
    return BinOp(leftName, rightName, op, leftType, rightType, parentNode, grandParentNode)
}
