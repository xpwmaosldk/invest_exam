# 주문/조회 그리고 트렌젝션..!! 

### 모집 기간내 투자상품 조회
***Request***
```
curl --location --request GET 'localhost:8080/investments' \
--header 'X-USER-ID: 1'
```
***Response***
```
[
    {
        "id": 1,
        "user": {
            "id": 1
        },
        "product": {
            "id": 1,
            "title": null,
            "totalInvestingAmount": 3,
            "currentInvestingAmount": 3,
            "investedPeopleCnt": 1,
            "state": "FINISHED",
            "startedAt": "2021-01-01 12:00:00",
            "finishedAt": "2021-11-01 12:00:00"
        },
        "investingAmount": 3,
        "started_at": "2021-04-17 22:28:35"
    },
    {
        "id": 2,
        "user": {
            "id": 1
        },
        "product": {
            "id": 2,
            "title": null,
            "totalInvestingAmount": 3,
            "currentInvestingAmount": 3,
            "investedPeopleCnt": 1,
            "state": "FINISHED",
            "startedAt": "2021-01-01 12:00:00",
            "finishedAt": "2021-11-01 12:00:00"
        },
        "investingAmount": 3,
        "started_at": "2021-04-17 22:59:59"
    }
]
```


### 투자하기 
***Request***
```
curl --location --request POST 'localhost:8080/investments' \
--header 'X-USER-ID: 1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "productId": 2,
    "amount": 4
}'
```
***Response***
```
200, invested
or
999, sold-out
```


### 내 투자상품 조회 
***Request***
```
curl --location --request GET 'localhost:8080/investments' \
--header 'X-USER-ID: 1'
```
***Response***
```
[
    {
        "id": 1,
        "user": {
            "id": 1
        },
        "product": {
            "id": 1,
            "title": null,
            "totalInvestingAmount": 3,
            "currentInvestingAmount": 3,
            "investedPeopleCnt": 1,
            "state": "FINISHED",
            "startedAt": "2021-01-01 12:00:00",
            "finishedAt": "2021-11-01 12:00:00"
        },
        "investingAmount": 3,
        "started_at": "2021-04-17 22:28:35"
    },
    {
        "id": 2,
        "user": {
            "id": 1
        },
        "product": {
            "id": 2,
            "title": null,
            "totalInvestingAmount": 3,
            "currentInvestingAmount": 3,
            "investedPeopleCnt": 1,
            "state": "FINISHED",
            "startedAt": "2021-01-01 12:00:00",
            "finishedAt": "2021-11-01 12:00:00"
        },
        "investingAmount": 3,
        "started_at": "2021-04-17 22:59:59"
    }
]
```


## 참고사항
- 금액 자료형은 int보다는 넉넉하게 long으로 설정(BigInteger는 지양)
- sold-out 직전 주문은 주문금액에서 남은 모집금액까지만 사용(금액 반환, 재확인 생략)
- '내 투자상품 조회'는 전체 투자내역에서 내 id로 필터링(인증/인가 생략)
- 중복상품 투자 관련 프로세스 생략

## 확장 가능성 목록
- 조건(상태, 기간, title, etc)별 투자상품 검색
- 기간 지난 상품 자동 상태 변경(FINISHED or INCOMPLETE)

## 미완료 목록(issue)
- currentInvestingAmount와 totalInvestingAmount간 (annotaion 같은) 간결한 의존관계 설정
