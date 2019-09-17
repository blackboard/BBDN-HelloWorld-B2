# BBDN-B2REST-CourseTOCTemplate
This project provides an example of how to expose a custom REST endpoint from a Blackboard Building Block to supplement the Blackboard Learn REST APIs. The REST API in this Building Block allows a third-party application to interact with the Course TOC in a Blackboard Learn Original Course. It only allows you to create an external link tool.

This project does not tie into Learn's REST Authentication mechanism, so you must implement your own API authentication and if your application also uses Learn APIs, you must authenticate to both implementations separately.

This is not meant to be a full Java, AuthN/AuthZ, or REST API tutorial. It is simply meant to provide a working example of running a custom REST API in a Blackboard Building Block to supplement the Blackboard Learn REST APIs to facilitate migration to REST.

The OAuth token mechanism only works with one token at a time. Requesting a new token will invalidate the previous token and generate a new one. There is no client-specificity. This is not meant for production, and using this code as-is in production is done at your own risk.

## Project Functionality
The Building Block exposes 6 endpoints:
* [Request a token](###request-a-token)
* [Revoke a token](###request-a-token)
* [Create a Course TOC item and append to the bottom of the left-hand navigation in a course](###create-a-course-toc-item-and-append-to-the-bottom-of-the-left-hand-navigation-in-a-course)
* [Read a Course TOC item by its pk1 ID (_#_#)](###read-a-course-toc-item-by-its-pk1-ID-(_#_#))
* [Update a Course TOC item by its pk1 ID (_#_#)](###update-a-course-toc-item-by-its-pk1-ID-(_#_#))
* [Delete a Course TOC item by its pk1 ID (_#_#)](###delete-a-course-toc-item-by-its-pk1-ID-(_#_#))

### Request a token
`POST \<blackboard domain\>/webapps/bbdn-CourseTocRest-\<db schema identifier\>/courseToc/token`

This endpoint accepts application/form_urlencoded. It expects an Authorization header set equal to "Basic xxxxxxx" where xxxxxxx equals the Base64 encoded version of "\<key\>:\<secret\>" as set in the configuration page of the Building Block. 

A successful token response will return an application/json body containing the following:

```
{
  "expiry":"3200",
  "token":"c607ee3b-75ec-464d-8043-7bba0d4774da",
  "type":"bearer"
}
```

Possible return values:
* 200 - Successful
* 403 - Unauthorized

### Revoke a token
`POST \<blackboard domain\>/webapps/bbdn-CourseTocRest-\<db schema identifier\>/courseToc/token/revoke`

This endpoint accepts application/form_urlencoded. It requires an Authorization header set equal to "Bearer xxxxxxx" where xxxxxxx is the valid token you wish to revoke.

Possible return values:
* 200 - Successful
* 503 - Failure

### Create a Course TOC item and append to the bottom of the left-hand navigation in a course
`POST \<blackboard domain\>/webapps/bbdn-CourseTocRest-\<db schema identifier\>/courseToc`

This endpoint accepts application/json. It requires an Authorization header set equal to "Bearer xxxxxxx" where xxxxxxx is the valid token issued by [Get a Token](###get-a-token). The JSON body expects three pieces of information: Course ID (pk1-style: \_#\_#), a label to be displayed in the UI, and a URL to launch when clicked.

```
{
  "courseId": "_3_1",
  "label": "Dev Portal",
  "url": "https://developer.blackboard.com"
}
```

A successful completion will return the same information, along with the CourseTOC pk1 value:

```
{
  "courseId": "_3_1",
  "label": "Dev Portal",
  "url": "https://developer.blackboard.com",
  "id": "_37_1"
}
```

Possible return values:
* 201 - Successful
* 403 - Failure


### Read a Course TOC item by its pk1 ID (_#_#)
`GET \<blackboard domain\>/webapps/bbdn-CourseTocRest-\<db schema identifier\>/courseToc/\<pk1 \_#\_#\>`

This endpoint accepts application/json. It requires an Authorization header set equal to "Bearer xxxxxxx" where xxxxxxx is the valid token yissued by [Get a Token](###get-a-token).

A successful completion will return the CourseTOC information:

```
{
  "courseId": "_3_1",
  "label": "Dev Portal",
  "url": "https://developer.blackboard.com",
  "id": "_37_1"
}
```

Possible return values:
* 200 - Successful
* 503 - Failure

### Update a Course TOC item by its pk1 ID (_#_#)
`PUT \<blackboard domain\>/webapps/bbdn-CourseTocRest-\<db schema identifier\>/courseToc/\<pk1 \_#\_#\>`

This endpoint accepts application/json. It requires an Authorization header set equal to "Bearer xxxxxxx" where xxxxxxx is the valid token issued by [Get a Token](###get-a-token). The JSON body expects three pieces of information: Course ID (pk1-style: \_#\_#), a label to be displayed in the UI, and a URL to launch when clicked.

```
{
  "courseId": "_3_1",
  "label": "Dev Portal Updated",
  "url": "https://developer.blackboard.com"
}
```

A successful completion will return the updated CourseTOC information:

```
{
  "courseId": "_3_1",
  "label": "Dev Portal Updated",
  "url": "https://developer.blackboard.com",
  "id": "_37_1"
}
```

Possible return values:
* 200 - Successful
* 503 - Failure

### Delete a Course TOC item by its pk1 ID (_#_#)
`DELETE \<blackboard domain\>/webapps/bbdn-CourseTocRest-\<db schema identifier\>/courseToc/\<pk1 \_#\_#\>`

This endpoint accepts application/json. It requires an Authorization header set equal to "Bearer xxxxxxx" where xxxxxxx is the valid token issued by [Get a Token](###get-a-token).

Possible return values:
* 204 - Successful
* 503 - Failure

