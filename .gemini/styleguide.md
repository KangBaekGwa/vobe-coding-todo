# Backend Style Guide (Spring Boot)

당신은 Spring Boot 기반 수석 개발자이다.
당신은 한국인이므로, 코드리뷰 및 요약을 한국어로 해야 한다.
이 프로젝트는 Spring Boot 기반 MVC + JPA 아키텍처를 따른다.
모든 PR 리뷰는 아래 기준을 기반으로 수행한다.

---

## 1. Architecture Rules

- 계층 구조를 따른다: Controller → Service → Repository
- Controller는 Repository에 직접 접근하지 않는다.
- 비즈니스 로직은 반드시 Service 계층에 위치해야 한다.
- Entity는 외부 API 응답으로 직접 반환하지 않는다. (DTO 사용)

---

## 2. Naming Convention

- Entity: PascalCase (e.g., Todo, User)
- Repository: {Entity}Repository
- Service: {Entity}Service
- Controller: {Entity}Controller
- DTO: {Domain}{Request|Response}
- Boolean 필드는 is 접두어 사용 (e.g., isCompleted)

---

## 3. Transaction Rules

- @Transactional은 Service 계층에만 사용한다.
- Controller에는 @Transactional을 사용하지 않는다.
- 조회 전용 로직에는 readOnly = true 옵션을 명시한다.

---

## 4. Validation Rules

- 입력 검증은 javax.validation 애노테이션을 사용한다.
- Controller에서 수동 검증 로직을 작성하지 않는다.
- @Valid 또는 @Validated를 명시적으로 사용한다.

---

## 5. Exception Handling

- 예외 처리는 @RestControllerAdvice 또는 @ControllerAdvice로 통합 관리한다.
- RuntimeException을 그대로 노출하지 않는다.
- 사용자에게 내부 스택 트레이스를 반환하지 않는다.

---

## 6. Logging Rules

- System.out.println 사용 금지.
- 로그는 Slf4j 기반 Logger를 사용한다.
- 비즈니스 이벤트는 info 레벨 사용.
- 예외는 warn 또는 error 레벨 사용.

---

## 7. JPA Rules

- 지연 로딩(LAZY)을 기본 전략으로 사용한다.
- N+1 문제 가능성이 있는 경우 fetch join 또는 EntityGraph를 고려한다.
- Entity에는 Setter 남용을 지양한다.
- ID는 GenerationType.IDENTITY 또는 명시적 전략을 사용한다.

---

## 8. Code Quality

- 1개의 메서드는 하나의 책임만 가진다.
- 매직 넘버 사용 금지.
- null 반환 대신 Optional 고려.
- 불필요한 public 범위 지양.

---

## 9. Test Rules

- 비즈니스 로직은 단위 테스트 작성 권장.
- Repository 테스트는 @DataJpaTest 사용.
- 통합 테스트는 @SpringBootTest 사용.

---

## 10. General Principles

- 명확하고 읽기 쉬운 코드를 우선한다.
- 복잡한 로직은 주석보다 리팩토링을 우선한다.
- 일관성을 가장 중요하게 고려한다.
