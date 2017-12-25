package web3Demo.helper;

import java.io.IOException;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.ClientTransactionManager;

import web3Demo.Hello_World;
import web3Demo.model.ContractTransactionRequest;

@Component
public class HelloWorldHelper {

	@Autowired
	private Web3j web3j;
	
	@Autowired
	private AccountHelper accountHelper;

	public BigInteger getCounter(ContractTransactionRequest contractTransactionRequest) throws Exception {
		return loadContract(contractTransactionRequest).getCounter().send();
	}

	public TransactionReceipt add(ContractTransactionRequest contractTransactionRequest) throws Exception {
		unlockAccount(contractTransactionRequest.getFromAddress());
		return loadContract(contractTransactionRequest).add().send();
	}

	public TransactionReceipt subtract(ContractTransactionRequest contractTransactionRequest) throws Exception {
		unlockAccount(contractTransactionRequest.getFromAddress());
		return loadContract(contractTransactionRequest).subtract().send();
	}
	
	private void unlockAccount(String address) throws IOException {
		if (!accountHelper.unlockAccount(address)) {
			throw new java.lang.RuntimeException("Could not unlock account");
		}
	}
	
	private Hello_World loadContract(ContractTransactionRequest request) {
		return Hello_World.load(
				request.getContractAddress(), 
				web3j, 
				new ClientTransactionManager(web3j, request.getFromAddress()), 
				request.getGasPrice(),
				request.getGasLimit());
	}
}
