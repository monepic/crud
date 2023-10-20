# CRUD

## Simple CRUD app with auto-generated schema

|Reference|
|---|
| [https://json-schema.org/](https://json-schema.org/) |
| [victools/jsonschema-generator](https://github.com/victools/jsonschema-generator)|

### Run the project:
```bash
git clone https://github.com/monepic/crud.git
cd crud;
mvn
```

There's one Entity called [MappingItem](./src/main/java/com/monepic/crud/entity/MappingItem.java).  
Its properties demonstrate some of the various use-cases of the annotations affecting how the schema is defined.

---

The basic CRUD operations are available with the default Accept header / `Accept: application/json`

|URL|Method|Returns|
|---|---|---|
|/mappingItems|GET|List of item|
|/mappingItems|POST|Newly created item|
|/mappingItems/{id}|GET|Item identified by `id`|
|/mappingItems/{id}|PUT|Newly updated item|
|/mappingItem/{id}|DELETE|Nothing|

---


Most importantly, the schema for the `MappingItem` object can be obtained by using the content type header: `Content-Type: application/schema+json`

`curl localhost:8080/mappingItems -H "Accept: application/schema+json"`


```json
{
  "$schema": "https://json-schema.org/draft/2020-12/schema",
  "type": "object",
  "properties": {
    "id": {
      "type": "integer"
    },
    "from": {
      "type": "string",
      "arbitraryMetadataField": "this data is set on the 'from' property"
    },
    "to": {
      "type": "string",
      "description": "Description of the 'to' field"
    },
    "email": {
      "type": "string",
      "description": "This field includes a URL which is automatically mapped to a controller and can be used for client-side validation",
      "format": "email",
      "validationUrl": "http://localhost:8080/validation/email?email"
    },
    "myGetter": {
      "type": "string",
      "minLength": 2,
      "maxLength": 30
    },
    "readOnlyThing": {
      "type": "number",
      "readOnly": true
    }
  },
  "description": "This is the 'MappingItem' description",
  "someClassMetadataField": "this data is set at the class level"
}
```

