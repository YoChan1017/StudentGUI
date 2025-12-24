# 장비GUI프로그래밍

## 🖥️ Java GUI & MySQL 기반 통합 관리 시스템

장비GUI프로그래밍 교과 과정 프로젝트: Java Swing과 MySQL을 활용한 데이터 관리

Java GUI(Swing) 환경에서 JDBC를 통해 MySQL 데이터베이스와 연동하여 실제 데이터를 처리하는 프로세스를 학습 및 구현

## 📑 목차

1. 기술 스택

2. 주요 기능

3. 프로젝트 구조

4. 실행 방법

5. 학습 성과

## 🛠️ 기술 스택

### Development

> Language : Java (JDK 17+)

> GUI Framework : Java Swing

> Database : MySQL

### Libraries

> MySQL Connector (DB 연동)

> JFreeChart (재고 현황 그래프 출력)

## 📌 주요 기능

### 1️⃣ 학생 정보 관리 시스템 (SIMS)

데이터 CRUD : 학생 정보(이름, 점수, 입학년도, 이메일, 학과 등)의 등록, 수정, 삭제 기능

편집 모드 (DBedit) : 체크박스를 활용한 다중 데이터 삭제, 텍스트 필드를 이용한 실시간 데이터 일괄 업데이트

데이터 검색 (DBsearch) : LIKE 연산자를 활용한 이름/학과 유사 패턴 검색, ResultSet 커서 제어를 통한 '이전/다음' 결과 탐색 기능

### 2️⃣ 상품 재고 및 성적 대시보드

재고 관리 (InMs01) : 상품명, 수량, 가격 정보를 관리하며 실시간 리스트 업데이트

> 데이터 시각화 : JFreeChart를 연동하여 재고 수량을 막대그래프로 시각화

성적 관리 (StGUI01) : 중간/기말 점수를 기반으로 평균 점수 자동 산출, 데이터 입력 시 현재 시스템 시간을 기록하여 JTable에 표시

## 📂 프로젝트 구조

src/projectDB/   <br>
├── DbFrame.java <br>
├── DBedit.java  <br>
├── DBsearch.java<br>
├── InMs01.java  <br>
├── StGUI01.java <br>
├── DBcon.java   <br>
└── DBSQL01.java <br>

> DbFrame.java : 시스템 메인 프레임 및 학생 정보 등록 관리

> DBedit.java : 데이터 수정 및 삭제 전용 인터페이스

> DBsearch.java : 데이터 검색 및 결과 네비게이션

> InMs01.java : 상품 재고 관리 및 차트 시각화 모듈

> StGUI01.java : 학생 성적 관리 및 자동 계산 모듈

> DBcon.java : 데이터베이스 연결 테스트 및 기본 접속 로직

> DBSQL01.java : SQL 실행 및 "PreparedStatement" 활용 예제


## ⚙️ 실행 방법

### 1. 데이터베이스 설정

MySQL에 접속하여 아래와 같이 데이터베이스와 테이블을 생성합니다.

> CREATE DATABASE itdb;

> USE itdb;

> CREATE TABLE testst (                                         <br>
> &nbsp;&nbsp;&nbsp;&nbsp; sid INT AUTO_INCREMENT PRIMARY KEY,  <br>
> &nbsp;&nbsp;&nbsp;&nbsp; name VARCHAR(20),                    <br>
> &nbsp;&nbsp;&nbsp;&nbsp; gpa FLOAT,                           <br>
> &nbsp;&nbsp;&nbsp;&nbsp; year INT,                            <br>
> &nbsp;&nbsp;&nbsp;&nbsp; email VARCHAR(50),                   <br>
> &nbsp;&nbsp;&nbsp;&nbsp; dept VARCHAR(30)                     <br>
> );


### 2. 환경 변수 확인

소스 코드 내의 DB 접속 정보가 본인의 환경과 일치하는지 확인합니다.

> URL : jdbc:mysql://localhost:3306/itdb

> User : boot

> Password : 12345

### 3. 라이브러리 추가

프로젝트의 Build Path에 아래 JAR 파일들을 추가해야 합니다.

> mysql-connector-java-x.x.x.jar

> jfreechart-x.x.x.jar

> jcommon-x.x.x.jar

## 🎓 학습 성과

이벤트 기반 프로그래밍 : ActionListener를 통한 사용자 인터페이스 상호작용 설계 역량 습득.

데이터 무결성 : Java 코드와 DB 테이블 간의 데이터 타입 매칭 및 유효성 검사 로직 구현.

시각화 라이브러리 활용 : 텍스트 형태의 데이터를 그래프로 변환하여 정보 전달력을 높이는 대시보드 설계 경험.
