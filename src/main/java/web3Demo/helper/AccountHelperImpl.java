package web3Demo.helper;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.http.HttpService;

@Component
public class AccountHelperImpl implements AccountHelper {
	
	@Value("${wallet.password}")
	private String walletPassword;
	
	private Admin admin = Admin.build(new HttpService());
	
	@Override
	public Boolean unlockAccount(String address) throws IOException {
		PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(address, walletPassword).send();
		if(personalUnlockAccount.getError() != null) {
			System.out.println(personalUnlockAccount.getError().getMessage());
		}
		//this piece of crap does not return true or false but true or null
		return personalUnlockAccount.accountUnlocked() != null && personalUnlockAccount.accountUnlocked();
	}

}
