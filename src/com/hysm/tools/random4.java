/**
 * 
 */
package com.hysm.tools;

import java.util.Random;

/**
 * XXXXXXXX
 * @author cgb
 * @date 2017年5月6日
 */

public class random4 {
	/**
	 * 随机生成四位数验证码
	 * 
	 * @return
	 */
	public static int getRandomNum4() {
		Random r = new Random();
		int temp =r.nextInt(9000) + 1000;
		return temp;// (Math.random()*(9999-1000)+1000)
	}
	
	public static int getRandomNum6() {
		Random r = new Random();
		int temp =r.nextInt(899999) + 100000;
		return temp;// (Math.random()*(9999-1000)+1000)
	}

}
