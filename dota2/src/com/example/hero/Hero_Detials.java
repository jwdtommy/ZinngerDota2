package com.example.hero;

import java.util.ArrayList;

import android.R.color;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.DBManager;
import com.example.db.SkillColumn;
import com.example.dota2.GoodsDetailFrag;
import com.example.dota2.ListFrag;
import com.example.dota2.R;
import com.example.model.HeroBean;
import com.example.model.ItemBean;
import com.example.model.SkillBean;
import com.jwd.utils.ImageUtil;
import com.jwd.utils.MatcherUtil;
import com.jwd.views.TopBar;

public class Hero_Detials extends Activity implements OnItemClickListener
{
	private TextView hero_name, hero_hp, hero_mp, hero_strong, hero_sharp, hero_intellenge, hero_catelog, hero_attack, hero_armor, hero_attackrange, hero_skill_descrpt, hero_movespeed, hero_add_strong, hero_add_sharp, hero_add_intellenge;
	private ImageView hero_image;

	private GridView skill_Gridview, goods_Gridview;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hero_detial);
		Bundle bundle=getIntent().getExtras();
		HeroBean heroBean = (HeroBean)bundle.getSerializable("hero_bean");
		ArrayList<SkillBean> skillBean = (ArrayList<SkillBean>) bundle.getSerializable("skill_bean");
		GetDetialAsyn detialAsyn = new GetDetialAsyn(heroBean);
		detialAsyn.execute();
	}

	private void initTopBar(String heroName)
	{
		TopBar topBar = (TopBar) findViewById(R.id.topbar);
		topBar.setTitle(heroName);
		topBar.showButton1(true);
		topBar.getButton1().setText(getString(R.string.back));
		topBar.getButton1().setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
			Hero_Detials.this.finish();
			}
		});
	}

	private void initHeroDetial(HeroBean heroBean, ArrayList<SkillBean> skillBean, ArrayList<ItemBean> itemBeans)
	{
		hero_name.setText(heroBean.getHero_name() + "");
		hero_hp.setText(new MatcherUtil().changeTextColor(getString(R.string.def_hp)+"£∫" +heroBean.getHero_def_hp(),MatcherUtil.REGULAR_MATCHNUM , null, Color.RED));
		hero_mp.setText(new MatcherUtil().changeTextColor(getString(R.string.def_mp)+"£∫" +heroBean.getHero_def_mp(),MatcherUtil.REGULAR_MATCHNUM ,null, Color.BLUE));
		hero_strong.setText("£∫" + heroBean.getHero_def_strong() + "");
		hero_sharp.setText("£∫" + heroBean.getHero_def_sharp() + "");
		hero_intellenge.setText("£∫" + heroBean.getHero_def_intelligence() + "");
		hero_catelog.setText(heroBean.getHero_description() + "");
		hero_attack.setText("£∫" + heroBean.getHero_def_attack() + "");
		hero_armor.setText("£∫" + heroBean.getHero_def_armor() + "");
		hero_movespeed.setText("£∫" + heroBean.getHero_def_speed() + "");
		hero_attackrange.setText("£∫" + heroBean.getHero_att_range() + "");
		hero_add_strong.setText(" + " + heroBean.getHero_up_strong());
		hero_add_sharp.setText(" + " + heroBean.getHero_up_sharp());
		hero_add_intellenge.setText(" + " + heroBean.getHero_up_intellience());

		hero_skill_descrpt.setText(getSkillInfo(skillBean.get(0)));
		Drawable bg = ImageUtil.getDrawableByPicName(heroBean.getHero_name_en(), this, "com.example.dota2");
		hero_image.setBackgroundDrawable(bg);
		skill_Gridview.setAdapter(new Hero_Skill_Adapter(this, skillBean));
		goods_Gridview.setAdapter(new Recommend_Goods(this, itemBeans));
	}

	private void initWedget()
	{

		hero_name = (TextView)findViewById(R.id.herodetial_name);
		hero_hp = (TextView) findViewById(R.id.herodetial_hp);
		hero_mp = (TextView) findViewById(R.id.herodetial_mp);
		hero_strong = (TextView) findViewById(R.id.herodetial_strong);
		hero_sharp = (TextView) findViewById(R.id.herodetial_sharp);
		hero_intellenge = (TextView) findViewById(R.id.herodetial_intelligence);
		hero_add_strong = (TextView) findViewById(R.id.herodetial_add_strong);
		hero_add_sharp = (TextView) findViewById(R.id.herodetial_add_sharp);
		hero_add_intellenge = (TextView) findViewById(R.id.herodetial_add_intelligence);
		hero_catelog = (TextView) findViewById(R.id.herodetial_descrpt);
		hero_attack = (TextView) findViewById(R.id.herodetial_attack);
		hero_armor = (TextView) findViewById(R.id.herodetial_armor);
		hero_attackrange = (TextView) findViewById(R.id.herodetial_attackrange);
		skill_Gridview = (GridView) findViewById(R.id.herodetial_skill_gridview);
		hero_image = (ImageView) findViewById(R.id.herodetial_image);
		hero_movespeed = (TextView) findViewById(R.id.herodetial_movespeed);
		hero_skill_descrpt = (TextView) findViewById(R.id.herodetial_skill_descrpt);
		goods_Gridview = (GridView) findViewById(R.id.herodetial_goods_gridview);
		skill_Gridview.setOnItemClickListener(this);
		goods_Gridview.setOnItemClickListener(this);
	}

	class Recommend_Goods extends BaseAdapter
	{
		private Context context;
		private ArrayList<ItemBean> data;

		Recommend_Goods(Context context, ArrayList<ItemBean> data)
		{
			this.data = data;
			this.context = context;
		}

		@Override
		public int getCount()
		{

			return data.size();
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			convertView = layoutInflater.inflate(R.layout.item_gridview_image, null);
			ImageView goodsImage = (ImageView) convertView.findViewById(R.id.imageView1);
//			TextView goodsName = (TextView) convertView.findViewById(R.id.textView1);
//			goodsName.setText(data.get(position).getGoods_chineseName());
			Drawable bg = ImageUtil.getDrawableByPicName(data.get(position).getGoods_name(), context, "com.example.dota2");
			goodsImage.setBackgroundDrawable(bg);
			return convertView;
		}

	}

	class Hero_Skill_Adapter extends BaseAdapter
	{
		private Context context;
		private ArrayList<SkillBean> data;

		Hero_Skill_Adapter(Context context, ArrayList<SkillBean> data)
		{
			this.data = data;
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2)
		{
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			convertView = layoutInflater.inflate(R.layout.item_gridview, null);
			ImageView skillImage = (ImageView) convertView.findViewById(R.id.imageView1);
			TextView skillName = (TextView) convertView.findViewById(R.id.textView1);
			skillName.setText(data.get(position).getSkill_name());
			Drawable bg = ImageUtil.getDrawableByPicName(data.get(position).getSkill_name_en(), context, "com.example.dota2");
			skillImage.setBackgroundDrawable(bg);
			return convertView;
		}

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

	}

	class GetDetialAsyn extends AsyncTask<Object, Object, Object>
	{
		private HeroBean heroBean;
		private ArrayList<ItemBean> itemBeans = new ArrayList<ItemBean>();
		private ProgressDialog progressDialog;
		private ArrayList<SkillBean> skillBeans;
		private DBManager db;
		private SQLiteDatabase sqldb;
		GetDetialAsyn(HeroBean heroBean)
		{
			db = new DBManager(Hero_Detials.this, "test");
			sqldb = db.getWritableDatabase();
			itemBeans.clear();
			this.heroBean = heroBean;
		}

		@Override
		protected void onPreExecute()
		{
			progressDialog=new ProgressDialog(Hero_Detials.this).show(Hero_Detials.this, "∂¡»°÷–", "");
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Object... params)
		{

			skillBeans = getSkillBeanById(heroBean.getHero_id());
			String[] recommand_goods = heroBean.getRecommend_goods().split(",");
			String sql = "select * from Goods where ";
			for (int i = 0; i < recommand_goods.length; i++)
			{
				if (i != recommand_goods.length - 1)
					sql = sql + "goods_name='" + recommand_goods[i] + "' or ";
				else
					sql = sql + "goods_name='" + recommand_goods[i] + "'";
			}
			sql = sql + " order by goods_price asc ";


			Cursor cursor = sqldb.rawQuery(sql, null);
			for (int i = 0; i < cursor.getCount(); i++)
			{

				cursor.moveToNext();
				itemBeans.add(ListFrag.getItemBeanByCursor(cursor));
			}
			cursor.close();
			publishProgress();
			
			return null;

		}

		@Override
		protected void onPostExecute(Object params)
		{
			initTopBar(heroBean.getHero_name());
			initWedget();
			initHeroDetial(heroBean, skillBeans, itemBeans);
			progressDialog.dismiss();
			sqldb.close();
			super.onPostExecute(params);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		switch (arg0.getId())
		{
		case R.id.herodetial_goods_gridview:			
			ItemBean goods = (ItemBean) arg0.getItemAtPosition(arg2);
			GoodsDetailFrag goodsDetailFrag = new GoodsDetailFrag();
			Bundle data = new Bundle();
			data.putSerializable("itemBean", goods);
			goodsDetailFrag.setArguments(data);
//			switchFrag(R.id.frag, goodsDetailFrag, true);
			break;
		case R.id.herodetial_skill_gridview:
			SkillBean skillBean = (SkillBean) arg0.getItemAtPosition(arg2);
			hero_skill_descrpt.setText(getSkillInfo(skillBean));
			break;
		}
		
		
	}

	private SpannableStringBuilder getSkillInfo(SkillBean skillBean)
	{
		String skill_deital = skillBean.getSkill_detial().replace(" -", "-");
		skill_deital = skill_deital.replace("- ", "-");
		skill_deital = skill_deital.replace(" ", "\n");
		skill_deital=(skillBean.getSkill_name() + "\n" + skillBean.getSkill_discription() + "\n" + skill_deital).replace("\n\n", "\n");

		return new MatcherUtil().changeTextColor(skill_deital, MatcherUtil.REGULAR_MATCHNUM, "º∂", Color.RED);
}
	private ArrayList<SkillBean> getSkillBeanById(int heroId)
	{
		DBManager manager = new DBManager(this, "test");
		SQLiteDatabase db = manager.getWritableDatabase();
		String sql = "";
		ArrayList<SkillBean> skillBeans = new ArrayList<SkillBean>();
		sql = "select * from Skill where hero_id='" + heroId + "'";
		Cursor cursor = db.rawQuery(sql, null);
		try
		{
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++)
		{
			cursor.moveToPosition(i);
			SkillBean skillBean = new SkillBean();
			skillBean.setHero_id(heroId);
			skillBean.setSkill_detial(cursor.getString(cursor.getColumnIndex(SkillColumn.skill_detail)));
			skillBean.setSkill_discription(cursor.getString(cursor.getColumnIndex(SkillColumn.skill_discription)));
			skillBean.setSkill_id(cursor.getInt(cursor.getColumnIndex(SkillColumn.skill_id)));
			skillBean.setSkill_image(cursor.getString(cursor.getColumnIndex(SkillColumn.skill_image)));
			skillBean.setSkill_name(cursor.getString(cursor.getColumnIndex(SkillColumn.skill_name)));
			skillBean.setSkill_name_en(cursor.getString(cursor.getColumnIndex(SkillColumn.skill_name_en)));
			skillBean.setSkill_order_id(cursor.getInt(cursor.getColumnIndex(SkillColumn.skill_order_id)));
			skillBean.setIs_ultimate(cursor.getString(cursor.getColumnIndex(SkillColumn.is_ultimate)));
			skillBeans.add(skillBean);
		}
		} catch (Exception e)
		{
		}finally{
			db.close();
		}
		return skillBeans;

	}
}
