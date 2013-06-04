package com.example.dota2;


import com.example.db.DBManager;
import com.example.db.GoodsColumn;
import com.example.model.ItemBean;
import com.geniuseoe2012.lazyloaderdemo.cache.ImageLoader;
import com.jwd.utils.ImageUtil;
import com.jwd.utils.StrUtil;
import com.jwd.views.TopBar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.CursorAdapter;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class GoodsDetailFrag extends Fragment {
	View view;
	FragmentActivity context;
	ItemBean goods;
	ImageLoader mLoader;
		@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			view=inflater.inflate(R.layout.goods_detial,null);
			context=this.getActivity();
			mLoader=new ImageLoader(context);
			goods=(ItemBean) getArguments().getSerializable("itemBean");
			initTopBar(goods.getGoods_chineseName());
			initView();
		return view;
	}
		private void initTopBar(String titleName)
		{
			TopBar topBar=(TopBar) view.findViewById(R.id.topbar);
			topBar.setTitle(titleName);
			topBar.showButton1(true);
			topBar.getButton1().setText("返回");
			topBar.getButton1().setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					getFragmentManager().popBackStack();
				}
			});
		}
		
		private void initView()
		{
			ImageView iv=(ImageView) view.findViewById(R.id.herodetial_image);
			TextView tv_ca= (TextView) view.findViewById(R.id.goods_name);
			TextView tv_price= (TextView) view.findViewById(R.id.goods_price);
			TextView tv_addSkill= (TextView) view.findViewById(R.id.goods_attrubite);
			TextView tv_desciption= (TextView) view.findViewById(R.id.goods_descript);
//			mLoader.DisplayImage(goods.getGoods_image(), iv, false,goods.getGoods_name());
			Drawable bg=ImageUtil.getDrawableByPicName(goods.getGoods_name(), context, "com.example.dota2");
			iv.setBackgroundDrawable(bg);
			tv_price.setText("价格："+goods.getGoods_price());
			tv_ca.setText("名称："+goods.getGoods_chineseName().replace(","," ").replace("\n", ""));
			tv_desciption.setText(goods.getGoods_add_skill().replace(" ", "\n").trim());
			tv_addSkill.setText(goods.getGoods_disdription().replace(" +","+").replace(" ","\n").trim());
			Log.i("zc","goods.getGoods_disdription():"+goods.getGoods_disdription().trim()+"111");
			initGridView1();
			initGridView2();
		}
		
		
		
		
		
		/**
		 * 物品合成所需
		 */
		private void initGridView1() {
			GridView gv = (GridView) view.findViewById(R.id.gridView1);
			DBManager manager = new DBManager(context, "test");
			SQLiteDatabase db = manager.getWritableDatabase();
			
			String[] fromGoods=goods.getGoods_compose_from().split(",");
			

			
			
			String sql = "select * from Goods where "+GoodsColumn.goods_name+"=";
			for(int i=0;i<fromGoods.length;i++)
			{
				if(i!=fromGoods.length-1)
				{
				sql+="'"+fromGoods[i].toLowerCase()+"'"+" or  "+GoodsColumn.goods_name+"=";
				}
				else{
					sql+="'"+fromGoods[i].toLowerCase()+"'";
				}
			}
			final Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount()<=0)
			{
				TextView tv=(TextView) view.findViewById(R.id.textView4);
				View layout=view.findViewById(R.id.layout2);
				tv.setVisibility(View.GONE);
				layout.setVisibility(View.GONE);
				return;
			}
			MyAdapter adapter = new MyAdapter(context, cursor, true);
			gv.setAdapter(adapter);
			gv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					cursor.moveToPosition(position);
					ItemBean goods = getItemBeanByCursor(cursor);
					GoodsDetailFrag goodsDetailFrag = new GoodsDetailFrag();
					Bundle data = new Bundle();
					data.putSerializable("itemBean", goods);
					goodsDetailFrag.setArguments(data);
					switchFrag(R.id.frag, goodsDetailFrag,false);
				}
			});
		}
		
		/**
		 * 物品可合成
		 */
		private void initGridView2() {
			GridView gv = (GridView) view.findViewById(R.id.gridView2);
			DBManager manager = new DBManager(context, "test");
			SQLiteDatabase db = manager.getWritableDatabase();
			String[] toGoods=goods.getGoods_compose_to().split(",");
			

			String sql = "select * from Goods where "+GoodsColumn.goods_name+"=";
			for(int i=0;i<toGoods.length;i++)
			{
				if(i!=toGoods.length-1)
				{
				sql+="'"+toGoods[i].toLowerCase()+"'"+" or  "+GoodsColumn.goods_name+"=";
				}
				else{
					sql+="'"+toGoods[i].toLowerCase()+"'";
				}
			}
			final Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount()<=0)
			{
				TextView tv=(TextView) view.findViewById(R.id.textView5);
				tv.setVisibility(View.GONE);
				View layout=view.findViewById(R.id.layout3);
				layout.setVisibility(View.GONE);
				return;
			}
			MyAdapter adapter = new MyAdapter(context, cursor, true);
			gv.setAdapter(adapter);
			gv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					cursor.moveToPosition(position);
					ItemBean goods = getItemBeanByCursor(cursor);
					GoodsDetailFrag goodsDetailFrag = new GoodsDetailFrag();
					Bundle data = new Bundle();
					data.putSerializable("itemBean", goods);
					goodsDetailFrag.setArguments(data);
					switchFrag(R.id.frag, goodsDetailFrag,true);
				}
			});
		}
		
		
		
		public void switchFrag(int Rid, Fragment frag, boolean addToBackStack) {
			try {
				FragmentTransaction ft = context.getSupportFragmentManager()
						.beginTransaction();
				ft.replace(Rid, frag);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				if (addToBackStack) {
					ft.addToBackStack(null);
				}
				ft.commit();// 提交
			} catch (Exception e) {
			}
		}
		

		class MyAdapter extends CursorAdapter {
			private class Holder {
				TextView tv_name;
				ImageView iv_icon;
			}

			LayoutInflater inflater;
			ItemBean goods;
			Cursor c;

			public MyAdapter(Context context, Cursor c, boolean autoRequery) {
				super(context, c, autoRequery);
				// TODO Auto-generated constructor stub
				inflater = LayoutInflater.from(context);
				this.c = c;
			}

			@Override
			public void bindView(View arg0, Context arg1, Cursor arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				Log.i("Zinger", "count():" + getCursor().getCount());
				return getCursor().getCount();
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return super.getItem(position);
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return super.getItemId(position);
			}

			@Override
			public View getView(int position, View convertView, ViewGroup arg2) {
				// TODO Auto-generated method stub
				Holder holder;
				if (convertView == null) {
					holder = new Holder();
					convertView = inflater.inflate(R.layout.item_gridview, null);
					convertView.setTag(holder);
				} else {
					holder = (Holder) convertView.getTag();
				}
				holder.tv_name = (TextView) convertView
						.findViewById(R.id.textView1);
				holder.iv_icon = (ImageView) convertView
						.findViewById(R.id.imageView1);
				c.moveToPosition(position);
				goods = getItemBeanByCursor(c);
				Log.i("Zinger", "Goods_image:" + goods.getGoods_image());
				holder.tv_name.setText(goods.getGoods_chineseName());
				holder.iv_icon.setTag(goods.getGoods_image());
				Drawable bg=ImageUtil.getDrawableByPicName(goods.getGoods_name(), context, "com.example.dota2");
				holder.iv_icon.setBackgroundDrawable(bg);
				return convertView;
			}

		}

		private ItemBean getItemBeanByCursor(Cursor c) {
			ItemBean goods = new ItemBean();
			goods.setGoods_name(c.getString(c
					.getColumnIndex(GoodsColumn.goods_name)));
			goods.setGoods_chineseName(c.getString(c
					.getColumnIndex(GoodsColumn.goods_chineseName)));
			goods.setGoods_image(c.getString(c
					.getColumnIndex(GoodsColumn.goods_image)));
			goods.setGoods_compose_from(c.getString(c
					.getColumnIndex(GoodsColumn.goods_compose_from)));
			goods.setGoods_compose_to(c.getString(c
					.getColumnIndex(GoodsColumn.goods_compose_to)));
			goods.setGoods_price(c.getInt(c.getColumnIndex(GoodsColumn.goods_price)));
			goods.setGoods_disdription(c.getString(c
					.getColumnIndex(GoodsColumn.goods_discription)));
			goods.setGoods_add_skill(c.getString(c
					.getColumnIndex(GoodsColumn.goods_add_skill)));
			return goods;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		}
		
		

