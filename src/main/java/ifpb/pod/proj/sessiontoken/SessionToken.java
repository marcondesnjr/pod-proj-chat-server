package ifpb.pod.proj.sessiontoken;


import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;

/**
 * Created by vmvini on 07/05/16.
 */
public class SessionToken {


    private static String getSecretKey(){
        return "201u30i2j1sok21mslkmlisua9uw103j21knkns.,M,X.NJHUWHQIWU2HK3J1NS09JOKNXSXKMSANDKJOWIEJ12O";
    }

    public static String generateToken(String userEmail){
        try {
            byte[] apiKeySecretBytes = getSecretKey().getBytes("UTF-8");

            Key key = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());


            JwtBuilder builder = Jwts.builder()
                    .setSubject("teste") //TEMPORARIO
                    .signWith(SignatureAlgorithm.HS256, key);

            //AINDA FALTA COLOCAR DADOS DO USUARIO NESSE TOKEN


            String token = builder.compact();
            return token;
        }
        catch(UnsupportedEncodingException e){
            return null;
        }

    }

    //METODO PARA DECODIFICAR O TOKEN E RETORNAR O EMAIL QUE ELE POSSUI, EM CASO DE TOKEN VÁLIDO.
    public String getEmailFromToken(){
        return null;
    }


}
