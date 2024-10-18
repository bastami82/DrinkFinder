import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.cstr
import platform.Foundation.NSLog

@OptIn(ExperimentalForeignApi::class)
actual fun logD(message: String) {
    NSLog("%s", message.cstr)
}