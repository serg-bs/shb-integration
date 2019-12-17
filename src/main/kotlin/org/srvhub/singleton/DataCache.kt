package org.srvhub.singleton

object DataCache {
    fun add(key: String, data: Any) {
        hashMap.put(key, data)
    }

    val hashMap = HashMap<String, Any>()

    fun getData(key: String): Any? {
        val value = hashMap[key]
        return value
    }
}
