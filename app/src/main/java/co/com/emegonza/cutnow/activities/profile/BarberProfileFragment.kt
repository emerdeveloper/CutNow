package co.com.emegonza.cutnow.activities.profile

import android.content.Context
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import co.com.emegonza.cutnow.R
import co.com.emegonza.cutnow.activities.appointment.AppointmentFragment
import co.com.emegonza.cutnow.activities.profile.fragments.*
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
        val fab : FloatingActionButton = view.findViewById(R.id.fab)


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
        fab.setOnClickListener { view ->

            val bundle: Bundle = Bundle()
            val fragment: AppointmentFragment = AppointmentFragment()
            bundle.putString("name", name)
            bundle.putString("imageProfile", imageProfile)

            fragment.arguments = bundle
            activity?.supportFragmentManager!!
                .beginTransaction()
                //.addSharedElement(sharedElement, transitionName)
                .replace(R.id.container_frame, fragment)
                .addToBackStack("appointment")
                .commit()
        }
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
        adapter.addFragment(ServiceFragment(), "Servicios")
        adapter.addFragment(ReviewFragment(), "Reseñas")

        viewPager.adapter = adapter
    }
}
