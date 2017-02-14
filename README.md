
<img src="https://www.edubrite.com/oltpublish/static/assets/eb-img/eb-new-logo.png" width="256">
javaapi
=======

Example of how to use Java client to invoke EduBrite LMS APIs

This is a full fledged java project to demonstrate how to create 
custom Java front end application to connect with EduBrite Learning Management System. 


Source code provided to help existing customers, developers create custom front end, plugins or integrate 
EduBrite with other enterprise applications.

To test these APIs you would need a business edition account at edubrite.com. To start your free 30 days trial go to www.edubrite.com

-----
Getting Started
===============

Generate API keys
-----------------

 1. `Login` to your micro-site and open  `Site Admin > Site Details` from the navigation.

     <img src="https://github.com/edubrite/javaapi/blob/features/api-improvements/Readme/1.jpg">

 2. Visit `Integration` tab and click on `Generate Encryption Keys` (if not already generated) 

    <img src="https://github.com/edubrite/javaapi/blob/features/api-improvements/Readme/2.jpg">

 3. Then click on `Generate Api Key`. This will generate username/password.

    <img src="https://github.com/edubrite/javaapi/blob/features/api-improvements/Readme/3.1.jpg">

    <img src="https://github.com/edubrite/javaapi/blob/features/api-improvements/Readme/3.2.jpg">

 4. Disable `API Encryption` and click `Save`

    <img src="https://github.com/edubrite/javaapi/blob/features/api-improvements/Readme/4.1.jpg">


-----
Use API keys
------------

1. Open `edubrite.properties` from the API project.
2.  Copy paste generated API keys against the keys in the file
```
    url=<Connect URL>
    userName=<Username>
    password=<Password>
```

-----

Calling API
-----------

 1. Get the configurations and set values for the session :
 
    ```
    PluginConfig config = PluginConfigManager.getConfig();
    config.setEnableEncryption(false);
    Pair<OltPublicKey, OltPrivateKey> keys = OltSymmetricKeyHandler.generateBaseKeyPair();
    Key secretKey = OltSymmetricKeyHandler.generateSecretKey();
    config.setKeys(keys);
    config.setSecretKey64(OltSymmetricKeyHandler.secretKeyToBase64String(secretKey));
    ```

 2. Initialize `EduBriteConnector` 
 
    ```
    EduBriteConnectorFactory.getInstance().getConnector().disconnect();
    EduBriteConnector connector = EduBriteConnectorFactory.getInstance().getConnector();
    connector.connect();
    ```
 3. Set application user for permissions. This user’s permission will be used for request.  Typically siteadmin username can be used.
 
    ```
    PluginConfigManager.getInstance().setApplicationUser(new User("<userName>"));
    ```
 4. Call the required Api using the connector.
 
    ```
    String response = connector.userSvc().get("<username>");
    ```

-----
All source - copyright - EduBrite Systems Inc.
