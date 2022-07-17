package dev.ogabek.androidgraphql.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import dev.ogabek.androidgraphql.DeleteUserMutation
import dev.ogabek.androidgraphql.InsertUserMutation
import dev.ogabek.androidgraphql.UpdateUserMutation
import dev.ogabek.androidgraphql.UserByIdQuery
import dev.ogabek.androidgraphql.databinding.ActivityCrudactivityBinding
import dev.ogabek.androidgraphql.network.GraphQL
import kotlinx.coroutines.launch

class CRUDActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCrudactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {

        val isNew = intent.getBooleanExtra("isNew", false)

        if (isNew) {
            binding.btnDelete.visibility = View.GONE
            binding.etName.setText("")
            binding.etRocket.setText("")
            binding.etTwitter.setText("")

            binding.btnSave.setOnClickListener {
                if (
                    binding.etName.text.isNotEmpty() && binding.etRocket.text.isNotEmpty() && binding.etTwitter.text.isNotEmpty()
                ) createUser(
                    binding.etName.text.toString(),
                    binding.etRocket.text.toString(),
                    binding.etTwitter.text.toString()
                ) else Toast.makeText(this, "Fill All fields", Toast.LENGTH_SHORT)
                    .show()
            }

        } else {
            binding.btnDelete.visibility = View.VISIBLE
            getUserById(intent.getStringExtra("id")!!)

            binding.btnSave.setOnClickListener {
                updateUser(intent.getStringExtra("id")!!,
                    binding.etName.text.toString(),
                    binding.etRocket.text.toString(),
                    binding.etTwitter.text.toString())
            }

            binding.btnDelete.setOnClickListener {
                deleteUser(intent.getStringExtra("id")!!)
            }

        }

    }

    private fun createUser(name: String, rocket: String, twitter: String) {
        lifecycleScope.launch launchWhenResumed@{
            val response = try {
                GraphQL.get().mutation(InsertUserMutation(name, rocket, twitter)).execute()
                Toast.makeText(applicationContext, "Created", Toast.LENGTH_SHORT).show()
            } catch (e: ApolloException) {
                Log.d("Main Activity", e.toString())
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
            }

            finish()

        }
    }

    private fun updateUser(id: String, name: String, rocket: String, twitter: String) {
        lifecycleScope.launch launchWhenResumed@{
            val response = try {
                GraphQL.get().mutation(UpdateUserMutation(id, name, rocket, twitter)).execute()
                Toast.makeText(applicationContext, "Update", Toast.LENGTH_SHORT).show()
            } catch (e: ApolloException) {
                Log.d("Main Activity", e.toString())
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
            }

            finish()

        }
    }

    private fun deleteUser(id: String) {
        lifecycleScope.launch launchWhenResumed@{
            val response = try {
                GraphQL.get().mutation(DeleteUserMutation(id)).execute()
                Toast.makeText(applicationContext, "Created", Toast.LENGTH_SHORT).show()
            } catch (e: ApolloException) {
                Log.d("Main Activity", e.toString())
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
            }

            finish()

        }
    }

    private fun getUserById(id: String) {
        lifecycleScope.launch launchWhenResumed@{
            val response = try {
                GraphQL.get().query(UserByIdQuery(id)).execute()
            } catch (e: ApolloException) {
                Log.d("MainActivity", e.toString())
                return@launchWhenResumed
            }

            val user = response.data?.users_by_pk

            binding.etName.setText(user?.name)
            binding.etRocket.setText(user?.rocket)
            binding.etTwitter.setText(user?.twitter)


        }
    }


}