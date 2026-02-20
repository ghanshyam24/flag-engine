# Spring boot Flag Engin

Spring boot Flag Engin

## Curl's

curl -X POST http://localhost:8080/features -H "Content-Type: application/json" -d "{\"name\":\"dark_mode\",\"defaultEnabled\":false,\"description\":\"Dark mode UI\"}"

curl -X PUT "http://localhost:8080/features/dark_mode/default?enabled=true"

curl -X PUT "http://localhost:8080/features/dark_mode/users/user1?enabled=true"

curl -X PUT "http://localhost:8080/featgures/dark_mode/users/user1?enabled=true"
curl -X PUT "http://localhost:8080/features/dark_mode/groups/beta?enabled=true"
curl -X PUT "http://localhost:8080/features/dark_mode/regions/us-east?enabled=true"
curl -X DELETE "http://localhost:8080/features/dark_mode/users/user1"
curl -X DELETE "http://localhost:8080/features/dark_mode/groups/beta"
curl -X DELETE "http://localhost:8080/features/dark_mode/regions/us-east"
curl -X GET "http://localhost:8080/features/dark_mode/evaluate?userId=user1&groupName=beta&region=us-east"
