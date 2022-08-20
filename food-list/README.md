파일위치  
js - src/main/resources/static/js  
css - src/main/resources/static/css  
html - src/main/resources/templates  

### thymeleaf
- 표현식
  - 변수 - ${변수이름}
  - 링크 - @{링크}
- 조건, 비교연산 등 다양한 연산 가능
- 기본 문법
  - xmlns:th / xmlns:layout - 네임스페이스 설정
  - layout:decorate - 레이아웃으로 사용, 확장자 제외  
  ex) layout:decorate="~{디렉토리/확장자 제외한 파일이름}"
  - th:fragment : 파편 이름 선언
  - layout:fragment : 파편과 이름을 동일시하면 속 내용 재정의
