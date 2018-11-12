package app.sub2_footballmatchschedule.utils

import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

//menggunakan standard delegated lazy
open class ContextProvider {
    open val main: CoroutineContext by lazy { UI }
}

class TextContextProvider : ContextProvider(){
    override val main : CoroutineContext = Unconfined
}