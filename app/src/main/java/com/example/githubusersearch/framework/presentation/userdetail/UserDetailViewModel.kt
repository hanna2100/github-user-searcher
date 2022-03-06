package com.example.githubusersearch.framework.presentation.userdetail

import androidx.lifecycle.ViewModel
import com.example.githubusersearch.business.interactors.searchuser.SearchUserInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel
@Inject
constructor(
    private val searchUserInteractors: SearchUserInteractors
) : ViewModel() {

}