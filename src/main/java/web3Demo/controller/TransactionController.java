package web3Demo.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

import web3Demo.helper.TransactionHelper;
import web3Demo.model.ContractQueryRequest;
import web3Demo.model.ContractTransactionRequest;
import web3Demo.model.EtherTransactionRequest;

@RestController
public class TransactionController {

	@Autowired
	TransactionHelper transactionHelper;

	@PostMapping("/transaction/sendEther")
	public @ResponseBody ResponseEntity<TransactionReceipt> sendEther(
			@RequestBody EtherTransactionRequest transactionRequest)
			throws IOException, InterruptedException, TransactionException, ExecutionException {

		System.out.println("From address = " + transactionRequest.getFromAddress());
		System.out.println("To address = " + transactionRequest.getToAddress());
		System.out.println("amount = " + transactionRequest.getAmount());

		TransactionReceipt receipt = transactionHelper.sendEtherTransaction(transactionRequest);

		return new ResponseEntity<TransactionReceipt>(receipt, HttpStatus.OK);
	}

	@PostMapping("/transaction/queryContract")
	public @ResponseBody ResponseEntity<String> queryContract(@RequestBody ContractTransactionRequest contractTransactionRequest)
			throws Exception {

		System.out.println("from address = " + contractTransactionRequest.getFromAddress());
		System.out.println("Contract address = " + contractTransactionRequest.getContractAddress());
		System.out.println("function = " + contractTransactionRequest.getFunctionName());

		String receipt = transactionHelper.queryContract(contractTransactionRequest);

		return new ResponseEntity<String>(receipt, HttpStatus.OK);

	}

	@PostMapping("/transaction/contract")
	public @ResponseBody ResponseEntity<TransactionReceipt> contractTransaction(
			@RequestBody ContractTransactionRequest contractTransactionRequest) throws Exception {
		
		System.out.println("functionName = " + contractTransactionRequest.getFunctionName());
		System.out.println("contractAddress = " + contractTransactionRequest.getContractAddress());
		System.out.println("fromAddress = " + contractTransactionRequest.getFromAddress());
		System.out.println("gasPrice = " + contractTransactionRequest.getGasPrice());
		System.out.println("gasLimit = " + contractTransactionRequest.getGasLimit());
		System.out.println("funds = " + contractTransactionRequest.getFunds());

		TransactionReceipt receipt = transactionHelper.sendContractTransaction(contractTransactionRequest);

		return new ResponseEntity<TransactionReceipt>(receipt, HttpStatus.OK);
	}

}
