
<p align="center">
<img src="https://www.edubrite.com/oltpublish/static/assets/eb-img/eb-new-logo.png" width="256">
</p>

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

Response Types
-----------

Response can be fetched in following types :

<table>
<tr><td><b>XML</b></td><td>default</td><td>connector.setResponseType( ResponseType.<b>XML</b> );</td></tr>
<tr><td><b>JSON</b></td><td></td><td>connector.setResponseType( ResponseType.<b>JSON</b> );</td></tr>
</table>

API Mapping
-----------

<table>
<tr><th>Service</th><th>Operations</th></tr>
<tr>
<td>
<b>UserApiService</b>
<br/><i>[connector.userSvc()]</i>
</td>
<td>
<ul>
<li>list :  <i>List the users based on given criteria from given group</i></li>
<li>create :  <i>Creates a new user</i></li>
<li>get :  <i>Get a specific user by user name</i></li>
<li>update :  <i>Updates user details</i></li>
<li>deactivate :  <i>Deactivates user from the site</i></li>
<li>activate :  <i>Activates deactivated user from the site</i></li>
<li>addToGroups :  <i>Adds a user to group(s) with given role</i></li>
<li>removeFromGroups : <i>Removes a user from group(s)</i></li>
<li>getSession :  <i>Gets all user sessions</i></li>
</ul>		
</td>
</tr>

<tr>
<td>
<b>GroupApiService</b>
<br/><i>[connector.groupSvc()]</i>
</td>
<td>
<ul>
<li>get :  <i>Fetches a group by given id</i></li>
<li>getSiteGroupList :  <i>Fetches all the groups of the site</i></li>
<li>getMyGroupList :  <i>Fetches user's groups</i></li>
<li>create :  <i>Creates a new group</i></li>
<li>removeByIds :  <i>Removes group(s) by given id(s)</i></li>
<li>removeByNames :  <i>Removes group(s) by name(s)</i></li>
<li>addRemoveGroupAndMembers :  <i>Adds/removes user into/from given group(s)</i></li>
</ul>		
</td>
</tr>

<tr>
<td>
<b>TestApiService</b>
<br/><i>[connector.testSvc()]</i>
</td>
<td>
<ul>
<li>getTest</li>
<li>getTestInstance</li>
<li>getTestCollections</li>
<li>getSubscribedExamsList</li>
<li>getNotSubscribedExamsList</li>
<li>subscribe</li>
<li>getTestInstanceList</li>
<li>getTestStats</li>
<li>getTestQuestionStats</li>
<li>getTestAttempts</li>
<li>getAllTestAttempts</li>
</ul>		
</td>
</tr>

<tr>
<td>
<b>CourseApiService</b>
<br/><i>[connector.courseSvc()]</i>
</td>
<td>
<ul>
<li>getCourseList</li>
<li>getCourseSessionList</li>
<li>getUsersCourseSessionList</li>
<li>getSubscribedExamsList</li>
<li>enrollCourseSession</li>
</ul>		
</td>
</tr>

<tr>
<td>
<b>ReportApiService</b>
<br/><i>[connector.reportSvc()]</i>
</td>
<td>
<ul>
<li>listProgramMembers</li>
<li>listCourseSessionMembers</li>
</ul>		
</td>
</tr>
</table>


-----
All source - copyright - EduBrite Systems Inc.
