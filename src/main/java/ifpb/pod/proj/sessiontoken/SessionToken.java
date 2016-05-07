package ifpb.pod.proj.sessiontoken;


import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;

/**
 * Created by vmvini on 07/05/16.
 */
public class SessionToken {


    private static Key getSecretKey() throws UnsupportedEncodingException{

            String raw = "201u30i2j1sok21mslkmlisua9uw103j21knkns.,M,X.NJHUWHQIWU2HK3J1NS09JOKNXSXKMSANDKJOWIEJ12O";
            byte[] apiKeySecretBytes = raw.getBytes("UTF-8");
            Key key = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

            return key;
    }

    public static String generateToken(String userEmail){
        try {
            JwtBuilder builder = Jwts.builder()
                    .setSubject(userEmail) //email para identificar o usuario
                    .signWith(SignatureAlgorithm.HS256, getSecretKey());

             String token = builder.compact();
            return token;
        }
        catch(UnsupportedEncodingException e){
            return null;
        }

    }


    //METODO PARA DECODIFICAR O TOKEN E RETORNAR O EMAIL QUE ELE POSSUI, EM CASO DE TOKEN VÁLIDO.
    public static String getEmailFromToken(String token){

        try {
            String userEmail = Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody().getSubject();
            return userEmail;
        }
        catch(UnsupportedEncodingException e){
            return null;
        }
        catch(SignatureException e){
            return null;
        }

    }


}
