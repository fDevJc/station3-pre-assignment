## 실행방법
### 0. 환경
```
java     : openjdk version "11.0.18"
boot     : version '2.7.9'
dbms     : H2 인메모리 DB (기본 ddl설정: create)
api-docs : http://localhost:8080/docs/index.html (서버실행 후 확인가능)
```
### 1. git clone
```shell
git clone https://github.com/fDevJc/station3-pre-assignment.git
```

### 2. build
```shell
./gradlew build
```

### 3. run
```shell
java -jar ./build/libs/station3-assingment-0.0.1.jar
```
- 기본포트: 8080

### (OR) 2,3 startup.sh
```shell
chmod 775 ./startup.sh
./startup.sh
```

## API 실행 방법
### Rest Doc API 참고
- URL : http://localhost:8080/docs/index.html
- 기타사항
  - 로그인 시 응답하는 tokenType과 tokenValue를 합쳐 Authorization 헤더에 넣어주세요.
    - ex) Authorization: Bearer testToken
  - 모든 방 목록 조회시 쿼리파라미터는 urlencoding된 값을 넣어주세요
    - 인코딩 전
      ```
        localhost:8080/api/v1/rooms?q="roomType":["ONE_ROOM"],"dealType":["LONG_TERM_RENT"],"depositRange": [0,10000],"rentRange": [0,100]
      ```
    - 인코딩 후
      ```shell
        localhost:8080/api/v1/rooms?q=%22roomType%22%3A%5B%22ONE_ROOM%22%5D%2C%22dealType%22%3A%5B%22LONG_TERM_RENT%22%5D%2C%22depositRange%22%3A%20%5B0%2C10000%5D%2C%22rentRange%22%3A%20%5B0%2C100%5D
      ```

## 과제요구사항

- [과제 요구사항 정리](./docs/과제요구사항.md)
- [용어 정리](./docs/용어정리.md)
- [API 설계](./docs/api)