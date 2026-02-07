# New Feature Implementation Guidelines

이 문서는 새로운 도메인 기능을 구현할 때 필요한 초기 스캐폴딩(Scaffolding)을 생성한다.

**AGENTS.md**의 패키지 구조(`domain` vs `model`)를 엄격히 준수한다.

## 1. Required Artifacts
요청된 기능에 대해 다음 파일들을 생성한다:

1. **Model Layer** (`src/main/java/.../model/{feature}`)
    - **Entity**: `TemporalEntity` 상속, 정적 팩토리 메서드, `protected` 생성자.
    - **Repository**: `JpaRepository` 인터페이스.

2. **Domain Layer** (`src/main/java/.../domain/{feature}`)
    - **DTO**: `Request` (Record), `Response` (Record).
    - **Service**: 트랜잭션 처리, 비즈니스 로직.
    - **Controller**: API 엔드포인트 정의.

## 2. Implementation Rules
- **Controller**: Service만 의존. Request DTO 수신 -> Service 호출 -> Response DTO 반환.
- **Service**: Entity 생성은 반드시 Entity 내의 **정적 팩토리 메서드**를 호출.
- **DTO**: Entity -> DTO 변환 메서드(`fromEntity`)를 DTO 내부에 `static`으로 구현하거나 Mapper 사용.

## 3. Output Format
- 각 파일의 상단에 `package` 구문을 정확한 경로로 명시한다.
- 설명 없이 코드 블록만 연속으로 출력한다.