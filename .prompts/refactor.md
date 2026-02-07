# Refactoring Guidelines

이 문서는 기존 코드를 프로젝트 표준(`AGENTS.md`)에 맞춰 리팩토링할 때 적용된다.

## 1. Check List (Priority Order)
1. **Layer Violation Check**:
    - Controller가 Repository를 직접 호출하는지 확인. (발견 시 Service 경유하도록 수정)
    - Controller가 Entity를 반환하는지 확인. (발견 시 DTO로 변환)
    - Domain 패키지에서 Model 패키지를 역참조하는지 확인.
2. **Entity Pattern Enforcement**:
    - `@Setter` 제거.
    - 기본 생성자가 `protected`인지 확인.
    - 정적 팩토리 메서드(`of`) 패턴 적용 여부 확인.
3. **Immutability Check**:
    - DTO가 `record` 타입인지 확인.
    - 필드 주입(`@Autowired`)을 생성자 주입(`@RequiredArgsConstructor`)으로 변경.
4. **Modern Java**:
    - `var` 키워드 적용 가능한 곳 변경.
    - `switch` 문을 Expression으로 변경.
    - `if-else` 중첩을 Early Return 패턴으로 간소화.

## 2. Output Format
- 변경 전/후의 차이점을 설명하지 말고, **수정된 전체 코드**만 보여준다.
- 수정된 이유가 '치명적(보안, 트랜잭션 등)'일 경우에만 주석으로 짧게 남긴다.