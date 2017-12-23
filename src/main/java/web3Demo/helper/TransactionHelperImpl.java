package web3Demo.helper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert.Unit;

import web3Demo.Hello_World;
import web3Demo.model.ContractTransactionRequest;
import web3Demo.model.EtherTransactionRequest;
import org.web3j.abi.datatypes.Type;

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
	public String queryContract(ContractTransactionRequest contractTransactionRequest) throws Exception {

		//TODO unlock account first
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
		
		RemoteCall<BigInteger> getCounter = contractHelloWorld.getCounter();
		
		BigInteger counterValue = getCounter.send();
		
		return counterValue.toString();
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
		
		TransactionReceipt receipt = contractHelloWorld.add().send();
		
		return receipt;
		
		/*
		
		
		
		
		
		
		
		Function function = new Function(contractTransactionRequest.getFunctionName(), 
				Arrays.<Type>asList(), // Parameters to pass as Solidity Types
				Collections.<TypeReference<?>>emptyList());
		
		String encodedFunction = FunctionEncoder.encode(function);
		
		Transaction transaction = Transaction.createFunctionCallTransaction(
				contractTransactionRequest.getFromAddress(),
				getNonce(contractTransactionRequest.getFromAddress()),
				contractTransactionRequest.getGasPrice(),
				contractTransactionRequest.getGasLimit(),
				contractTransactionRequest.getContractAddress(),
				contractTransactionRequest.getFunds(),
				encodedFunction );
		
		//org.web3j.protocol.core.methods.request.Transaction.createFunctionCallTransaction(String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, String data)
		
		EthSendTransaction transactionResponse = web3j.ethSendTransaction(transaction).sendAsync().get();

		String transactionHash = transactionResponse.getTransactionHash();

	// wait for response using EthGetTransactionReceipt...
		
		
		EthGetTransactionReceipt transactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).sendAsync().get();
		
		
		
		
		
		return transactionReceipt; */
	}
	
	private BigInteger getNonce(String address) throws InterruptedException, ExecutionException {
		EthGetTransactionCount ethGetTransactionCount = web3j
				.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).sendAsync().get();
		return ethGetTransactionCount.getTransactionCount();
	}

}
