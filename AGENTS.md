# AI Collaboration Policy & Project Rules

이 프로젝트는 **Spring Boot 4.0.2** 기반의 **MVC + JPA** 아키텍처를 따른다.
모든 AI 도구(Gemini, Copilot, Cursor)는 본 문서를 **최우선 지침(Supreme Law)**으로 삼는다.

---

## 0. Fundamental Principles
- **언어**: 한국어 (모든 설명, 주석, 리뷰, 커밋 메시지)
- **JDK**: Java 25
- **Framework**: Spring Boot 4.0.2
- **Database**: PostgreSQL
- **Style**: Google Java Style Guide 준수

---

## 1. Package Structure (Strict)
**[중요]** 비즈니스 로직(`domain`)과 데이터 영속성(`model`)을 물리적으로 분리한다.
임의의 최상위 패키지를 생성하지 않는다.

```text
src/main/java/com/baekgwa/vibecodingtodolist
├── domain           # [비즈니스/웹 계층]
│   └── {feature}    # (예: member, post)
│       ├── controller
│       ├── service
│       └── dto
├── model            # [데이터/영속 계층]
│   └── {feature}
│       ├── entity
│       └── repository
├── global           # [공통 설정] Config, Exception, Security, Utils
└── infra            # [외부 연동] S3, Mail, External API Clients
```

---

## 2. Layer Responsibilities
### 2.1 Domain Layer (domain.*)
- Controller:
  - 요청 검증 및 DTO 반환 담당. 
  - Entity 반환 금지. 
  - Repository 직접 접근 금지 (반드시 Service 경유). 
  - @RestController, @RequiredArgsConstructor 사용. 
- Service:
  - 핵심 비즈니스 로직 구현. 
  - 트랜잭션 경계 설정 (@Transactional). 
  - Repository를 통해서만 영속 계층 접근.
- DTO:
  - 데이터 전송 객체. record 타입 사용 권장. 
  - domain 패키지에 위치.

### 2.2 Model Layer (model.*)
- Entity: DB 테이블 매핑 객체. (model 패키지 필수)
- Repository: JpaRepository 인터페이스. (model 패키지 필수)
- 제약: Controller나 Service는 model 패키지의 구조를 알 수 있지만, 역참조는 금지한다.

### 2.3 Global & Infra
- Global: 공통 응답(ApiResponse), 예외 처리(GlobalExceptionHandler), 보안 설정.
- Infra: 외부 시스템 연동 코드 (AWS, Redis 등).

---

## 3. Entity Implementation Strategy (Strict)
[중요] 모든 Entity는 반드시 아래 패턴을 따른다.
- 상속: TemporalEntity (또는 BaseEntity)를 상속받아 시간 관리.
- 접근 제어:
  - Class Level: @NoArgsConstructor(access = AccessLevel.PROTECTED)
  - Constructor: private (외부 생성 차단)
- 생성 전략:
  - 정적 팩토리 메서드(of) 필수 사용.
  - **ID(PK)**는 생성자 파라미터에서 제외. 
  - 연관 관계: 모든 ManyToOne, OneToOne은 FetchType.LAZY 필수.

### ✅ Entity Code Standard (Example)
```Java
@Entity
@Getter
@Table(name = "stack_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StackPostEntity extends TemporalEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stack_id")
    private StackEntity stack;

    @Column(name = "sequence", nullable = false)
    private Long sequence;

    // 생성자는 Private으로 잠금
    private StackPostEntity(StackEntity stack, Long sequence) {
       this.stack = stack;
       this.sequence = sequence;
    }

    // 정적 팩토리 메서드 제공
    public static StackPostEntity of(StackEntity stack, Long sequence) {
       return new StackPostEntity(stack, sequence);
    }
}
```

---

## 4. Implementation Rules
### 4.1 Dependency Injection & Immutability
- 생성자 주입 원칙: @Autowired 필드 주입 금지. 
- Lombok 활용: private final 필드 선언 후 @RequiredArgsConstructor 사용. 
- 불변성: 객체는 기본적으로 불변으로 설계한다.

### 4.2 Transaction Policy
- Service 계층: @Transactional은 Service 계층에만 적용. 
- ReadOnly: 클래스 레벨에 @Transactional 적용 금지. 
- CUD 작업: 데이터 변경 메서드에만 @Transactional 별도 명시.

### 4.3 Validation Policy
- Standard: jakarta.validation 어노테이션 사용 (@NotNull, @Size 등). 
- Controller: 파라미터에 @Valid 또는 @Validated 명시. 
- Logic: Controller 내부에서 수동 if 검증 로직 지양.

### 4.4 JPA & Performance
- N+1 문제: fetch join 또는 EntityGraph를 적극 활용하여 해결. 
- Setter 금지: Entity의 상태 변경은 비즈니스 메서드(예: changePassword())를 통해서만 수행.

### 4.5 Exception Handling
- Global: @RestControllerAdvice로 예외 통합 관리. 
- Response: 내부 스택 트레이스를 클라이언트에 노출하지 않는다.

---

## 5. Code Quality & Testing
### 5.1 Quality Rules
- SRP: 한 메서드는 하나의 책임만 가진다.
- No Magic Number: 상수로 추출하여 사용.
- Optional: null 반환 대신 Optional<T> 사용.
- Logging: System.out.println 사용 금지 → @Slf4j (log.info) 사용.

### 5.2 Testing Policy
- Unit Test: 비즈니스 로직 검증 (JUnit 5 + Mockito).
- Repository Test: @DataJpaTest 사용.
- Integration Test: @SpringBootTest 사용.