# Secure REST API with JWT token

Spring (6.2) Security JWT: How to secure your Spring Boot REST APIs with JSON Web Tokens

## Generate PKCS8 private/public keys

> from $project/resource/certs folder

### Generating RSA private key (PKS1), 4096 bit long modulus (2 primes)

```shell
$ openssl genrsa -out keypair.pem 4096

```
### Extract public RSA key from keypair 

```shell
$ openssl rsa -in keypair.pem -pubout -out public.key
```

### Reformat private key to PKCS8 

```shell
$ openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.key
```