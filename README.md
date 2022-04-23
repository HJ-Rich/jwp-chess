# Spring Chess

# How to Start

### 1. Run Docker

```
docker run -d -p 80:80 docker/getting-started 
```

### 2. Run Docker Container

```
cd docker
docker-compose -p chess up -d
```

### 3. Run SpringBootApplication

```
cd ..
./gradlew bootRun
```

<p align="center">
    <img src="./woowacourse.png" alt="우아한테크코스" width="250px">
</p>

# 체스 - 웹,DB

---

![Generic badge](https://img.shields.io/badge/Level2-chess-green.svg)
![Generic badge](https://img.shields.io/badge/test-98_passed-blue.svg)
![Generic badge](https://img.shields.io/badge/version-2.0.0-brightgreen.svg)

> 우아한테크코스 웹 백엔드 4기, 체스 - 웹,DB 저장소입니다.

[![Video Label](http://img.youtube.com/vi/xGw8WqAIQDY/0.jpg)](https://youtu.be/xGw8WqAIQDY)

## 기능 구현 목록

---

# 4단계 웹

- [x] 페이지 접속 시, 서버의 Board 정보대로 화면에 기물을 배치한다.
- [x] 기물 클릭, 이동지점 클릭을 통해 기물을 이동시킨다.
    - [x] 이동이 불가한 곳일 경우 이동 실패 알림을 표시한다.
    - [x] 이동에 성공할 경우 화면의 기물 위치를 이동시키고 필요에 따라 잡힌 기물을 제게한다.
- [x] 왕이 잡힐 경우, 게임을 종료처리한다.
- [x] 기물이 이동될 때마다 점수를 업데이트하여 표기한다.

# 5단계 DB

- 필수 요구사항

- [x] 웹 서버를 재시작하더라도 이전에 하던 체스 게임을 다시 시작할 수 있어야 한다.
- [x] DB를 적용할 때 도메인 객체의 변경을 최소화해야한다.

- 선택 요구사항

- [x] 체스 게임방을 만들고 체스 게임방에 입장할 수 있는 기능을 추가한다.
- [ ] 사용자별로 체스 게임 기록을 관리할 수 있다.

# 콘솔

## 🚀 1단계 - 체스판 초기화 요구사항

- [x] 체스판을 초기화한다.
    - [x] 체스판에서 말의 위치 값은 가로 위치는 왼쪽부터 a ~ h이고, 세로는 아래부터 위로 1 ~ 8로 구현한다.
- [x] 각 진영은 검은색(대문자) 와 흰색(소문자)로 구분한다.
- [x] start 명령어 입력시, 초기화 된 체스판을 콘솔에 출력한다

## 🚀 2단계 - 체스판 말 이동 요구사항

- [x] `move source위치 target위치`를 실행해 이동한다.
- [x] 콘솔 말의 이동 규칙에 적합하게 이동한다.
    - [x] [예외처리] 체스판을 벗어나는 위치를 입력하면 에러를 발생시킨다.
    - [x] [예외처리] 체스말에 적합하지 않은 이동을 실행하면 이동에 실패한다.

## 🚀 3단계 - 승패 및 점수

- [x] King이 잡혔을 때 게임을 종료한다.
- [x] 체스 게임은 현재 남아 있는 말에 대한 점수를 구할 수 있어야 한다.
- [x] `status` 명령을 입력하면 각 진영의 점수를 출력하고 어느 진영이 이겼는지 결과를 볼 수 있어야 한다.

<br><br>
