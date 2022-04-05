package dev.sukhrob.familybudget.view.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.familybudget.BuildConfig
import dev.sukhrob.familybudget.R
import dev.sukhrob.familybudget.databinding.FragmentAboutBinding
import dev.sukhrob.familybudget.view.base.BaseFragment

@AndroidEntryPoint
class AboutFragment : BaseFragment<FragmentAboutBinding, AboutViewModel>() {
    override val viewModel: AboutViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentAboutBinding.inflate(inflater, container, false)
}
