package uz.muhammadziyo.homework33

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.muhammadziyo.homework33.databinding.FragmentFragment3Binding
import uz.muhammadziyo.homework33.models.MyData


class fragment3 : Fragment() {

    private lateinit var binding: FragmentFragment3Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFragment3Binding.inflate(layoutInflater)
        binding.phoneNumber.text = MyData.phoneNumber

        binding

        return binding.root
    }


}