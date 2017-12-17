package web3Demo.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

import web3Demo.model.Account;

public interface Web3Service {

	public List<Account> getAccounts() throws InterruptedException, ExecutionException;
	
	public TransactionReceipt sendEther();
	
}
