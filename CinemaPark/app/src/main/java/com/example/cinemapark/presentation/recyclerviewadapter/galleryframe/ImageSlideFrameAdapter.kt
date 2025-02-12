package com.example.cinemapark.presentation.recyclerviewadapter.galleryframe

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.cinemapark.R
import com.example.cinemapark.dataclass.galleryframe.ItemFrame

class ImageSlideFrameAdapter(private val context: Context, private var imageListFrame: List<ItemFrame>?) : PagerAdapter() {
    override fun getCount(): Int {
        return imageListFrame!!.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View =  (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
            R.layout.image_slider_item, null)
        val ivImages = view.findViewById<ImageView>(R.id.iv_images)

        imageListFrame!![position].let {
            Glide.with(context)
                .load(it.imageUrl)
                .into(ivImages)

            Log.d("slideGlide", "${it.imageUrl}")
        }




        val vp = container as ViewPager
        vp.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}