package com.codesniper.plugin.asm

/**
 * Represents a class pool
 *
 * @author johnsonlee
 */
interface KlassPool {

    /**
     * Returns an instance [Klass]
     *
     * @param type the qualified name of class
     */
    fun get(type: String): Klass

}
