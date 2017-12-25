package web3Demo.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import web3Demo.helper.HelloWorldHelper;
import web3Demo.model.ContractTransactionRequest;

@RestController
public class ContractController {
	
	@Autowired
	HelloWorldHelper helloWorldHelper;

	@PostMapping("/helloworld/getCounter")
	public @ResponseBody ResponseEntity<BigInteger> getCounter(
			@RequestBody ContractTransactionRequest contractTransactionRequest) throws Exception {

		BigInteger result = helloWorldHelper.getCounter(contractTransactionRequest);

		return new ResponseEntity<BigInteger>(result, HttpStatus.OK);

	}

	@PostMapping("/helloworld/add")
	public @ResponseBody ResponseEntity<TransactionReceipt> add(
			@RequestBody ContractTransactionRequest contractTransactionRequest) throws Exception {

		TransactionReceipt receipt = helloWorldHelper.add(contractTransactionRequest);

		return new ResponseEntity<TransactionReceipt>(receipt, HttpStatus.OK);
	}

	@PostMapping("/helloworld/subtract")
	public @ResponseBody ResponseEntity<TransactionReceipt> subtract(
			@RequestBody ContractTransactionRequest contractTransactionRequest) throws Exception {

		TransactionReceipt receipt = helloWorldHelper.subtract(contractTransactionRequest);

		return new ResponseEntity<TransactionReceipt>(receipt, HttpStatus.OK);
	}

}
