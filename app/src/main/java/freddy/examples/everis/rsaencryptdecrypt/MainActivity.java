package freddy.examples.everis.rsaencryptdecrypt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import freddy.examples.everis.rsaencryptdecrypt.util.RSAKeyPairGenerator;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.textViewEncrypt)
    TextView textViewEncrypt;
    @BindView(R.id.textViewDecrypt)
    TextView textViewDecrypt;
    private RSAKeyPairGenerator rsaKeyPairGenerator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
       rsaKeyPairGenerator = new RSAKeyPairGenerator();
       /* try {
            byte[] data = rsaKeyPairGenerator.RSAEncrypt("HOLA MUNDO");
            String decrypt = rsaKeyPairGenerator.RSADecrypt(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }*/
    }

    @OnClick(R.id.btn_encrypt)
    public void encryptBtn() {
        if (editText.getText().toString().trim().isEmpty()) {
            Toast.makeText(getBaseContext(), "LLena el campo ps mascotaaa", Toast.LENGTH_LONG).show();
            return;
        }
        encrypt(editText.getText().toString().trim());
    }

    @OnClick(R.id.btn_decrypt)
    public void decryptBtn() {
        if (textViewEncrypt.getText().toString().trim().isEmpty()) {
            Toast.makeText(getBaseContext(), "Aun no has encryptado nada ...", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            decrypt(textViewEncrypt.getText().toString().trim());
        } catch (IllegalBlockSizeException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    private void decrypt(String txt) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

        try {
            byte[] data = Hex.decodeHex(txt);
            textViewDecrypt.setText(rsaKeyPairGenerator.RSADecrypt(data));
        } catch (DecoderException e) {
            e.printStackTrace();
        }
    }


    private void encrypt(String txt) {
        try {
            byte[] data = rsaKeyPairGenerator.RSAEncrypt(txt);
            textViewEncrypt.setText(new String(org.apache.commons.codec.binary.Hex.encodeHex(data)));

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    }

}
