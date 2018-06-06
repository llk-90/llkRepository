package test;

import java.math.BigInteger;

public class Test {

	public static void main(String[] args) {
		
		/*BigInteger num = new BigInteger("214716589363191755726389250");
		num = num.setBit(13);
		System.out.println(num);*/
		BigInteger num = new BigInteger("214716589363191755726397442");//77388953295225712417964034  77388953295225712417972226
		int[] arr = {1,
				13,
				20,
				29,
				31,
				32,
				37,
				38,
				39,
				40,
				42,
				43,
				44,
				45,
				46,
				47,
				48,
				49,
				50,
				51,
				55,
				57,
				58,
				59,
				60,
				61,
				62,
				63,
				64,
				65,
				66,
				67,
				68,
				69,
				71,
				72,
				73,
				74,
				75,
				76,
				77,
				79,
				80,
				81,
				82,
				83,
				84,
				85,
				86,
				87,
				3333};
		for(int num2 :arr) {
			if(testRights(num, num2)) {
			 System.out.println("存在其中的权限"+num2);
			 
			}
		}
		//System.out.println(num.clearBit(13));
	}

	
	public static boolean testRights(BigInteger sum,int targetRights){
		return sum.testBit(targetRights);
	}
	
	
	
}
