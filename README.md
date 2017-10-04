## samples-23102

#### a collection of sample code for dealing with a customized group

customgroup-schema-contrib.xml adds a 'customField' to the group schema

#### the 'group' endpoint
Using the group endpoint 'customField' is not visible -- this is because it uses the NuxeoGroup class, which is not aware of entities in the group schema other than the group name and label.
``` curl -u Administrator:Administrator http://localhost:8080/nuxeo/api/v1/group/members
```


#### Enricher for the 'group' enpoint
An enricher can insert extra info in a REST response. this example enricher returns the parentGroups list in the response. 
https://doc.nuxeo.com/nxdoc/content-enrichers/ 
https://doc.nuxeo.com/nxdoc/bonus-contribute-your-own-enricher/
``` curl -u Administrator:Administrator http://localhost:8080/nuxeo/api/v1/group/members -H 'X-NXenrichers-group:custom_group'
```

#### the 'directory' endpoint
Since groups are stored in a standard Nuxeo directory. The 'directory' endpoint can be more versatile, though read-only.
https://doc.nuxeo.com/nxdoc/data-lists-and-directories/ 

Get list of all groups
``` curl -u Administrator:Administrator http://localhost:8080/nuxeo/api/v1/directory/groupDirectory
```
Get specific group
``` curl -u Administrator:Administrator http://localhost:8080/nuxeo/api/v1/directory/groupDirectory/members
```

#### Directory operations
Nuxeo has Operations for working with directories 
http://explorer.nuxeo.com/nuxeo/site/distribution/current/viewOperation/Directory.Entries 
http://explorer.nuxeo.com/nuxeo/site/distribution/current/viewOperation/Directory.CreateEntries 
http://explorer.nuxeo.com/nuxeo/site/distribution/current/viewOperation/Directory.ReadEntries 
http://explorer.nuxeo.com/nuxeo/site/distribution/current/viewOperation/Directory.UpdateEntries 
http://explorer.nuxeo.com/nuxeo/site/distribution/current/viewOperation/Directory.DeleteEntries 

List all entries in a directory
```curl -u Administrator:Administrator http://localhost:8080/nuxeo/site/automation/Directory.Entries -d '{"params":{"directoryName":"groupDirectory"}}' -H 'content-type: application/json' 
```

List specific entry
```curl -u Administrator:Administrator http://localhost:8080/nuxeo/site/automation/Directory.ReadEntries -d '{"params":{"directoryName":"groupDirectory","entries":"[\"members\"]" }}' -H 'content-type: application/json'
```

List multiple entries
```curl -u Administrator:Administrator http://localhost:8080/nuxeo/site/automation/Directory.ReadEntries -d '{"params":{"directoryName":"groupDirectory","entries":"[\"members\",\"administrators\"]" }}' -H 'content-type: application/json'
```

Create a new entry in a directory
```curl -u Administrator:Administrator http://localhost:8080/nuxeo/site/automation/Directory.CreateEntries -d '{"params":{"directoryName":"groupDirectory","entries":"[{\"groupname\":\"newgroup\",\"members\":[],\"customField\":\"somestring\"}]"}}' -H 'content-type: application/json'
```

Update entry in a directory
```curl -u Administrator:Administrator http://localhost:8080/nuxeo/site/automation/Directory.UpdateEntries -d '{"params":{"directoryName":"groupDirectory","entries":"[{\"groupname\":\"members\",\"customField\":\"someotherstring\"}]"}}' -H 'content-type: application/json'
```

#### Custom operation
A custom operation can be used to do things that aren't easily done with what's provided in the REST API. This operation's a bit easier to use than the default operations because the parameters are simpler. 
https://doc.nuxeo.com/nxdoc/contributing-an-operation/
``` curl -u Administrator:Administrator http://localhost:8080/nuxeo/site/automation/Directory.UpdateGroupField -d '{"params":{"groupName":"members","field":"customField","value":"somerandomstring"}}' -H 'content-type: application/json'
```

#### More info
This project was bootstrapped with Nuxeo CLI https://doc.nuxeo.com/nxdoc/nuxeo-cli/ using the below command
``` nuxeo bootstrap operation enricher
```

