package com.chootdev.railwaydic.database

import java.sql.SQLException

/**
 * Created by Choota on 11/10/17.
 */
interface Crud {
    fun create(item: Any): Int
    fun update(item: Any): Int
    fun delete(item: Any): Int
    fun findById(id: Int): Any
    fun deleteAll(): Boolean

    @Throws(SQLException::class)
    fun findAll(): List<*>
}