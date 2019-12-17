package org.srvhub.singleton

object CredentialManager {
    fun registerCredential(credential: Credential) {
        allCredential.put(credential.serviceSystemName, credential)
    }

    val allCredential = HashMap<String, Credential>()

    fun getCredential(serviceSystemName: String): Credential? {
        val dataProvider = allCredential[serviceSystemName]
        return dataProvider
    }
}