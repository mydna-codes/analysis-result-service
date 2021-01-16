# analysis-result

### Library
```xml
<dependency>
    <groupId>codes.mydna</groupId>
    <artifactId>analysis-result-lib-v1</artifactId>
    <version>${analysis-result.version}</version>
</dependency>
```

### Docker

*Note: This service requires DB.*

Pull docker image:
```bash
docker pull mydnacodes/analysis-result
```

Run docker image:
```bash
docker run -d -p <PORT>:8080 
    -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://<DB_HOST>:<DB_PORT>/analysis-result
    -e KUMULUZEE_DATASOURCES0_USERNAME=<DB_USERNAME> 
    -e KUMULUZEE_DATASOURCES0_PASSWORD=<DB_PASSWORD> 
    -e KEYCLOAK_REALM=<KEYCLOAK_REALM_NAME>
    -e KEYCLOAK_CLIENTID=<KEYCLOAK_CLIENT_ID>
    -e KEYCLOAK_AUTHSERVERURL=<KEYCLOAK_SERVER_URL>
    -e KEYCLOAK_AUTH_CLIENTSECRET=<KEYCLOAK_CLIENT_SECRET>
    --name analysis-result-service
    mydnacodes/analysis-result
```