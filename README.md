# Registers
Simple home budgeting application. Allowing manipulating budget registers, recharge them and transfer money between them.

## Requirements
Java version 8 or higher.

## How to run
You can run the application in following ways:
- Running this [jar](https://github.com/robertostaszewski/registers/releases) - Download jar from release site
  and run this command in a folder containing file
  
  `java -jar registers-0.0.1-SNAPSHOT.jar`
- From the command line with Gradle - clone this repo and run this command being inside repository
  
  `./gradlew bootRun`
- You can also build a single executable JAR file on your own - clone this repo and run this command being inside 
  repository
  
  `./gradlew build` then `java -jar build/libs/registers-0.0.1-SNAPSHOT.jar`

## How it works
Application start at port 8080. On a startup creates h2 database file (if it does not exist) in home directory: 
`~/db/registersdb` and initialize it with demo data of four registers:
- “Wallet” register with a balance of 1000
- “Savings” register with a balance of 5000
- “Insurance policy” register with a balance of 0
- “Food expenses” register with a balance of 0

Application exposes three endpoints:
- `POST /rest/registers/operation/recharge` - Recharge an existing register with given amount.<br> 
  Accepts body in following format:
      
      {
        "registerId": 2,
        "amount": 300.0
      }
  where `registerId` is the id of register to recharge, and `amount` is the amount to transfer. 
  If amount is negative then its withdrawal operation.
  

- `POST /rest/registers/operation/transfer` - Transfer given amount between two existing registers.<br>
  Accepts body in following format:
  
      {
        "sourceId": 1,
        "destinationId": 2,
        "amount": 300.0
      }
  where `sourceId` and `destinationId` are the ids of registers to transfer from and to, and `amount` is the amount to 
  transfer. If amount is negative then is transferred from destination to source. 
  

- `GET /rest/registers/balance` - Get current balance of all registers <br>
  Returns summary with list of all registers, and their balance in following format:

      {
        "registers": [
          {
            "id": 1,
            "name": "Wallet",
            "balance": 1600.0
          },
          {
            "id": 2,
            "name": "Savings",
            "balance": 3800.0
          },
          {
            "id": 3,
            "name": "Insurance policy",
            "balance": 0.0
          },
          {
            "id": 4,
            "name": "Food expenses",
            "balance": 300.0
          }
        ]
      }

## Possible next step

* Application could store just transactions and calculate balance of each register on the fly - this would allow 
  providing transactions history, easier archiving and eventual transaction edition, by te cost of doing recalculation 
  every time for each register (which could be decreased by storing intermediate results).   