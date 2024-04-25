package mapkit.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class LatLng(
    @SerialName("lat") val latitude: Double,
    @SerialName("lng") val longitude: Double,
)
{
    override fun toString(): String {
        return "[$longitude, $latitude]"
    }

    fun toJson(): String {
         return Json.encodeToString(this)
    }
}