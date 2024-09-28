package com.knu.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.knu.onboarding.IntroFragment
import com.knu.retastylog.onboarding.databinding.ItemIntroBinding

class IntroAdapter : RecyclerView.Adapter<IntroViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): IntroViewHolder {
        val binding = ItemIntroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IntroViewHolder(binding)
    }

    override fun getItemCount(): Int = IntroFragment.MAX_STEP

    override fun onBindViewHolder(
        holder: IntroViewHolder,
        position: Int,
    ) {
        holder.bind(position)
    }
}
