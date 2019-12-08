package org.jetbrains.research.deepbugs.common.datatypes

import org.jetbrains.research.deepbugs.common.utils.Mapping

abstract class Call(
    private val callee: String,
    private val arguments: List<String>,
    private val base: String,
    private val argumentTypes: List<String>,
    private val parameters: List<String>,
    @Suppress("unused") private val src: String
) : DataType {
    override val text: String = "$base.$callee(${arguments.joinToString(",")})"

    protected fun vectorize(token: Mapping, type: Mapping): List<Float>? {
        val nameVector = token.get(callee) ?: return null
        val argVectors = arguments.map { arg -> token.get(arg) ?: return null }.reduce { acc, arg -> acc + arg }
        val baseVector = token.get(base) ?: (FloatArray(200) { 0.0f }).toList()
        val typeVectors = argumentTypes.map { argType -> type.get(argType) ?: return null }.reduce { acc, argType -> acc + argType }
        val paramVectors = parameters.map { param -> token.get(param) ?: FloatArray(200) { 0.0f }.toList() }.reduce { acc, param -> acc + param }
        return listOf(nameVector, argVectors, baseVector, typeVectors, paramVectors).flatten()
    }
}
