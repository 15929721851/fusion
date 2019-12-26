package io.github.kenneycode.fusion.parameter

import android.opengl.GLES20
import io.github.kenneycode.fusion.util.Util

/**
 *
 * Coded by kenney
 *
 * http://www.github.com/kenneycode/fusion
 *
 * Shader uniform参数基类
 *
 */

abstract class UniformParameter(key : String) : Parameter(key) {

    /**
     *
     * 绑定参数
     *
     * @param program GL Program
     *
     */
    override fun bind(program: Int) {
        if (location < 0) {
            location = GLES20.glGetUniformLocation(program, key)
        }
        Util.assert(location >= 0)
        onBind(location)
    }

}
