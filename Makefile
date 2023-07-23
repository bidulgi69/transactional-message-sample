generate:
	./gradlew clean && ./gradlew bootjar && docker-compose build

background:
	docker-compose up -d mysql zookeeper kafka && sleep 30

run: 
	docker-compose up -d order-service tailing-service subscribe-service

clean:
	docker-compose down --remove-orphans && docker volume rm $$(docker volume ls -qf dangling=true)
