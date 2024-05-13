package com.example.imagesearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearch.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val imageList = mutableListOf<ImageInfo>()
    private val adapter = SearchAdapter(imageList)

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.rvSearch.adapter = adapter
        binding.rvSearch.layoutManager = GridLayoutManager(context, 2)

        binding.btnSearch.setOnClickListener {
            imageRequest()
            Toast.makeText(context, "click", Toast.LENGTH_SHORT).show()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun imageRequest() {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://dapi.kakao.com/").addConverterFactory(GsonConverterFactory.create()).build()
        val apiService: ImageService = retrofit.create(ImageService::class.java)
        val inputData = binding.etInput.text.toString()
        val movieCall = apiService.searchImage(
            query = inputData
        )

        if (!imageList.isEmpty()) {
            imageList.clear()
        }
        movieCall.enqueue(object : Callback<Image> {
            override fun onResponse(call: Call<Image>, response: Response<Image>) {
                val data = response.body()

                val imageinfo = data?.imageList

                if (!imageinfo.isNullOrEmpty()) {
                    imageinfo?.let { info ->
                        info.forEach {
                            imageList.add(it)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<Image>, t: Throwable) {
                call.cancel()
            }
        })

    }
}