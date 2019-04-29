package com.epam.valkaryne.weatherforecasting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    var fragment: ForecastFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragment = ForecastFragment()
        fragment?.let { val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, it)
            transaction.commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sort, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_alpha_asc -> {
                fragment?.sortForecastData(ForecastFragment.SORT_ALPHABETIC_ASCEND)
                return true
            }
            R.id.action_alpha_desc -> {
                fragment?.sortForecastData(ForecastFragment.SORT_ALPHABETIC_DESCNED)
                return true
            }
            R.id.action_num_asc -> {
                fragment?.sortForecastData(ForecastFragment.SORT_NUMERIC_ASCEND)
                return true
            }
            R.id.action_num_desc -> {
                fragment?.sortForecastData(ForecastFragment.SORT_NUMERIC_DESCEND)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
