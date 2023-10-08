package com.test.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "movies", indices = [androidx.room.Index(value = ["id"], unique = true)])
data class MovieEntity(
    @PrimaryKey
    @Nonnull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "adult")
    var adult: Boolean? = false,

    @ColumnInfo(name = "backdrop_path")
    var backdrop_path: String?,

    @ColumnInfo(name = "original_language")
    var original_language: String?,

    @ColumnInfo(name = "original_title")
    var original_title: String?,

    @ColumnInfo(name = "overview")
    var overview: String?,

    @ColumnInfo(name = "popularity")
    var popularity: Double?,

    @ColumnInfo(name = "poster_path")
    var poster_path: String?,

    @ColumnInfo(name = "release_date")
    var release_date: String?,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "video")
    var video: Boolean? = false,

    @ColumnInfo(name = "vote_average")
    var vote_average: Double?,

    @ColumnInfo(name = "vote_count")
    var vote_count: Int?,
)
