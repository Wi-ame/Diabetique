package doct_fragment

import android.hardware.Camera
import androidx.lifecycle.LifecycleOwner
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.Query

class FirebaseRecyclerOptions<T> private constructor(
    val modelClass: Class<T>,
    val query: Query?,
    val snapshots: SnapshotParser<T>,
    val parser: SnapshotParser<T>? = null,
    val owner: LifecycleOwner? = null,
    val errorHandler: Camera.ErrorCallback? = null
) {
    // Méthodes et propriétés publiques
}