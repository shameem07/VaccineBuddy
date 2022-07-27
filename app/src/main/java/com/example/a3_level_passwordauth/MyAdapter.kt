package com.example.a3_level_passwordauth


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView


class MyAdapter(val vaccineList: List<CenterX>):
        RecyclerView.Adapter<MyAdapter.ViewHolder>()

{

    var context: Context? = null
            class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
                var centern: TextView

                var addre: TextView
                var blockname:TextView



                init {
                    centern=itemView.findViewById(R.id.centerid)
                    addre=itemView.findViewById(R.id.centername)
                    blockname=itemView.findViewById(R.id.blockname)


                }

             }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.rowview,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        ("Center ID: "+vaccineList[position].center_id.toString()).also { holder.centern.text = it }
        holder.addre.text = vaccineList[position].name.toString()
        holder.blockname.text = vaccineList[position].block_name.toString()




        holder.itemView.setOnClickListener {
            Log.i("ADAPTER","IT WORKS")
            val fragmentManager: FragmentManager =
                (context as Home).supportFragmentManager

            val sharedPreferences = context?.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
            val myEdit = sharedPreferences?.edit()


            val centername = vaccineList[position].name.toString()
            val address = vaccineList[position].location.toString()
            val centerid = vaccineList[position].center_id.toString()
            val lat = vaccineList[position].lat.toDouble()
            val long = vaccineList[position].long.toDouble()
            myEdit?.putString("centername", centername)
            myEdit?.putString("address", address)
            myEdit?.putString("centerid", centerid)
            myEdit?.putLong("lat",lat!!.toBits())
            myEdit?.putLong("long",long!!.toBits())
            myEdit?.commit()






            (context as Home).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_content,CenterDetails(),"CenterDetails")
                .addToBackStack("CenterDetails")
                .commit()


        }

    }

    override fun getItemCount(): Int {
        return vaccineList.size
            }


}