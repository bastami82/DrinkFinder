import uk.appyapp.drinkfinder.BuildConfig.BUILD_TYPE

actual fun isDebugBuild(): Boolean = BUILD_TYPE == "debug"