# simple-bank-transfer-api
Simple bank transfer api without using spring

## Setup guide

#### Minimum Requirements

 - Java 11
 - Maven 3.x

#### Install the application

1. Make sure you have [Java](https://www.oracle.com/technetwork/java/javase/downloads/jdk13-downloads-5672538.html) and [Maven](https://maven.apache.org) installed

2. Open the command line in the source code folder

3. Build project

  ```
  $ mvn package
  ```

Run the tests
  ```
  $ mvn test
  ```


Run the project

  ```
  java -jar bank-transfer-1.0-SNAPSHOT.jar
  ```

## API

### Create an account with predefined balance
**POST** /accounts 

Example Request

````json
{
    {
        "number": 1,
        "balance": 500
    }
}
````
Example Response

````
{
    "response": {
        "status": 200,
        "message": "SUCCESS"
    }
}
````
---

**GET** /accounts -  get all the accounts that has been created

Example Request
```
localhost:5555/accounts
```

Example Response

````
{
    "body": [
        {
            "accountNumber": 1,
            "balance": 500
        },
        {
            "accountNumber": 2,
            "balance": 354.89
        }
    ],
    "response": {
        "status": 200,
        "message": "SUCCESS"
    }
}
````
---
**GET** /accounts/:number - get the account of the given account number 

````
localhost:5555/accounts/1
````
Example Response

````
{
    "body": {
        "accountNumber": 1,
        "balance": 500
    },
    "response": {
        "status": 200,
        "message": "SUCCESS"
    }
}
````
---

**POST** /accounts/transfer - creating a transfer between given accounts


````
{
	"fromAccountNumber":2,
	"toAccountNumber": 1,
	"amount" :16.48
}
````
Example Response

````
{
    "response": {
        "status": 200,
        "message": "SUCCESS"
    }
}
````
---
**DELETE** /accounts - delete all of the accounts

Example Request
```
localhost:5555/accounts
```


Example Response

````
{
    "response": {
        "status": 200,
        "message": "SUCCESS"
    }
}
````
---
