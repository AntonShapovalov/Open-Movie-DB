package concept.omdb.ui.list

import android.os.Parcel
import android.os.Parcelable
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion

data class MovieSuggestion(val query: String) : SearchSuggestion {

    constructor(parcel: Parcel) : this(parcel.readString() ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(query)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun getBody(): String {
        return query
    }

    companion object CREATOR : Parcelable.Creator<MovieSuggestion> {
        override fun createFromParcel(parcel: Parcel): MovieSuggestion {
            return MovieSuggestion(parcel)
        }

        override fun newArray(size: Int): Array<MovieSuggestion?> {
            return arrayOfNulls(size)
        }
    }

}