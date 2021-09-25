package com.mind.data.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by rui
 *  on 2021/8/16
 */
@Entity(tableName = "tab_person")
data class PersonEntityModel(
    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "person_id")
    val personId: String,

    /**
     * 餐饮单位id
     */
    @ColumnInfo(name = "catering_id")
    val cateringId: String,

    /**
     *姓名
     */
    @ColumnInfo(name = "name")
    val name: String,

    /**
     *电话
     */
    @ColumnInfo(name = "mobile")
    val mobile: String,

    /**
     *身份
     */
    @ColumnInfo(name = "identity")
    val identity: String,

    /**
     * 人脸信息
     */
    @ColumnInfo(name = "face")
    val face: String,

    /**
     *验收角色(B:买方;E:其他;)
     */
    @ColumnInfo(name = "validRole")
    val validRole: String,

    /**
     * 签到时间
     */
    @ColumnInfo(name = "signin_time")
    val signinTime: String,

    @ColumnInfo(name = "error_face")
    var errorFace: Int = 0,

    @ColumnInfo(name = "face_token")
    var faceToken: String,

    @ColumnInfo(name = "face_index")
    var faceIndex: Int = -1,


    )