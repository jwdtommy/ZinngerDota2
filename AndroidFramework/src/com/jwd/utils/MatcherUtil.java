package com.jwd.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

public class MatcherUtil
{
	public final static String REGULAR_MATCHNUM = "([0-9]|%|\\.){1}";

	/**
	 * 
	 * @param content  待替换的文本内容
	 * @param matchRegion  需要匹配的内容（正则表达式方式）参照REGULAR_开头的常量
	 * @param ignoreRegion 忽略字段，需要排除在外的内容。以字符串形式表示
	 * @param color	需要改变的颜色
	 * @return SpannableStringBuilder 
	 */
	public SpannableStringBuilder changeTextColor(String content, String matchRegion, String ignoreRegion, int color)
	{
		Pattern pattern = Pattern.compile(matchRegion);
		Matcher matcher;
		SpannableStringBuilder style = new SpannableStringBuilder(content);

		for (int i = 0; i < content.length(); i++)
		{
			matcher = pattern.matcher(content.charAt(i) + "");
			while (matcher.find())
			{
				if(null!=ignoreRegion)
				if ((i - 1)!=-1&&(content.charAt(i - 1) + "").equals(ignoreRegion))
					continue;
				style.setSpan(new ForegroundColorSpan(color), i, i + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}
		}
		return style;

	}
}
