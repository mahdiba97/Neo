package com.mahdiba97.notes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mahdiba97.notes.databinding.FragmentAboutBinding
import java.text.DateFormat
import java.util.*


class AboutFragment : Fragment() {
  private lateinit var binding: FragmentAboutBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    actionbar(requireActivity())?.let {
      it.title = getString(R.string.about)
      it.setDisplayHomeAsUpEnabled(true)
    }
    binding = FragmentAboutBinding.inflate(layoutInflater)
    val stringBuilder = StringBuilder()
    val info = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
    stringBuilder.append("${getString(R.string.version)} ${info.versionName}\n")
    val installDate =
      DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(info.lastUpdateTime)
    stringBuilder.append("${getString(R.string.install_date)} $installDate")

    binding.tvAbout.text = stringBuilder.toString()
    binding.tvTwitterAccount.setOnClickListener {
      startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/mahdiba97")))
    }
    setHasOptionsMenu(true)
    return binding.root
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return if (item.itemId == android.R.id.home) {
      findNavController().navigateUp()
      Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
      true
    } else super.onOptionsItemSelected(item)
  }
}