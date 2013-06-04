package com.example.dota2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.db.DBManager;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity
{
	private Context context;
	private ListFrag listFrag;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		listFrag = new ListFrag();
		initSlidingMenu();
		Bundle data = new Bundle();
		data.putInt("flag", ListFrag.Flag_HERO);
		listFrag.setArguments(data);
		switchFrag(R.id.frag, listFrag);
		DBManager.copyDatabaseFile(context, false, "test");
		// DBManager db = new DBManager(context, "test",1);
		// SQLiteDatabase dbs=db.getWritableDatabase();
		// db.onUpgrade(dbs, 1, 2);
		// ExecutorService executorService =Executors.newCachedThreadPool();
		// executorService.execute(new Runnable() {
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// AnaylizeGoods anayGoods=new AnaylizeGoods(context);
		// HashSet<String> names=anayGoods.getAllGoodsName();
		// anayGoods.getAllGoodsInfoByNames(names);
		// }
		// });
		// ExecutorService executorService1 =Executors.newFixedThreadPool(10);
		// final Anaylize anaylize=new Anaylize(this);
		// Executors.newSingleThreadExecutor();
		// executorService1.execute(new Runnable() {
		// @Override
		// public void run() {
		// ArrayList<String> addrList=new ArrayList<String>();
		// try {
		// for (int i = 1; i < 11; i++) {
		// addrList=anaylize.anaylizeAddress("http://db.dota2.uuu9.com/hero/list?p="+(i+1),addrList);
		// }
		//
		// for (int i = 0; i < addrList.size(); i++) {
		// ArrayList<String> resultList =
		// anaylize.anaylizeData(urlHeader+addrList.get(i));
		// anaylize.saveData(resultList,i);
		// }
		// /*
		// * ArrayList<String> resultList =
		// *
		// anaylize.anaylizeData("http://db.dota2.uuu9.com/hero/show/invoker");
		// * anaylize.saveData(resultList,0);
		// */
		//
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } finally {
		// Log.i("Dota2", "Done");
		// }
		// }
		// });
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("onResume", "onResume");
	}

	public void switchFrag(int Rid, Fragment frag)
	{
		try
		{
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(Rid, frag);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(null);
			ft.commit();// 提交
		} catch (Exception e)
		{
		}
	}

	private void initSlidingMenu()
	{
		this.setBehindContentView(R.layout.slidingmenu_container_left);
		this.setContentView(R.layout.activity_main);

		SlidingMenu sm = this.getSlidingMenu();
		// sm.setSecondaryMenu(R.layout.slidingmenu_container_right);
		// sm.setSecondaryShadowDrawable(R.drawable.sm_shadow_right);
		sm.setShadowWidthRes(R.dimen.sm_shadow_width);
		sm.setShadowDrawable(R.drawable.sm_shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setMode(SlidingMenu.LEFT);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	@Override
	public void onBackPressed()
	{
		new AlertDialog.Builder(this).setTitle("提示").setMessage("确定要退出么？").setPositiveButton("确定", new DialogInterface.OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// TODO Auto-generated method stub
				MainActivity.this.finish();
			}
		}).setNegativeButton("取消", null).show();
		Log.i("Zinger", "onBack");
//		super.onBackPressed();
	}
}
