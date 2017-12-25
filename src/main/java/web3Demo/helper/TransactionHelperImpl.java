package web3Demo.helper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert.Unit;

import web3Demo.Hello_World;
import web3Demo.model.ContractTransactionRequest;
import web3Demo.model.EtherTransactionRequest;

@Component
public class TransactionHelperImpl implements TransactionHelper {

	@Autowired
	private Web3j web3j;
	
	@Autowired
	private AccountHelper accountHelper;

	@Override
	public TransactionReceipt sendEtherTransaction(EtherTransactionRequest transactionRequest)
			throws IOException, InterruptedException, TransactionException, ExecutionException {

		if (accountHelper.unlockAccount(transactionRequest.getFromAddress())) {
			ClientTransactionManager transactionManager = new ClientTransactionManager(web3j,transactionRequest.getFromAddress());
			Transfer transfer = new Transfer(web3j,transactionManager);
			return transfer.sendFunds(transactionRequest.getToAddress(), BigDecimal.valueOf(transactionRequest.getAmount().longValue()), Unit.WEI).sendAsync().get();
		}
		return null;
	}

	@Override
	public BigInteger queryContract(ContractTransactionRequest contractTransactionRequest) throws Exception {

		if (!accountHelper.unlockAccount(contractTransactionRequest.getFromAddress())){
			return null;
		}
		
		ClientTransactionManager transactionManager = new ClientTransactionManager(web3j,contractTransactionRequest.getFromAddress());
		
		Hello_World contractHelloWorld = Hello_World.load(
				contractTransactionRequest.getContractAddress(), 
				web3j, 
				transactionManager, 
				contractTransactionRequest.getGasPrice(), 
				contractTransactionRequest.getGasLimit());
		
		return contractHelloWorld.getCounter().send();
	}

	@Override
	public TransactionReceipt sendContractTransaction(ContractTransactionRequest contractTransactionRequest) throws Exception {
		
		if (!accountHelper.unlockAccount(contractTransactionRequest.getFromAddress())){
			return null;
		}
		
		ClientTransactionManager transactionManager = new ClientTransactionManager(web3j,contractTransactionRequest.getFromAddress());

		Hello_World contractHelloWorld = Hello_World.load(
				contractTransactionRequest.getContractAddress(), 
				web3j, 
				transactionManager, 
				contractTransactionRequest.getGasPrice(), 
				contractTransactionRequest.getGasLimit());
		
		return contractHelloWorld.subtract().send();
	}
}
