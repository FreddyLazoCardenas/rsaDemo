package freddy.examples.everis.rsaencryptdecrypt.util;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class RSAEncryptUtil {

    private static RSAEncryptUtil instance;

    private Cipher encryptCipher;

    public RSAEncryptUtil(Context context) {
        try {

            X509Certificate cert = X509Certificate.getInstance(context.getResources().getAssets().open("public.crt"));
            encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            encryptCipher.init(Cipher.ENCRYPT_MODE, cert.getPublicKey());
        } catch (IOException | CertificateException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            Log.e(RSAEncryptUtil.class.getSimpleName(), "encode: ", e);
        }
    }

    public static RSAEncryptUtil getInstance(Context context){
        if(instance == null){
            instance =  new RSAEncryptUtil(context);
        }
        return instance;
    }

    public String encode(String value){
        String messageCrypteB64 = "";
        try {
            byte[] messageACrypter = value.getBytes();
            byte[] messageCrypte = encryptCipher.doFinal(messageACrypter);
            messageCrypteB64 = Base64.encodeToString(messageCrypte, Base64.DEFAULT);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            Log.e(RSAEncryptUtil.class.getSimpleName(), "encode: ", e);
        }
        return messageCrypteB64;
    }
}
