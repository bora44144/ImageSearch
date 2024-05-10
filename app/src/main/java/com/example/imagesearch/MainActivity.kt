package com.example.imagesearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearch.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { ImageAdapter(imageList) }
    private val imageList = mutableListOf<ImageInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = GridLayoutManager(this, 2)

        binding.btnSearch.setOnClickListener {
            imageRequest()
        }
    }

    fun imageRequest() {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://dapi.kakao.com/").addConverterFactory(GsonConverterFactory.create()).build()
        val apiService: ImageService = retrofit.create(ImageService::class.java)
        val inputData = binding.etInput.text.trim().replace(Regex(" "), "")
        val movieCall = apiService.searchImage(
            query = inputData
        )

        if (!imageList.isEmpty()) {
            imageList.clear()
        }
        movieCall.enqueue(object : Callback<Image> {
            override fun onResponse(call: Call<Image>, response: Response<Image>) {
                val data = response.body()

                val movieinfo = data?.imageList

                if (!movieinfo.isNullOrEmpty()) {
                    movieinfo?.let { info ->
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