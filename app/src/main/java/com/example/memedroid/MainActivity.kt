package com.example.memedroid

import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmemeapi()
    }
    fun loadmemeapi(){
        var pb=findViewById<ProgressBar>(R.id.pb)
        pb.visibility=View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        var url ="https://meme-api.herokuapp.com/gimme"
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{ response ->
                url=response.getString("url")
                var iv1=findViewById<ImageView>(R.id.iv1)
                Glide.with(this).load(url).listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pb.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pb.visibility=View.GONE
                        return false
                    }
                }).into(iv1)
            },
            Response.ErrorListener {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
            })
        queue.add(JsonObjectRequest)
    }

    fun sharememe(view: View) {
        var intent=Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, Checkout this cool meme")
        intent.type="text/plain"
        val chooser=Intent.createChooser(intent,"Share this meme via...")
        startActivity(chooser)
    }
    fun nextmeme(view: View) {
        loadmemeapi()
    }
}