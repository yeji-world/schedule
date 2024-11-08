# 일정 관리

### [ 요구사항 ]

- 일정 구성
  - 작성자명, 일정제목, 일정내용, 작성일/수정일(날짜, 시간 포함)
- 일정 CRUD
- 적절한 상태코드 반환
- 수정, 삭제 시 비밀번호 일치 확인
- 생성, 수정, 삭제 시 비밀번호 반환 제외

**C 일정 생성**
- 각 일정의 고유 식별자(ID) 자동 생성
- 일정 생성 시 작성자명, 비밀번호, 할일 필요
- 생성된 데이터(식별자, 작성자명, 할일, 작성일, 수정일) 응답
  - 상태코드: 201 Created
- 최초 입력 시 수정일 = 작성일

**R 일정 전체 조회**
- 조회 조건: 수정일(YYYY-MM-DD) 내림차순 혹은 작성자명
- 여러 개의 데이터를 배열 형태로 한번에 응답
  - 상태코드: 200 OK
- 데이터가 없는 경우 비어 있는 배열 형태로 응답
  - 상태코드: 200 OK

**R 일정 선택 조회**
- 조회할 일정에 대한 식별자값 필요
- 조회된 데이터(식별자, 작성자명, 할일, 작성일, 수정일) 응답
  - 상태코드: 200 OK
- 조회할 데이터가 없는 경우 Exception 발생
  - 상태코드: 404 Not Found

**U 일정 선택 수정**
- 수정할 일정에 대한 식별자값 필요
- 비밀번호 일치 확인(불일치 시 오류코드 및 메세지 반환)
- 비밀번호 반환 제외
- 할일, 작성자명만 수정 가능
  - 최소 둘 중 하나의 값 필수
  - 상태코드: 400 Bad Request(필수값이 없거나 다른 값 수정 요청 시) 
- 수정된 데이터(식별자, 작성자명, 할일, 작성일, 수정일) 응답
  - 상태코드: 200 OK
- 수정할 데이터가 없는 경우 Exception 발생
  - 상태코드: 404 Not Found
- 수정 완료 시 수정 시점으로 수정일 변경

**D 일정 선택 삭제**
- 삭제할 일정에 대한 식별자값 필요
- 비밀번호 일치 확인(불일치 시 오류코드 및 메세지 반환)
- 비밀번호 반환 제외
- 상태코드: 200 OK 응답
- 삭제할 데이터가 없는 경우 Exception 발생
  - 상태코드: 404 Not Found

 
### [ API 명세서 ]
<img width="700" alt="API" src="https://github.com/user-attachments/assets/cc89dfa3-e544-4f5a-b037-97751b0ce7db">


### [ ERD ]
![image](https://github.com/user-attachments/assets/9734f5d3-0231-49a1-bedd-7c119d4bdbc2)


### [ SQL ]
```
-- 1. 테이블 생성(Create)
-- schedule 테이블 생성
CREATE TABLE schedule (
                          id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '일정 ID',
                          name VARCHAR(20) NOT NULL COMMENT '작성자명',
                          password VARCHAR(25) NOT NULL  COMMENT '비밀번호',
                          content VARCHAR(200) NOT NULL COMMENT '일정',
                          createdDate TIMESTAMP NOT NULL COMMENT '작성일',
                          updatedDate TIMESTAMP NOT NULL COMMENT '수정일'
);

-- 2. 일정 생성(Insert)
-- schedule 정보
INSERT INTO schedule (name, password, content)
    VALUE ("작성자명", "1234", "일정");

-- 3. 전체 일정 조회(Select)
SELECT * FROM schedule;

-- 4. 선택 일정 조회(Select)
SELECT * FROM schedule WHERE id = 1;

-- 5. 선택 일정 수정(Update)
UPDATE schedule SET content = "수정한 일정", updatedDate = current_timestamp() Where id = 1;
UPDATE schedule SET name = "수정한 작성자명", updatedDate = current_timestamp() Where id = 1;

-- 6. 선택 일정 삭제(Delete)
DELETE from schedule where id = 1;
```

