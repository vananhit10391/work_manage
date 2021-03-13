# work_manage module
## Testing
* `curl -H "Content-Type: application/json" -XGET localhost:8082/work/?size=10&page=0&sort=endingDate,asc`
* `curl -H "Content-Type: application/json" -XGET localhost:8082/work/{name}`
* `curl -d '{"workName": "Work_01", "startingDate": "2020-01-12","endingDate":"2020-03-11","status": "Doing"}' -H "Content-Type: application/json" -XPOST localhost:8082/work/`
* `curl -d '{"workName": "Work_01", "startingDate": "2020-01-13","endingDate":"2020-03-14","status": "Planning"}' -H "Content-Type: application/json" -XPUT localhost:8082/work/`
* `curl -H "Content-Type: application/json" -XDELETE "localhost:8082/work/{name}"`