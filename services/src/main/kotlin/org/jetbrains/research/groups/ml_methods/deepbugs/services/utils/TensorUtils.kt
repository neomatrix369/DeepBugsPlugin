package org.jetbrains.research.groups.ml_methods.deepbugs.services.utils

import org.tensorflow.Tensor
import java.nio.FloatBuffer

object TensorUtils {

    fun getResult(tensor: Tensor<*>): Float {
        val array = Array(tensor.shape()[0].toInt()) { FloatArray(tensor.shape()[1].toInt()) }
        tensor.copyTo(array)
        return array[0][0]
    }

    fun vectorizeListOfArrays(arrayList: List<FloatArray>): Tensor<Float> {
        val resArray = arrayList.reduce { acc, array -> acc + array }
        return Tensor.create(longArrayOf(1, resArray.size.toLong()), FloatBuffer.wrap(resArray))
    }
}