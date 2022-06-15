package com.dev.countrypicker.bottomsheet.network

object NetworkURL {

    //headers
    const val BEARER = "Bearer"

    // response code
    const val RESPONSE_OK = 200
    const val RESPONSE_CREATED = 201
    const val RES_NOT_FOUND = 404
    const val RES_UNAUTHORISED = 401
    const val RES_BLOCKED = 402
    const val RES_FORBIDDEN = 403
    const val RES_UNPROCESSABLE_ENTITY = 422
    const val RES_SERVER_ERROR = 500

    const val GET_COUNTRY_LIST = "v3.1/all"
}