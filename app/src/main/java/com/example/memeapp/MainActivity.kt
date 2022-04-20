package com.example.memeapp
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private var currentImageUrl:String?=null
    lateinit var progressBar:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView=findViewById<ImageView>(R.id.imageView)
        progressBar=findViewById(R.id.progressBar)
        loadMeme()
    }

    private fun loadMeme() {
        progressBar.visibility=View.VISIBLE

        val url = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,null,
            { response ->
                currentImageUrl =response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }
                }).into(imageView)
            },
            {

            })
       Singleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun shareMeme(view: View) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "hey checkout this meme $currentImageUrl")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "share")
        startActivity(shareIntent)

    }
    fun nextButton(view: View) {
        loadMeme()

    }
}
