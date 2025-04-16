package khw15.eventsdicoding.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventResponse(
	@SerializedName("listEvents")
	val listEvents: List<ListEventsItem>? = null,

	@SerializedName("error")
	val error: Boolean? = null,

	@SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class ListEventsItem(
	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("summary")
	val summary: String? = null,

	@SerializedName("description")
	val description: String? = null,

	@SerializedName("imageLogo")
	val imageLogo: String? = null,

	@SerializedName("mediaCover")
	val mediaCover: String? = null,

	@SerializedName("category")
	val category: String? = null,

	@SerializedName("ownerName")
	val ownerName: String? = null,

	@SerializedName("cityName")
	val cityName: String? = null,

	@SerializedName("quota")
	val quota: Int? = null,

	@SerializedName("registrants")
	val registrants: Int? = null,

	@SerializedName("beginTime")
	val beginTime: String? = null,

	@SerializedName("endTime")
	val endTime: String? = null,

	@SerializedName("link")
	val link: String? = null
) : Parcelable
