import * as CryptoJS from 'crypto-js';

export class crypt {

    public static encrypt(strString :string) :string
    {
        let encryptedBase64Key = "bXVzdGJlMTZieXRlc2tleQ==";
        let parsedBase64Key = CryptoJS.enc.Base64.parse(encryptedBase64Key);
        let encryptedData = null;
        let passwordNotEncrypted = strString.toString();
        {
        encryptedData = CryptoJS.AES.encrypt(passwordNotEncrypted, parsedBase64Key, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
        });
     }
        return encryptedData;
    }
    
}

//Constants.encrypt('123');