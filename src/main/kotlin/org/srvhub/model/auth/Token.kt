package org.srvhub.model.auth

import java.math.BigDecimal

class Token {
    var access_token: String? = null
    var scope: String? =null
    var token_type: String? =null
    var expires_in: BigDecimal? =null
    var userId: String? =null
    var jti: String? =null
}