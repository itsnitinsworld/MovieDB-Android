package com.themoviedb.utils

/**
 * @author Nitin Khanna
 * @date 19-11-2020
 */

object AppConstants {

    object Preference {
        const val IS_FIRST_TIME_USER = "is_first_time_user"
    }

    object DummyData {
        const val FORWARD_MESSAGE =
            "#BigBreaking: Nazma begum(43) sitting in #ShaheenBaghProtest was not feeling well. Doctors found her #coronavirus positive. She denied the treatment and again went to #ShaheenBagh\n" +
                    " What kind of moron are these people?\n" +
                    "#Coronavirus Reaches Delhi"
        const val DEVICE_ID = "1"
    }

    object MovieDB{
        const val IMAGE_PATH = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/"
        const val API_KEY = "4c065e562519ef8755b6f816a9b81e67"
    }

    object API {
        const val BASE_URL = "https://api.themoviedb.org/3/movie/"
    }
}