# BotAdmin

## Available Profiles
1. Local DEV (local) This profile allows you run the Bot locally in dev mode and deploy the bot locally and test it.
```
mvn clean install -P local
```

2. Local DEV-WAR (local) This profile allows you run the Bot locally in dev mode and deploy the bot on tomcat and test it.
```
mvn clean install -P local-war
```
Once built copy the WAR to "~/installs/apache-tomcat-9.0.21/webapps" and start tomcat (using ~/installs/apache-tomcat-9.0.21/bin/startup.sh) and test using URL:
```
http://localhost:8091/botadmin/actuator/info
```

I have changed the server.xml for tomcat 9 and added the following in HOST section
```
<Context path="/botadmin" docBase="seerlogics-bot-admin-1.0.0-SNAPSHOT"/>
```
I have also added additional parameters like below to HOST element
```
autoDeploy="false" deployOnStartup="false"
```
to make the app work with a new context path.

3. AWS/Production (aws-ec2) This profile allows you to deploy the Bot on AWS EC2 instance and test it.
```
mvn clean install -P aws-ec2
```
Once the build succeeds you should see 'spring.profiles.active=aws-ec2' set in the application.properties file.

4. AWS/Production WAR (aws-ec2-war) This profile allows you to deploy the Bot WAR file on AWS EC2 instance and test it.
```
mvn clean install -P aws-ec2-war
```

## How to run application locally
1. Main class: com.seerlogics.botadmin.BotAdminApplication
2. Java args: -DdepProfile=dev -DconfigLoc=/opt/installs/tomcat/8.5.9 -Dspring.profiles.active=local

## How to run BotAdmin Application locally
```
java -jar -Dspring.profiles.active=local -DdepProfile=dev -DconfigLoc=/opt/installs/tomcat/8.5.9 seerlogics-bot-admin-1.0.0-SNAPSHOT.jar
```

## Cloud Deployments
1. When testing deploying on cloud run the ''BotAdminApplication-ec2:8091'' configuration.
2. You should launch the bot from the Bot Admin application and instance will be launched.
3. You can connect to instance using the below command:
```
ssh -i ~/svn/bhushan/theory/AWS/SeerLogics/keyPairs/bizBotAdminLogin.pem ec2-user@ec2-3-14-73-84.us-east-2.compute.amazonaws.com
```

## Possible Errors
1. JPA Error
```
Error Desc: Caused by: org.hibernate.PersistentObjectException: detached entity passed to persist: com.paulsanwald.Account
                at org.hibernate.event.internal.DefaultPersistEventListener.onPersist(DefaultPersistEventListener.java:141)
```
Soln: This is because you are passing an already saved/persisted entity as a possible FK into another object and then trying to "PERSIST" it
as if its a new entity. To resolve this issue remove the "CascadeType.PERSIST" from the cascade type list


2. AWS login issue:
```
The authenticity of host 'ec2-18-222-187-228.us-east-2.compute.amazonaws.com (18.222.187.228)' can't be established.
ECDSA key fingerprint is SHA256:qWtnrKiQwMzqimgVAHsiI6T0Rgx/YSsOtcmF7otQ/gY.
Are you sure you want to continue connecting (yes/no)? yes
Warning: Permanently added 'ec2-18-222-187-228.us-east-2.compute.amazonaws.com,18.222.187.228' (ECDSA) to the list of known hosts.
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@         WARNING: UNPROTECTED PRIVATE KEY FILE!          @
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
Permissions 0644 for '/home/bkane/svn/bhushan/theory/AWS/SeerLogics/keyPairs/bizBotAdminLogin.pem' are too open.
It is required that your private key files are NOT accessible by others.
This private key will be ignored.
Load key "/home/bkane/svn/bhushan/theory/AWS/SeerLogics/keyPairs/bizBotAdminLogin.pem": bad permissions
Permission denied (publickey).
```
To fix this run the below command:
```
chmod 0400 ~/svn/bhushan/theory/AWS/SeerLogics/keyPairs/*.pem
```

## Unit Testing and SonarQube
1. In order to generate code coverage report, JaCoCo is being used.
2. JaCoCo has been configured in the "BOM" xml file.
3. References:
    a. https://www.mkyong.com/maven/maven-jacoco-code-coverage-example/
    b. https://www.baeldung.com/jacoco
    c. https://thepracticaldeveloper.com/2016/02/06/test-coverage-analysis-for-your-spring-boot-app/

## Example Code for AWS Management
1. http://www.doublecloud.org/2016/03/amazon-web-service-java-sdk-tutorial-create-new-virtual-machine/
2. AWS Management code: https://github.com/neowu/cmn-project/blob/master/cmn/src/main/java/core/aws/task/ec2/CreateInstanceTask.java
3. Create Instance Task: https://www.programcreek.com/java-api-examples/?code=neowu/cmn-project/cmn-project-master/cmn/src/main/java/core/aws/task/ec2/CreateInstanceTask.java#
4. Create ELB: https://www.programcreek.com/java-api-examples/?code=neowu/cmn-project/cmn-project-master/cmn/src/main/java/core/aws/task/ec2/CreateInstanceTask.java#

## Admin/Actuator URLs
1. Health URL: http://localhost:8091/actuator/health
2. Info URL: http://localhost:8091/actuator/info

## Running BOT H2 DB in server mode
```
java -cp ~/installs/H2/h2-1.4.199.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -baseDir /Users/bkane/svn/code/java/SeerlogicsBotAdmin/h2
```
The above will start the server print out the below in console:
```
TCP server running at tcp://23.202.231.166:9092 (others can connect)

Web Console server running at http://192.168.0.113:8082 (others can connect)
```

##### You can connect to the server both the Web app, IDE(DB client) and H2 console at the same time using URL:
BotAdminDB URL:
```
jdbc:h2:tcp://localhost/~/svn/code/java/SeerlogicsBotAdmin/h2/botDB
```
ChatBot DB URL:
```
jdbc:h2:tcp://localhost/~/svn/code/java/SeerlogicsBotAdmin/h2/chatBotServerDB
```

## Deploy Bot Admin and Chat Bot on AWS

1. Run the below script to deploy BotAdmin Sever on AWS:
```
/Users/bkane/svn/code/java/SeerlogicsBotAdmin/docs/AWS/scripts/launchBotAdmin.sh
```

The above script will copy the Bot Admin WAR and run the instances. When the instances 
run the below script will get invoked on start up. This will install JDK, Tomcat etc on the 
newly created instances.
```
/Users/bkane/svn/code/java/SeerlogicsBotAdmin/docs/AWS/scripts/onLaunchScript.sh
```

 
2. 