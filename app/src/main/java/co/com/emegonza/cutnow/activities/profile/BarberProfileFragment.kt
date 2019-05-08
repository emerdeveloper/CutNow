package co.com.emegonza.cutnow.activities.profile

import android.content.Context
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.Toolbar;
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import co.com.emegonza.cutnow.R
import co.com.emegonza.cutnow.activities.profile.fragments.DescriptionFragment
import co.com.emegonza.cutnow.activities.profile.fragments.PriceFragment
import co.com.emegonza.cutnow.activities.profile.fragments.ReviewFragment
import com.squareup.picasso.Picasso

class BarberProfileFragment : Fragment() {

    lateinit var name: String
    lateinit var imageProfile: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle: Bundle? = arguments
        if (bundle != null)
            name = bundle.getString("name")
            imageProfile = bundle!!.getString("imageProfile")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.barber_profile, container, false)
        val toolbarLayout : CollapsingToolbarLayout = view.findViewById(R.id.toolbar_layout)
        val appBarLayout : AppBarLayout = view.findViewById(R.id.app_bar)
        val viewPager : ViewPager = view.findViewById(R.id.viewPagerContainer)
        val tabLayout : TabLayout = view.findViewById(R.id.tabs)
        val profileImage : ImageView = view.findViewById(R.id.profileImage)
        val nameBarber : TextView = view.findViewById(R.id.nameBarber)

        /*if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(toolbarLayout)
            if ((activity as AppCompatActivity).supportActionBar != null)
                (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }*/

        var containerButtons = activity!!.findViewById<LinearLayout>(R.id.container_visualization)
        containerButtons.visibility = View.GONE;

        //toolbarLayout.title = name
        Picasso.get().load(imageProfile).into(profileImage)
        nameBarber.text = name

        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = true
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbarLayout.title = name
                    isShow = true
                } else if (isShow) {
                    toolbarLayout.title = " "
                    isShow = false
                }
            }
        })

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

        adapter.addFragment(DescriptionFragment(), "Descripción")
        adapter.addFragment(PriceFragment(), "Precios")
        adapter.addFragment(ReviewFragment(), "Reseñas")

        viewPager.adapter = adapter
    }
}
