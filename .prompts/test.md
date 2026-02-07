# Test Code Generation Guidelines

이 문서는 JUnit 5와 Mockito를 사용한 테스트 코드 작성 시 적용된다.

## 1. General Rules
- **Framework**: JUnit 5, AssertJ, Mockito
- **Naming**: 한글로 명확하게 작성 (`@DisplayName("회원 가입 성공 테스트")`)
- **Pattern**: `Given - When - Then` 주석으로 구획 분리

## 2. Layer-Specific Strategies

### 2.1 Service Layer (Unit Test)
- **Annotation**: `@ExtendWith(MockitoExtension.class)` 사용 (Spring Context 로딩 금지).
- **Mocking**: Repository 등 의존 객체는 `@Mock`으로 선언하고 `@InjectMocks`로 주입.
- **Verification**: `verify(repository, times(1)).save(...)` 등으로 호출 검증.
- **Fixture**: 테스트 데이터 생성 시 Builder 패턴이나 별도 Fixture 메서드 활용.

### 2.2 Repository Layer (Slice Test)
- **Annotation**: `@DataJpaTest` 사용.
- **Config**: Auditing 적용을 위해 필요시 `@Import(JpaConfig.class)` 추가.
- **Check**: `save` 후 `findById`로 조회하여 필드 값이 일치하는지 검증 (`assertThat`).

### 2.3 Controller Layer (WebMvc Test)
- **Annotation**: `@WebMvcTest(TargetController.class)` 사용.
- **Security**: `@WithMockUser` 사용하여 인증 통과 처리.
- **Check**: `MockMvc`를 사용하여 Status Code 및 Response Body(JSON) 검증.