# WSExample with JWT Security

- mvn jetty:run

# Buat Token
simpan parameter di header request

```
curl -H 'Content-Type: application/json' \
-H 'username: admin' \
-H 'password: 12341234' \
-v -X GET http://localhost:9090/Service/hello/authenticate

```

# Akses Resource (API)

```
curl -H 'Content-Type: application/json' \
-H 'token: -tokennya disini- \
-v -X GET http://localhost:9090/Service/hello/resource

```
