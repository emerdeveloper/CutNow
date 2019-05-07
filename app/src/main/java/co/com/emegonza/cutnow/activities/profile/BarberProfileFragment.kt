package co.com.emegonza.cutnow.activities.profile

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout
import co.com.emegonza.cutnow.R
import co.com.emegonza.cutnow.activities.profile.fragments.DescriptionFragment
import co.com.emegonza.cutnow.activities.profile.fragments.PriceFragment
import co.com.emegonza.cutnow.activities.profile.fragments.ReviewFragment

class BarberProfileFragment : Fragment() {

    lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle: Bundle? = arguments
        if (bundle != null)
            name = bundle.getString("name")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.barber_profile, container, false)
        val toolbarLayout : Toolbar = view.findViewById(R.id.toolbar)
        val viewPager : ViewPager = view.findViewById(R.id.viewPagerContainer)
        val tabLayout : TabLayout = view.findViewById(R.id.tabs)


        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(toolbarLayout)
            if ((activity as AppCompatActivity).supportActionBar != null)
                (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        var containerButtons = activity!!.findViewById<LinearLayout>(R.id.container_visualization)
        containerButtons.visibility = View.GONE;

        toolbarLayout.title = name

        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        //linearLayoutManager = LinearLayoutManager(activity)
        /*recyclerView.layoutManager =  LinearLayoutManager(activity)
        recyclerView.hasFixedSize()
        recyclerView.adapter = UserRecyclerViewAdapter(users, { userItem : User -> barberItemClicked(userItem) })*/
        return view
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        //listener = null
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)

        adapter.addFragment(DescriptionFragment(), "DESCRIPTION")
        adapter.addFragment(PriceFragment(), "PRICES")
        adapter.addFragment(ReviewFragment(), "REVIEWS")

        viewPager.adapter = adapter
    }
}
