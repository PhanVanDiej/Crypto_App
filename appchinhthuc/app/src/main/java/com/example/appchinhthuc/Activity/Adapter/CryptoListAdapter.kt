package com.example.appchinhthuc.Activity.Adapter

import android.content.Context
import android.content.Intent
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appchinhthuc.Activity.DetailCryptoActivity
import com.example.appchinhthuc.Model.CryptoModel
import com.example.appchinhthuc.R
import com.example.appchinhthuc.databinding.ViewholderWalletBinding

class CryptoListAdapter(private val item: MutableList<CryptoModel>):RecyclerView.Adapter<CryptoListAdapter.ViewHolder>() {
    class ViewHolder(val binding:ViewholderWalletBinding):RecyclerView.ViewHolder(binding.root)

    private lateinit var context: Context
    var formatter:DecimalFormat?=null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CryptoListAdapter.ViewHolder {
        context=parent.context
        formatter= DecimalFormat("###,###,###,###")
        val binding=ViewholderWalletBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoListAdapter.ViewHolder, position: Int) {
        val item=item[position]
        holder.binding.cryptoNameTxt.text=item.Symbol
        holder.binding.cryptoPriceTxt.text="$"+formatter?.format(item.Price)
        holder.binding.changePercentTxt.text=item.ChangePercent.toString()+"%"
        holder.binding.propertySizeTxt.text=item.AmountNumber.toString()+item.ShortSymbol.replace("/USDT","")
        holder.binding.propertyAmountTxt.text="$"+formatter?.format(item.AmountDollar)

        if(item.ChangePercent<0) holder.binding.changePercentTxt.setTextColor(context.resources.getColor(
            R.color.red))
        val drawableResourceId=holder.itemView.resources.getIdentifier(item.SymbolLogo,"drawable",holder.itemView.context.packageName)

        Glide.with(context).load(drawableResourceId).into(holder.binding.logolmg)

        holder.itemView.setOnClickListener{
            val intent=Intent(context,DetailCryptoActivity::class.java)
            intent.putExtra("object",item)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int =item.size

}