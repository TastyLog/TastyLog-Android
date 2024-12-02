package repositoryimpl

import com.knu.datastore.preferences.SharedPreferencesManager
import com.knu.search.repository.RecentKeyWordRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecentKeyWordRepositoryImpl @Inject constructor(
    private val preferencesManager: SharedPreferencesManager,
) : RecentKeyWordRepository {

    override fun putStringList(key: String, value: List<String>) {
        preferencesManager.putStringList(key, value)
    }

    override fun getStringList(key: String): List<String> {
        return preferencesManager.getStringList(key)
    }
}
