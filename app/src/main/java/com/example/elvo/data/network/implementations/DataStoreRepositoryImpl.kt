package com.example.elvo.data.network.implementations

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.elvo.domain.model.user.ProfileInfo
import com.example.elvo.domain.model.user.UserInfo
import com.example.elvo.domain.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): DataStoreRepository {
    companion object{
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        val USER_ID = intPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("username")
        val USER_AVATAR = stringPreferencesKey("user_avatar")
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    override fun getAccessToken(): Flow<String?> {
        return dataStore.data.map{ it[ACCESS_TOKEN_KEY] }
    }

    override fun getRefreshToken(): Flow<String?> {
        return dataStore.data.map{ it[REFRESH_TOKEN_KEY] }
    }

    override suspend fun clearTokens() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
            preferences.remove(REFRESH_TOKEN_KEY)
        }
    }

    override suspend fun saveUserInfo(userInfo: UserInfo) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = userInfo.userId
            preferences[USER_NAME] = userInfo.username
            preferences[USER_AVATAR] = userInfo.avatarUrl
        }
    }

    override fun getUserInfo(): Flow<ProfileInfo?> {
        return dataStore.data.map {
            ProfileInfo(
                it[USER_NAME],
                it[USER_AVATAR]
            )
        }
    }

    override suspend fun clearUserInfo() {
        dataStore.edit { preferences ->
            preferences.remove(USER_ID)
            preferences.remove(USER_NAME)
            preferences.remove(USER_AVATAR)
        }
    }

}