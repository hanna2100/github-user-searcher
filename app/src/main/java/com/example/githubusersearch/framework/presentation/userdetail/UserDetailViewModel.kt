package com.example.githubusersearch.framework.presentation.userdetail

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.githubusersearch.business.interactors.searchuser.SearchUserInteractors
import com.example.githubusersearch.framework.presentation.search.SearchUserFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel
@Inject
constructor(
    private val searchUserInteractors: SearchUserInteractors
) : ViewModel() {

    fun moveToSearchUserFragment(view: View?) {
        val action = UserDetailFragmentDirections
            .actionUserDetailFragmentToSearchUserFragment()
        view?.findNavController()?.navigate(action)
    }

}