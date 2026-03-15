# 장비GUI프로그래밍
# 🖥️ Java GUI & MySQL 기반 통합 관리 시스템

> Java Swing과 JDBC를 활용한 데이터베이스 연동 데스크탑 애플리케이션  
> 교과목: 장비GUI프로그래밍 | 개발 환경: Eclipse IDE, Java 17, MySQL

---

## 📌 프로젝트 개요

Java Swing GUI 환경에서 JDBC를 통해 MySQL 데이터베이스와 연동, **실제 데이터를 CRUD 처리하는 데스크탑 관리 시스템**을 설계 및 구현한 프로젝트입니다.  
학생 정보 관리, 성적 처리, 상품 재고 관리 등 복수의 서브시스템을 하나의 프로젝트로 구성하여 실무형 개발 역량을 습득했습니다.

---

## 🛠️ 기술 스택

| 구분 | 기술 |
|------|------|
| Language | Java (JDK 17) |
| GUI Framework | Java Swing (JFrame, JTable, JPanel 등) |
| Database | MySQL 5.7+ |
| DB 연동 | JDBC (`mysql-connector-java-5.1.47`) |
| 시각화 라이브러리 | JFreeChart 1.0.9 + JCommon 1.0.12 |
| 개발 도구 | Eclipse IDE |

---

## 📂 프로젝트 구조

```
src/projectDB/
├── DbFrame.java      # 메인 프레임 — 학생 정보 등록 (INSERT)
├── DBedit.java       # 데이터 수정 · 삭제 전용 인터페이스
├── DBsearch.java     # 유사 패턴 검색 및 결과 탐색
├── InMs01.java       # 상품 재고 관리 + JFreeChart 시각화
├── StGUI01.java      # 학생 성적 관리 + 자동 평균 계산
├── DBcon.java        # DB 연결 테스트 및 기본 접속 로직
└── DBSQL01.java      # PreparedStatement 활용 SQL 실행 예제
```

---

## 📑 주요 기능 상세

### 1. 학생 정보 관리 시스템 (SIMS) — `DbFrame.java`

- 학생 정보(이름, GPA, 입학년도, 이메일, 학과) 입력 폼 구성
- **GridLayout** 기반 UI, JComboBox로 학과 선택
- MySQL `testst` 테이블에 데이터 INSERT 처리
- 등록 / 취소 / 검색 / 수정·삭제 / 종료 버튼 이벤트 연결

```java
// 등록 버튼 이벤트 — SQL INSERT 실행 예시
String sql = "insert into testst (name, gpa, year, email, dept) values('"
    + Name.getText().trim() + "', "
    + Gpa.getText().trim() + ", "
    + Year.getText().trim() + ", '"
    + Email.getText().trim() + "', '"
    + Dept.getSelectedItem().toString().trim() + "')";
stmt.executeUpdate(sql);
```

---

### 2. 데이터 수정 · 삭제 — `DBedit.java`

- DB 전체 데이터를 `JCheckBox` + `JTextField` 조합으로 화면에 렌더링
- **체크박스 선택 → 삭제**, **텍스트 필드 직접 수정 → 일괄 UPDATE** 방식 구현
- `PreparedStatement`로 파라미터화된 UPDATE / DELETE 쿼리 실행
- 수정/삭제 완료 후 `selectList()` 호출로 화면 즉시 갱신

```java
// 체크된 행만 삭제 처리
if (cb.isSelected()) {
    pstmt.setString(1, cb.getText()); // sid 값
    pstmt.executeUpdate();
}
```

---

### 3. 데이터 검색 — `DBsearch.java`

- `PreparedStatement` + `LIKE` 연산자로 이름 / 학과 유사 패턴 검색
- `ResultSet` 커서를 직접 제어해 **이전(previous) / 다음(next) 결과 탐색** 구현
- 검색 결과 없을 시 라벨로 사용자 피드백 제공

```sql
SELECT * FROM testst WHERE name LIKE ? OR dept LIKE ?
```

---

### 4. 상품 재고 관리 — `InMs01.java`

- 상품명 / 수량 / 가격 CRUD (추가, 삭제, 수정, 검색)
- 상품명 키워드 검색 → `equalsIgnoreCase`로 대소문자 무관 매칭 후 해당 행 자동 선택
- **JFreeChart 연동**: 테이블 데이터를 막대그래프(Bar Chart)로 시각화
  - `CategoryPlot`, `BarRenderer`, 한글 폰트(Malgun Gothic) 설정 적용

```java
// 테이블 데이터 → JFreeChart 데이터셋 변환
for (int i = 0; i < tableModel.getRowCount(); i++) {
    String productName = tableModel.getValueAt(i, 0).toString();
    int quantity = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
    dataset.addValue(quantity, "수량", productName);
}
```

---

### 5. 학생 성적 관리 — `StGUI01.java`

- 이름 / 중간고사 / 기말고사 입력 → **평균 자동 계산** 후 JTable에 추가
- `LocalDateTime` + `DateTimeFormatter`로 입력 시각 자동 기록
- 선택한 행 삭제 기능 및 입력값 유효성 검사 (공백 체크, 숫자 파싱 예외 처리)

---

## ⚙️ 실행 방법

### 1. 데이터베이스 설정

```sql
CREATE DATABASE itdb;
USE itdb;

CREATE TABLE testst (
    sid   INT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(20),
    gpa   FLOAT,
    year  INT,
    email VARCHAR(50),
    dept  VARCHAR(30)
);
```

### 2. DB 접속 정보 확인

```
URL      : jdbc:mysql://localhost:3306/itdb?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true
User     : boot
Password : 12345
```

### 3. 라이브러리 (Build Path에 추가)

```
mysql-connector-java-5.1.47.jar
jfreechart-1.0.9.jar
jcommon-1.0.12.jar
```

---

## 💡 구현 과정에서 해결한 주요 이슈

| 문제 | 해결 방법 |
|------|-----------|
| 한글 데이터 깨짐 | JDBC URL에 `useUnicode=true&characterEncoding=utf8` 파라미터 추가 |
| SSL 인증 오류 | `useSSL=false&allowPublicKeyRetrieval=true` 옵션으로 개발 환경 설정 |
| JFreeChart 한글 축 레이블 깨짐 | `CategoryAxis`, `ValueAxis`에 `Font("Malgun Gothic", ...)` 직접 지정 |
| ResultSet 양방향 탐색 | `createStatement()` 대신 `TYPE_SCROLL_INSENSITIVE` 방식으로 커서 제어 |

---

## 🎓 학습 성과

- **이벤트 기반 프로그래밍**: `ActionListener` 구현을 통한 버튼별 분기 처리 및 UI 상호작용 설계
- **JDBC 연동 전 과정 이해**: 드라이버 로딩 → 커넥션 생성 → Statement/PreparedStatement 실행 → ResultSet 처리 → 자원 해제
- **데이터 무결성**: 입력값 유효성 검사, 예외 처리(NumberFormatException, SQLException), 파라미터 바인딩으로 오류 방지
- **시각화 라이브러리 활용**: 텍스트 데이터를 차트로 변환하는 대시보드 설계 경험 습득
- **UI 설계 패턴**: BorderLayout / GridLayout / FlowLayout 조합으로 역할별 패널 분리 구성

---

## 📸 화면 구성

```
┌─────────────────────────────┐
│          SIMS 메인          │  ← DbFrame.java
│  NAME  [__________]         │
│  GPA   [__________]         │
│  YEAR  [__________]         │
│  EMAIL [__________]         │
│  DEPT  [  드롭다운  ▼]      │
│  [등록] [취소] [수정/삭제]  │
│  [검색]       [종료]        │
└─────────────────────────────┘
         ↓ 수정/삭제 클릭
┌─────────────────────────────────────────────────┐
│  sid  │ name │ gpa │ year │ email │ dept        │  ← DBedit.java
│ [☑1] │[홍길동]│[3.5]│[2022]│[...]  │[스마트IT..] │
│ [☐2] │[김철수]│[4.0]│[2023]│[...]  │[AI학과    ] │
│              [수정]  [삭제]  [현재창 종료]       │
└─────────────────────────────────────────────────┘
```

---

> 본 프로젝트는 Java Swing GUI와 MySQL JDBC 연동의 전체 사이클을 실습하기 위한 교과 프로젝트입니다.
