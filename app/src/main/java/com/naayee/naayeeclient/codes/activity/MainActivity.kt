package com.naayee.naayeeclient.codes.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.RecyclerView
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.adapters.RecommendListAdapter
import com.naayee.naayeeclient.codes.model.EntityModel
import com.naayee.naayeeclient.codes.model.RecommendationsModel
import com.naayee.naayeeclient.codes.network_res.GlobalNetResponse
import com.naayee.naayeeclient.codes.viewmodel.RecomendListViewModel
import com.naayee.naayeeclient.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val recomendListViewModel: RecomendListViewModel? by viewModels()
    private var mainActivityBinding: ActivityMainBinding? = null
    private var shopListRecyclerView: RecyclerView? = null
    private var rlistadapter: RecommendListAdapter? = null
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "NaaYee"

        setUpViews()
        setUpObservers()
    }

    private fun setUpViews() {
        binding.apply {

            toggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout,
                R.string.open,
                R.string.close
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.firstItem -> {
                        Toast.makeText(this@MainActivity, "First Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.secondtItem -> {
                        Toast.makeText(this@MainActivity, "Second Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.thirdItem -> {
                        Toast.makeText(this@MainActivity, "third Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
        }

        binding.also {
            shopListRecyclerView = it.shopListRecyclerView
        }
        rlistadapter = RecommendListAdapter { item ->
            if (item?.Id != null) {
                val intent = Intent(this, ShopProfileActivity::class.java)
                intent.putExtra(INTENT_KEY_ENTITY, item)
                startActivity(intent)
            }
        }


        shopListRecyclerView?.adapter = rlistadapter
    }

    private fun setUpObservers() {
        recomendListViewModel?.apply {
            recomendListLiveData.observe(this@MainActivity) { response ->
                if (response != null) {
                    parseNetworkResponseForRecomendList(response)
                }
            }
            getContactsListForMessage("25.3", "56.2")
        }
    }

    private fun parseNetworkResponseForRecomendList(response: GlobalNetResponse<RecommendationsModel>) {
        when (response) {
            is GlobalNetResponse.Success -> {
                val successResponse: RecommendationsModel? = response.value
                if (successResponse?.entityList != null) {
                    rlistadapter?.submitData(successResponse.entityList!!)
                }
            }
            is GlobalNetResponse.NetworkFailure -> {

            }
            else -> {
                //nothing to do just show a toast message
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val INTENT_KEY_ENTITY = "intent_key_entity"
    }


}