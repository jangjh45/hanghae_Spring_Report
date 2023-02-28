# Spring Boot CRUD API Lv.1
<br>

🏁 Goal:  "스프링 부트로 로그인 기능이 없는 나만의 항해 블로그 백엔드 서버 만들기"
<br>

✅ 서비스 완성 요구사항
1. 아래의 요구사항을 기반으로 Use Case 그려보기
    - 손으로 그려도 됩니다.
    - cf. [https://narup.tistory.com/70](https://narup.tistory.com/70)
2. 전체 게시글 목록 조회 API
    - 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
    - 작성 날짜 기준 내림차순으로 정렬하기
3. 게시글 작성 API 
    - 제목, 작성자명, 비밀번호, 작성 내용을 저장하고
    - 저장된 게시글을 Client 로 반환하기
4. 선택한 게시글 조회 API 
    - 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기 
    (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
5. 선택한 게시글 수정 API
    - 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
    - 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
6. 선택한 게시글 삭제 API
    - 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
    - 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기

## 1. Use Case
![Use Case](https://user-images.githubusercontent.com/99319021/221593567-37fc82e3-16cf-4f3c-9079-ef960fec0b5a.png)
## 2. API 명세서
[📋 API 명세서 📋](https://fanatical-ornament-28d.notion.site/API-18f26201685e4b03ba8081247a4ff41d)

<br>

❓ Why: 과제 제출시에는 아래 질문을 고민해보고 답변을 함께 제출해주세요.

1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)
POST 메서드로 HTTP Body에 담아서 값을 보내서 비밀번호 확인에 사용했다.

2. 어떤 상황에 어떤 방식의 request를 써야하나요?
GET: 리소스의 정보를 요청할 때.

POST: 새로운 리소스를 생성 또는 업데이트할떄 사용함.
예를 들면은 사용자가 새로운 계정을 생성하거나 블로그 글을 작성할 때 사용됩니다.

PUT: 기존 리소스를 업데이트할 때 사용한다.

DELETE: 리소스를 삭제할 때 사용

3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?

HTTP URI(Uniform Resource Identifier)를 통해 자원(Resource)을 명시하고,
HTTP Method(POST, GET, PUT, DELETE)를 통해 해당 자원에 대한 CRUD Operation을 적용하는 것을 의미한다.

-- REST API 설계 규칙 --
1. 슬래시 구분자(/ )는 계층 관계를 나타내는데 사용한다.
2. URI 마지막 문자로 슬래시(/ )를 포함하지 않는다.
3. 밑줄(_)은 URL에 사용하지 않는다.
4. URL경로에는 소문자가 적합하다.
5. 파일확장자는 URI에 포함하지 않는다.

4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)
계층별로 분리했습니다.

5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요!
