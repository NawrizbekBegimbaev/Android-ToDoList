package packagename.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.core.view.marginLeft
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import packagename.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = PurposeAdapter()
    private val dids = mutableListOf<Purposes>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = PurposeDatabase.getInstance(this)
        val purposeDao = db.getPurposeDao()

        lifecycleScope.launchWhenResumed {
            adapter.models = purposeDao.getAllPurposes().toMutableList()
        }

        binding.apply {
            rc.adapter = adapter
            rc.addItemDecoration(
                DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            )
            plus.setOnClickListener {
                menu.getLayoutParams().height = WRAP_CONTENT
                val param = (task.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    setMargins(210, 0, 0, 0)
                }
                task.layoutParams = param
                today.isVisible = false
                val dialog = AddPurposeDialog()
                dialog.show(this@MainActivity.supportFragmentManager, dialog.tag)
                dialog.setOnSuccessListener {
                    lifecycleScope.launchWhenResumed {
                        purposeDao.addPurpose(it)
                    }
                    lifecycleScope.launchWhenResumed {
                        adapter.models = purposeDao.getAllPurposes().toMutableList()
                    }

                    Snackbar.make(plus, "Добавлено", Snackbar.LENGTH_SHORT).show()
                    menu.getLayoutParams().height = 440;
                    val param = (task.layoutParams as ViewGroup.MarginLayoutParams).apply {
                        setMargins(20, 0, 20, 5)
                    }
                    task.layoutParams = param
                    today.isVisible = true
                }
                lifecycleScope.launchWhenResumed { adapter.models = purposeDao.getAllPurposes().toMutableList() }

            }
            adapter.setOnDidClickListener { purpose, position ->
                if (purpose.did == 1) {dids.add(purpose);delete.isVisible = true; delete.isClickable = true}
                else adapter.models.forEach { if (it.did == 0) dids.remove(purpose);}
                if(dids.isNotEmpty()){
                    delete.isVisible = true; delete.isClickable = true
                }else{
                    delete.isVisible = false; delete.isClickable = false
                }
            }
            delete.setOnClickListener {
                dids.forEach {
                    adapter.models.remove(it)
                    lifecycleScope.launchWhenResumed {
                        purposeDao.deletePurpose(it)
                    }

                }
                adapter.notifyDataSetChanged()
                dids.clear()
                delete.isClickable = false
                delete.isVisible = false
            }
        }
    }
}