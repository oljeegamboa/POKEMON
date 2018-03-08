package com.example.asus.pokemonapi

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.R.attr.data
import org.json.JSONObject
import android.R.attr.data
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.R.attr.data
import android.os.StrictMode
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import java.net.MalformedURLException


class MainActivity : AppCompatActivity() {
    var c = 0
    var test : TextView?=null

    var prog : ProgressBar?=null


    var adapter :Pokemons?=null
    var datapoke : ArrayList<Pokemon> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        test = findViewById(R.id.textView)
        var process = fetchData()

        prog = findViewById(R.id.progressBar)
        process.execute()


    }





    inner class fetchData : AsyncTask<Void,Void,Void> (){
        var data : String? = ""
        var SingleParse:String=""
        var DataParsed:String = ""
        var ImageUrl:String=""

        var counter =1


        override fun doInBackground(vararg p0: Void?): Void? {
            try {

                while(counter !=21) {


                    data = StringUrl(counter)

                    var JO = JSONObject(data).getJSONObject("sprites")
                    SingleParse = JO.getString("front_default")
                    ImageUrl = ImageUrl + SingleParse

                    JO = JSONObject(data)
                    SingleParse = JO.getString("name")
                    DataParsed = DataParsed + SingleParse
                    datapoke.add(Pokemon(DataParsed,ImageUrl))
                    adapter = Pokemons(datapoke,applicationContext)



                    publishProgress()



                    DataParsed = ""
                    ImageUrl = ""
                    c=counter
                    counter++



                }



            }
            catch(e: MalformedURLException){
                e.printStackTrace()
            }
            catch(e: JSONException){

                e.printStackTrace()
            }


            return null
        }


        override fun onProgressUpdate(vararg values: Void?) {


            test!!.text = "Pokemons : "+c.toString()
            var layout_manager = LinearLayoutManager(applicationContext)
            recyclerView.layoutManager = layout_manager
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter






            super.onProgressUpdate(*values)

        }


        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            prog!!.visibility = View.GONE

        }
    }





    fun StringUrl ( counter:Int): String {
        var url = URL("https://pokeapi.co/api/v2/pokemon/"+counter.toString()+"/")
        var httpURLConnection = url.openConnection()
        var inputStream = httpURLConnection.getInputStream()
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var line: String? = ""
        var data :String =""
        while (line != null) {
            line = bufferedReader.readLine()
            data += line
        }
        return  data
    }
}

