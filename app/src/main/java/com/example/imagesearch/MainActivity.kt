package com.example.imagesearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.imagesearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSearchFrag.setOnClickListener {
            setFragment(SearchFragment())
        }
        binding.btnStorageFrag.setOnClickListener {
            setFragment(StorageFragment())
        }
        setFragment(SearchFragment())
    }

    private fun setFragment(frag: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.layout, frag)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }
}