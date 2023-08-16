package autopartsclient.chat;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import com.mojang.brigadier.LiteralMessage;

import autopartsclient.util.ChatUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.network.message.MessageSignatureData;

public class ChatEncryption {

    private static MinecraftClient mc = MinecraftClient.getInstance();

    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static final String ALGORITHM = "AES";

    public static void handleMessage(String message) {
	final String secretKey = "TestKey";

	String parsedMessage;

	parsedMessage = message.substring(1);

	String encryptedMessage = encrypt(parsedMessage, secretKey);

	encryptedMessage = encryptedMessage + "//";

	mc.getNetworkHandler().sendChatMessage(encryptedMessage);

	//handleSentMessage(encryptedMessage);
	// MinecraftClient.getInstance().player.sendMessage(Text.literal(encryptedMessage),
	// false);
    }

    public static void handleSentMessage(String message) {
	final String secretKey = "TestKey";

	String correctChecker = message.substring(Math.max(message.length() - 2, 0));
	switch (correctChecker) {
	case "//": {
	    String parsedMessage = message.substring(0, message.length() - 2);
	    
	    String[] splited = parsedMessage.split("\\s+");
	    
	    System.out.println(splited[0].toString());
	    System.out.println(splited[1].toString());
	    
	    String decryptedMessage = decrypt(splited[1], secretKey);

	    //mc.getNetworkHandler().sendChatMessage(decryptedMessage);
	    //System.out.println("Signature: " + signature);
	    ChatUtils.decryptedMessage(splited[0].toString() + " " + decryptedMessage);

	    break;
	}
	default:
	    return;
	}
    }

    private static String encrypt(String strToEncrypt, String secret) {
	try {
	    prepareSecreteKey(secret);
	    Cipher cipher = Cipher.getInstance(ALGORITHM);
	    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	    return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
	} catch (Exception e) {
	    System.out.println("Error while encrypting: " + e.toString());
	}
	return null;
    }

    private static String decrypt(String strToDecrypt, String secret) {
	try {
	    prepareSecreteKey(secret);
	    Cipher cipher = Cipher.getInstance(ALGORITHM);
	    cipher.init(Cipher.DECRYPT_MODE, secretKey);
	    return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	} catch (Exception e) {
	    System.out.println("Error while decrypting: " + e.toString());
	}
	return null;
    }

    public static void prepareSecreteKey(String myKey) {
	MessageDigest sha = null;
	try {
	    key = myKey.getBytes(StandardCharsets.UTF_8);
	    sha = MessageDigest.getInstance("SHA-1");
	    key = sha.digest(key);
	    key = Arrays.copyOf(key, 16);
	    secretKey = new SecretKeySpec(key, ALGORITHM);
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	}
    }
}
