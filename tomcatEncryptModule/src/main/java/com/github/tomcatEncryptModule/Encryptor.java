package com.github.tomcatEncryptModule;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
/**
 * cmd : java -jar tomcat_encryptModule.jar
 * @author JHY
 *
 */
public class Encryptor {

    private static final String ALGORITHM = "AES";

    private static final String defaultSecretKey = "secretKey";

    private Key secretKeySpec;

    public Encryptor() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            UnsupportedEncodingException {
        this(null);
    }

    public Encryptor(String secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            UnsupportedEncodingException {
        this.secretKeySpec = generateKey(secretKey);
    }

    public String encrypt(String plainText) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        return asHexString(encrypted);
    }

    public String decrypt(String encryptedString) throws InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] original = cipher.doFinal(toByteArray(encryptedString));
        return new String(original);
    }

    private Key generateKey(String secretKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (secretKey == null) {
            secretKey = defaultSecretKey;
        }
        byte[] key = (secretKey).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // use only the first 128 bit

        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128); // 192 and 256 bits may not be available

        return new SecretKeySpec(key, ALGORITHM);
    }

    private final String asHexString(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                strbuf.append("0");
            }
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }

    private final byte[] toByteArray(String hexString) {
        int arrLength = hexString.length() >> 1;
        byte buf[] = new byte[arrLength];
        for (int ii = 0; ii < arrLength; ii++) {
            int index = ii << 1;
            String l_digit = hexString.substring(index, index + 2);
            buf[ii] = (byte) Integer.parseInt(l_digit, 16);
        }
        return buf;
    }

    public static void main(String[] args) throws Exception {
    	encryptor();
    }

    public static Object encryptor() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
    	Scanner sc = new Scanner(System.in);
		String secretKey = "";
		String mode = "";
		String text = "";
		System.out.print("secretKey를 입력하세요. (종료는 exit 입력) : ");
		
		secretKey = sc.next();

		if("exit".equals(secretKey)) {
			System.out.println("====== 암호화 프로그램종료 ======");
			System.exit(0);
		}else if(!"".equals(secretKey) && secretKey!=null){
			Encryptor ae = new Encryptor(secretKey);
			
			System.out.print("암호화 mode 선택 (1.encrypt / 2.decrypt) : ");
			mode = sc.next();
			
			if("1".equals(mode)){
				System.out.print("암호화 할 string 를 입력 : ");
				text = sc.next();
				String encryptedString = ae.encrypt(text);
		        String decryptedString = ae.decrypt(encryptedString);
		        System.out.println("secretKey : "+secretKey+"\ntext: "+decryptedString + "\n암호화 값: " + encryptedString);
		        System.out.print("[Server.xml에 해당하는 필드값에 넣어주세요.]\n");
			}else if("2".equals(mode)) {
				System.out.print("복호화 할 string 를 입력 :");
				text = sc.next();
                System.out.println("secretKey : "+secretKey+"\ndecryptedString : " + ae.decrypt(text));
			}else {
				System.out.print("암호화 mode 잘못선택하였습니다.\n");
				return encryptor();
			}
		}else {
			System.out.print("secretKey를 입력하지 않았습니다.");
			return encryptor();
		}
		return encryptor();
    }
    
}
