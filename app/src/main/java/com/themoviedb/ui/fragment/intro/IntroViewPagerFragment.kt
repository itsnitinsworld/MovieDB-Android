package com.themoviedb.ui.fragment.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.themoviedb.R
import com.themoviedb.databinding.IntroFragmentBinding
import com.themoviedb.ui.fragment.intro.model.IntroPagerModel
import com.themoviedb.utils.extensions.loadImage

class IntroViewPagerFragment : Fragment() {
    private lateinit var introPagerModel: IntroPagerModel
    private lateinit var binding: IntroFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!requireArguments().containsKey(MODEL)) throw RuntimeException("Fragment must contain a /$MODEL/ argument!")
        introPagerModel = requireArguments().getParcelable(MODEL)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout resource file
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.intro_fragment, container, false)
        binding.imageView.loadImage(introPagerModel.imageSource)
        binding.tvHeader.text = introPagerModel.titleText
        binding.tvDescription.text = introPagerModel.descText

        return binding.root
    }


    companion object {
        private const val MODEL = "model"
        fun newInstance(introPagerModel: IntroPagerModel?): IntroViewPagerFragment {
            val frag = IntroViewPagerFragment()
            val b = Bundle()
            b.putParcelable(MODEL, introPagerModel)
            frag.arguments = b
            return frag
        }
    }
}