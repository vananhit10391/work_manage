# work_manage module
## API: get all work
* `curl -H "Content-Type: application/json" -XGET "localhost:8082/work/?size=10&page=0&sort=endingDate,asc&sort=startingDate,desc"`
## API: get work by name
* `curl -H "Content-Type: application/json" -XGET localhost:8082/work/{name}`
## API: add new work
* `curl -d '{"workName": "Work_01", "startingDate": "2020-01-12","endingDate":"2020-03-11","status": "Doing"}' -H "Content-Type: application/json" -XPOST localhost:8082/work/`
## API: update work
* `curl -d '{"workName": "Work_01", "startingDate": "2020-01-13","endingDate":"2020-03-14","status": "Planning"}' -H "Content-Type: application/json" -XPUT localhost:8082/work/`
## API: delete work by name
* `curl -H "Content-Type: application/json" -XDELETE "localhost:8082/work/{name}"`
* fffff
