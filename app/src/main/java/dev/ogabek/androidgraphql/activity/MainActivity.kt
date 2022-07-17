package dev.ogabek.androidgraphql.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.exception.ApolloException
import dev.ogabek.androidgraphql.R
import dev.ogabek.androidgraphql.UsersLIstQuery
import dev.ogabek.androidgraphql.adapter.UsersAdapter
import dev.ogabek.androidgraphql.databinding.ActivityMainBinding
import dev.ogabek.androidgraphql.network.GraphQL
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter by lazy { UsersAdapter(arrayListOf()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        getUserList()

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter

        binding.btnCreate.setOnClickListener {
            val intent = Intent(this, CRUDActivity::class.java)
            intent.putExtra("isNew", true)
            startActivity(intent)
        }

    }

    private fun getUserList() {
        lifecycleScope.launch launchWhenResumed@{
            val response = try {
                GraphQL.get().query(UsersLIstQuery(10)).execute()
            } catch (e: ApolloException) {
                Log.d("MainActivity", e.toString())
                return@launchWhenResumed
            }

            val users = response.data?.users
            binding.rvUsers.adapter = UsersAdapter(users as ArrayList)
            (binding.rvUsers.adapter as UsersAdapter).onClick = {
                open(it)
            }

        }
    }

    fun open(id: String) {
        val intent = Intent(this, CRUDActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

}