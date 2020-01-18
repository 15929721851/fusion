package io.github.kenneycode.fusion.program

import io.github.kenneycode.fusion.common.Shader

/**
 *
 * Coded by kenney
 *
 * http://www.github.com/kenneycode/fusion
 *
 * GL Program缓存池
 *
 */

object GLProgramPool {

    private val cache = mutableMapOf<Shader, GLProgram>()

    /**
     *
     * 获取指定Shader对应的GL Program，如果缓存池中没有，将创建
     *
     * @param shader shader
     *
     * @return Shader对应的GL Program
     *
     */
    fun obtainGLProgram(shader: Shader): GLProgram {
        if (!cache.containsKey(shader)) {
            cache[shader] = GLProgram(shader)
        } else {
            cache[shader]!!.increaseRef()
        }
        return cache[shader]!!
    }

    /**
     *
     * 将GL Program放回cache
     *
     * @param glProgram 要放回的GL Program
     *
     */
    fun releaseGLProgram(glProgram: GLProgram) {
        cache[glProgram.shader] = glProgram
    }

    /**
     *
     * 释放资源
     *
     */
    fun release() {
        cache.values.forEach { glProgram ->
            glProgram.release()
        }
        cache.clear()
    }

}

