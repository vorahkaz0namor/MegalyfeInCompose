package com.ccink.model

interface Constants {
    companion object {

        const val SERVER_API_URL = "https://service-authorization.onrender.com"
        const val BACKEND_API_URL = "https://megalife-backend-i4d5.onrender.com"

        const val REQUIRED_PASSWORD_LENGTH = 6
        const val PROGRESS_MAX_VALUE = 1.0f

        const val RUSSIAN = "ru"

        const val MAX_CHAR = 500

        fun createDressedPetImageRequest(
            petId: Int,
            clothesId: Int?
        ): String {
            val inlineClothesId = clothesId?.let { "&full_id=$it" } ?: ""
            return BACKEND_API_URL +
                    "/files/images/pet/dressed/download?" +
                    "pet_id=$petId" + inlineClothesId
        }
    }
}