start:
	docker-compose up

build:
	docker-compose build

test:
	docker-compose run --rm stun ./mvnw mvn:test 
	docker-compose run --rm client npm test
	docker-compose run --rm signaling npm test
