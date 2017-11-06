package util

import com.google.gson.Gson

class Resource {

    companion object {
        fun read(file: String): String {
             return Resource::class.java.getResource(file).readText()
        }

        inline fun <reified T> readObject(file: String): T = Gson().fromJson(Resource.read(file), T::class.java)

    }

}