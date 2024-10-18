import platform.Foundation.NSProcessInfo

actual fun isDebugBuild(): Boolean {
    val isDebug = NSProcessInfo.processInfo().environment()["DEBUG_BUILD"] as String?
    return isDebug == "1" || isDebug == "true"
}