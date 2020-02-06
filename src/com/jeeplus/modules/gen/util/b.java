package com.jeeplus.modules.gen.util;

import java.io.PrintStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import javax.crypto.Cipher;

public class b {
	public static HashMap<String, Object> a1() throws NoSuchAlgorithmException {
		HashMap a1 = new HashMap();
		KeyPairGenerator a3 = KeyPairGenerator.getInstance("RSA");
		a3.initialize(512);
		KeyPair a2 = a3.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) a2.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) a2.getPrivate();
		a1.put("public", publicKey);
		a1.put("private", privateKey);
		return a1;
	}

	public static RSAPublicKey a2(String a1, String a2) {
		try {
			BigInteger b1 = new BigInteger(a1);
			BigInteger b2 = new BigInteger(a2);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static RSAPrivateKey a3(String modulus, String exponent) {
		try {
			BigInteger b1 = new BigInteger(modulus);
			BigInteger b2 = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String a4(String a1, RSAPublicKey a2)
			throws Exception {
		Cipher a3 = Cipher.getInstance("RSA");
		a3.init(1, a2);

		int a4 = a2.getModulus().bitLength() / 8;

		String[] a5 = a11(a1, a4 - 11);
		String a6 = "";

		for (String s : a5) {
			a6 = a6 + a10(a3.doFinal(s.getBytes()));
		}
		return a6;
	}

	public static String a5(String a6, RSAPrivateKey a7)
			throws Exception {
		Cipher a8 = Cipher.getInstance("RSA");
		a8.init(1, a7);

		int key_len = a7.getModulus().bitLength() / 8;

		String[] datas = a11(a6, key_len - 11);
		String mi = "";

		for (String s : datas) {
			mi = mi + a10(a8.doFinal(s.getBytes()));
		}
		return mi;
	}

	public static String a6(String a2, RSAPublicKey a3) throws Exception {
		Cipher a1 = Cipher.getInstance("RSA");
		a1.init(2, a3);

		int key_len = a3.getModulus().bitLength() / 8;
		byte[] bytes = a2.getBytes();
		byte[] bcd = a8(bytes, bytes.length);
		System.err.println(bcd.length);

		String ming = "";
		byte[][] arrays = a12(bcd, key_len);
		for (byte[] arr : arrays) {
			ming = ming + new String(a1.doFinal(arr));
		}
		return ming;
	}

	public static String a7(String a2, RSAPrivateKey a3) throws Exception {
		Cipher a1 = Cipher.getInstance("RSA");
		a1.init(2, a3);

		int a4 = a3.getModulus().bitLength() / 8;
		byte[] a5 = a2.getBytes();
		byte[] b1 = a8(a5, a5.length);
		System.err.println(b1.length);

		String ming = "";
		byte[][] arrays = a12(b1, a4);
		for (byte[] arr : arrays) {
			ming = ming + new String(a1.doFinal(arr));
		}
		return ming;
	}

	public static byte[] a8(byte[] ascii, int asc_len) {
		byte[] bcd = new byte[asc_len / 2];
		int j = 0;
		for (int i = 0; i < (asc_len + 1) / 2; i++) {
			bcd[i] = a9(ascii[(j++)]);
			bcd[i] = (byte) ((j >= asc_len ? 0 : a9(ascii[(j++)])) + (bcd[i] << 4));
		}
		return bcd;
	}

	public static byte a9(byte asc) {
		byte bcd;
		if ((asc >= 48) && (asc <= 57)) {
			bcd = (byte) (asc - 48);
		} else {
			if ((asc >= 65) && (asc <= 70)) {
				bcd = (byte) (asc - 65 + 10);
			} else {
				if ((asc >= 97) && (asc <= 102))
					bcd = (byte) (asc - 97 + 10);
				else
					bcd = (byte) (asc - 48);
			}
		}
		return bcd;
	}

	public static String a10(byte[] bytes) {
		char[] temp = new char[bytes.length * 2];

		for (int i = 0; i < bytes.length; i++) {
			char val = (char) ((bytes[i] & 0xF0) >> 4 & 0xF);
			temp[(i * 2)] = (char) (val > '\t' ? val + 'A' - 10 : val + '0');

			val = (char) (bytes[i] & 0xF);
			temp[(i * 2 + 1)] = (char) (val > '\t' ? val + 'A' - 10 : val + '0');
		}
		return new String(temp);
	}

	public static String[] a11(String a1, int a2) {
		int x = a1.length() / a2;
		int y = a1.length() % a2;
		int z = 0;
		if (y != 0) {
			z = 1;
		}
		String[] a3 = new String[x + z];
		String a4 = "";
		for (int i = 0; i < x + z; i++) {
			if ((i == x + z - 1) && (y != 0))
				a4 = a1.substring(i * a2, i * a2 + y);
			else {
				a4 = a1.substring(i * a2, i * a2 + a2);
			}
			a3[i] = a4;
		}
		return a3;
	}

	public static byte[][] a12(byte[] a2, int a3) {
		int x = a2.length / a3;
		int y = a2.length % a3;
		int z = 0;
		if (y != 0) {
			z = 1;
		}
		byte[][] a1 = new byte[x + z][];

		for (int i = 0; i < x + z; i++) {
			byte[] arr = new byte[a3];
			if ((i == x + z - 1) && (y != 0))
				System.arraycopy(a2, i * a3, arr, 0, y);
			else {
				System.arraycopy(a2, i * a3, arr, 0, a3);
			}
			a1[i] = arr;
		}
		return a1;
	}
}