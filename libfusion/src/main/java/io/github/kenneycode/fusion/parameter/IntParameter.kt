package io.github.kenneycode.fusion.parameter

import android.opengl.GLES20.glUniform1i
import io.github.kenneycode.fusion.common.glCheck

/**
 *
 * Coded by kenney
 *
 * http://www.github.com/kenneycode/fusion
 *
 * Shader int参数
 *
 */

class IntParameter(key: String, private var value: Int) : UniformParameter(key) {

    override fun onBind(location: Int) {
        glCheck { glUniform1i(location, value) }
    }

    override fun update(value: Any) {
        (value as? Int)?.let {
            this.value = it
        }
    }

}
