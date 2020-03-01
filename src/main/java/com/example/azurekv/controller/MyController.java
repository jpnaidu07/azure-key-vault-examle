package com.example.azurekv.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.certificates.CertificateClient;
import com.azure.security.keyvault.certificates.CertificateClientBuilder;
import com.azure.security.keyvault.certificates.models.KeyVaultCertificateWithPolicy;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;

@RestController
public class MyController {
	
	@Value("${hws-user-name}")
	private String props;

    @GetMapping(value="/", produces = MediaType.TEXT_PLAIN_VALUE)
    public String index() {
    	System.out.println(props);
    	
    	ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
    	        .clientId("a727c413-a062-4c1e-902d-dc22f0a48623")
    	        .clientSecret("35e99ad2-9c11-480f-99ee-da01ec2fc61f")
    	        .tenantId("20763003-3e19-4c2e-ad43-1a4309984346")
    	        .build();

    	    // Azure SDK client builders accept the credential as a parameter
    	    SecretClient client = new SecretClientBuilder()
    	        .vaultUrl("https://props.vault.azure.net/")
    	        .credential(clientSecretCredential)
    	        .buildClient();
    	client.getSecret("hws-user-name");
    	
        return "This is Home page";
    }

    @GetMapping(value="/hello", produces = MediaType.TEXT_PLAIN_VALUE)
    public String sayHello() {
    	ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
    	        .clientId("a727c413-a062-4c1e-902d-dc22f0a48623")
    	        .clientSecret("35e99ad2-9c11-480f-99ee-da01ec2fc61f")
    	        .tenantId("20763003-3e19-4c2e-ad43-1a4309984346")
    	        .build();
    	
    	CertificateClient client = new CertificateClientBuilder()
    	        .vaultUrl("https://props.vault.azure.net/")
    	        .credential(clientSecretCredential)
    	        .buildClient();
    	
    	KeyVaultCertificateWithPolicy certificate = client.getCertificate("certificateName");
    	System.out.printf("Recevied certificate with name %s and version %s and secret id",
    		    certificate.getProperties().getName(),
    		    certificate.getProperties().getVersion(), certificate.getSecretId());
        return "Hello there!";
    }
}