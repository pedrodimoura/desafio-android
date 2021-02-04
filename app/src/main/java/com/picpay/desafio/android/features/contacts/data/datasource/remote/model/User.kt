package com.picpay.desafio.android.features.contacts.data.datasource.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName(SERIALIZED_NAME_IMG) val img: String,
    @SerializedName(SERIALIZED_NAME_NAME) val name: String,
    @SerializedName(SERIALIZED_NAME_ID) val id: Int,
    @SerializedName(SERIALIZED_NAME_USERNAME) val username: String
) : Parcelable {
    companion object {
        private const val SERIALIZED_NAME_IMG = "img"
        private const val SERIALIZED_NAME_NAME = "name"
        private const val SERIALIZED_NAME_ID = "id"
        private const val SERIALIZED_NAME_USERNAME = "username"
    }
}