package com.knu.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
  @SerialName("code")
  val code: Int,
  @SerialName("message")
  val message: String,
  @SerialName("data")
  val data: T? = null,
)
