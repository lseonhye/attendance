## 모바일융합공학과 25-1 데이터베이스2 프로젝트
# 📝 출석 기록 데이터 베이스 프로젝트 📝

- 20221006 김나연
- 20227002 이선혜

## 1. 프로젝트 개요
본 프로젝트는 회원의 출입 기록과 학습 내용을 체계적으로 관리하기 위한 출석 기록 관리 시스템이다.

- 사용자는 연구실 출입, 퇴실 기록을 남긴다.
- 하루 학습 내용을 기록한다.
- 타이머 기능을 사용하여 효율적으로 개인 공부를 한다.
- 입실 시간과 퇴실 시간을 계산하여 하루 총 공부량을 계산한다.

## 2. 주요 기능 및 요구사항
### 사용자 관리
- 사용자 가입 시 다음 정보를 입력:
  - 아이디 (user_id), 비밀번호, 이름, 학번, 직급
- 사용자는 아이디로 식별됨
- 사용자 권한은 일반 사용자와 관리자로 구분되며, 관리자는 모든 정보 수정 가능

### 출석 관리
- 사용자는 하루에 한 번 출석 가능
- 출석 정보는 출석 번호(attendance_id), 날짜(attendance_date), 메모로 구성
- 출석 시 자동으로 입실 시간이 기록되며, 퇴실 시간은 사용자가 입력
- 사용자와 출석 날짜 조합은 중복될 수 없음

  
### 출석 로그 및 학습 기록
- 출석 후에만 학습 기록 작성 가능
- 출석 로그는 다음 정보를 포함:
  - 사용자 아이디, 출석 번호, 입실 시간, 퇴실 시간, 출석 여부, 메모
- 학습 기록 수정 가능, 수정 여부는 확인 가능하도록 설계

### 🧹 기능 요약 🧹
1. 사용자 등록 / 조회 / 수정 / 삭제
2. 출석 생성 (날짜별 1회)
3. 출석 로그 생성 (자동 입실 시간 기록 포함)
4. 퇴실 시간 업데이트
5. 메모 및 학습 내용 저장
6. 출석 여부 자동 판단 (결석 자동 처리 포함)
7. 하루 총 공부 시간 계산 기능
   
## 3. 기술 스택
### Backend :
- Java, Springboot, PostgreSQL, aws, postman

  - sever : aws
  <img width="400" alt="image" src="https://github.com/user-attachments/assets/27a6e004-5b34-4b62-a099-6a89222e3fed" />

    - 서버 배포 후 API 연결 확인
    <img width="400" alt="스크린샷 2025-06-23 09 19 11" src="https://github.com/user-attachments/assets/48d8106c-68e9-4090-a052-d3da090ae021" />


### Frontend :
- SVELTE, JavaScript, Figma

## 4. 데이터베이스 설계
### users table
<img width="600" alt="image" src="https://github.com/user-attachments/assets/5b630aff-b3d9-43c9-a0b7-af4ca4f6ae8b" />

### attendance table
<img width="600" alt="image" src="https://github.com/user-attachments/assets/757f0e8c-7bfb-4954-a304-7ff8f71741e8" />

### attendance_log table
<img width="600" alt="image" src="https://github.com/user-attachments/assets/d3fc0bbc-4617-4844-bdb7-dd83f6b77ff3" />

### DB - Diagram
<img width="300" alt="image" src="https://github.com/user-attachments/assets/5e443e8b-2587-4b0b-8a7d-dbdc04d0e961" />

## 5. API 명세서
<img width="600" alt="image" src="https://github.com/user-attachments/assets/374d235b-a4ae-4396-a4f2-363664c75351" />

<img width="600" alt="image" src="https://github.com/user-attachments/assets/a83377bd-47fa-4edb-99b6-1193d86c47fd" />

<img width="600" alt="image" src="https://github.com/user-attachments/assets/d596489d-adc2-437d-b022-477c87dec699" />

<img width="600" alt="image" src="https://github.com/user-attachments/assets/405434f6-8683-4178-b1f6-e9a03aef68b5" />

### 자동 결석 처리 로직
<img width="360" alt="스크린샷 2025-06-23 09 09 49" src="https://github.com/user-attachments/assets/a73a073f-dfd5-4722-9a15-ec79fd02329b" />
<img width="297" alt="스크린샷 2025-06-23 09 11 33" src="https://github.com/user-attachments/assets/fd3edccd-ac08-47d8-a346-6310cc81374f" />

  - `attendanceId = 20`이 전날이어야 함!

```java
LocalDate yesterday = LocalDate.now().minusDays(1); // 어제 날짜
// attendanceId = 20이 "2025-06-13"이면 오늘이 "2025-06-14"여야 작동
```

### 출석 시간 계산 기능
<img width="360" alt="스크린샷 2025-06-23 09 12 28" src="https://github.com/user-attachments/assets/8849fe94-2e2e-4713-aa63-2f99a85bb683" />

### 출석 시 자동 시간 기록
- 출석 기록 생성시 자동으로 현재시간으로 채우고 저장
<img width="360" alt="스크린샷 2025-06-23 09 12 50" src="https://github.com/user-attachments/assets/e7301ccd-7703-4052-8ff2-39faa0c4ac8d" />
<img width="360" alt="스크린샷 2025-06-23 09 13 02" src="https://github.com/user-attachments/assets/3f60d2d3-8fbe-4f6c-851e-7f3d7f15f2cb" />

## 6. 프로젝트 디자인
- 초기 디자인 구성
  <img width="500" alt="image" src="https://github.com/user-attachments/assets/842980ae-f319-49f8-a8ff-5b594d7359b0" />

  <img width="500" alt="image" src="https://github.com/user-attachments/assets/d8eeeb58-ba61-41df-bbca-08b24ee34dfa" />

- 최종 디자인
  
  <img width="500" alt="image" src="https://github.com/user-attachments/assets/e66bc6ac-2ca7-40d9-9b36-16afe2d140a1" />

  <img width="500" alt="image" src="https://github.com/user-attachments/assets/6493e2b4-8c6c-4f79-a718-b8fa5c996870" />

  <img width="500" alt="image" src="https://github.com/user-attachments/assets/477a35a1-1458-491d-aaf6-290b7fa57a81" />
