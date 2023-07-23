# transactional-message-sample
`Debezium` 을 이용한 transactional messaging 샘플 프로젝트 입니다.

## 프로젝트 구조
<img src="https://github.com/bidulgi69/transactional-message-sample/assets/17774927/f224838e-75f0-4240-b99d-debbed86e93c" />


## Run

#### intel 사용 시 default compose 변경
```
export COMPOSE_FILE=docker-compose-intel.yml
```

#### build
```
make generate
```
#### run
```
make background && make run
```

#### cleanup
```
make clean
```
