# EventsforDicoding

Kotlin으로 제작된 Android 애플리케이션으로, 이벤트 세부 정보를 관리하고 조회할 수 있습니다. Dicoding의 [Belajar Fundamental Aplikasi Android](https://www.dicoding.com/academies/14-belajar-fundamental-aplikasi-android) 과정과 [Android Developer](https://www.dicoding.com/learningpaths/7) 학습 경로의 일환으로 개발된 이 프로젝트는 현대 Android 개발 관행을 MVVM 아키텍처를 통해 구현한 사례를 보여줍니다. 사용자는 이벤트를 즐겨찾기로 표시하고, 라이트/다크 모드를 전환하며, 알림 환경설정을 커스터마이즈할 수 있습니다. 퍼포먼스, 모범 사례 준수, 직관적인 사용자 경험, 다국어 지원을 중점으로 정성껏 제작되었습니다.

## 주요 기능

- ✅ 완료된 이벤트 목록 탐색
- 🔍 제목으로 이벤트 검색
- 📄 이벤트 상세 정보 조회
- ❤️ Room을 사용한 영구 저장 기반 좋아요/좋아요 취소
- 🌐 다국어 지원 (영어, 인도네시아어, 한국어)
- 🧼 Material Design 가이드라인을 따르는 깔끔하고 반응형 UI

## 기술 스택

- **언어**: Kotlin
- **아키텍처**: MVVM (Model-View-ViewModel)
- **데이터 바인딩**: LiveData, ViewModel
- **네트워킹**: Retrofit, 코루틴, Dicoding 이벤트 API
- **로컬 저장소**: Room 데이터베이스
- **UI 구성요소**: RecyclerView, Material Components, View Binding
- **기타**: AndroidX, Navigation Component, Dark Mode, SharedPreferences

## 사전 준비사항

프로젝트를 시작하기 전에 다음 소프트웨어가 설치되어 있어야 합니다:

- **Android Studio** (최신 안정 버전 권장)
- **Git** (레포지토리 클론용)

## 시작하기

프로젝트를 로컬에서 실행하려면:

1. 터미널을 열고 다음 명령어를 입력합니다:

   ```bash
    git clone https://github.com/khw315/EventsforDicoding.git
    ```

2. Android Studio로 프로젝트 열기:
    - Android Studio를 엽니다.
    - `기존 Android Studio 프로젝트 열기`를 선택합니다.
    - 클론한 레포지토리 경로로 이동하여 선택합니다.

3. 프로젝트 빌드:
    - Android Studio가 자동으로 프로젝트를 빌드합니다.
    - 자동으로 빌드되지 않는 경우 메뉴에서 **Build** > **Make Project**를 선택합니다.

4. 앱 실행:
    - Android 기기를 연결하거나 에뮬레이터를 사용합니다.
    - 녹색 실행 버튼을 클릭하여 앱을 실행합니다.

## 앱 구조

- **홈**: RecyclerView를 이용하여 예정된 이벤트와 완료된 이벤트를 두 가지 카테고리로 표시합니다.
- **상세 페이지**: 이벤트의 이름, 날짜, 설명 등 상세 정보를 보여주며 공식 웹페이지 링크를 포함합니다.
- **즐겨찾기**: 사용자가 이벤트를 즐겨찾기 목록에 저장할 수 있으며, Room 데이터베이스에 영구적으로 저장됩니다.
- **설정**:
  - **다크 모드**: 라이트 모드와 다크 모드를 전환할 수 있습니다.
  - **알림 설정**: 이벤트 알림을 활성화하거나 비활성화할 수 있습니다.
  - **네트워크 연결 상태 확인**: 앱이 인터넷 연결 상태를 모니터링하고 오프라인일 경우 사용자에게 알립니다

## 사용 방법

앱을 실행하면 다음 작업을 수행할 수 있습니다:

- **이벤트 탐색**: 홈 화면에서 예정된 이벤트와 완료된 이벤트를 쉽게 탐색할 수 있습니다.
- **이벤트 상세 보기**: 이벤트를 터치하여 상세 정보와 공식 웹페이지 링크를 볼 수 있습니다.
- **이벤트 즐겨찾기**: 이벤트를 즐겨찾기 목록에 추가하여 나중에 빠르게 접근할 수 있습니다.
- **설정 커스터마이즈**: 설정 페이지에서 다크 모드를 활성화하거나 알림 환경설정을 관리할 수 있습니다.
- **오프라인 처리**: 기기가 오프라인일 때 앱이 이를 감지하고 사용자에게 알립니다.

## 라이선스

이 프로젝트는 **MIT 라이선스** 하에 배포됩니다. 자세한 내용은 [LICENSE](./LICENSE) 파일을 확인하세요.

## 연락처

이 프로젝트에 대한 질문이나 피드백이 있으시면 [이메일](mailto:contact@alfaisal.my.id)로 연락하시거나 [LinkedIn](https://www.linkedin.com/in/fafr/)에서 저와 연결해 주세요.

## 감사의 말

이 프로젝트는 Dicoding의 [Belajar Fundamental Aplikasi Android](https://www.dicoding.com/academies/14-belajar-fundamental-aplikasi-android) 과정과 [Android Developer](https://www.dicoding.com/learningpaths/7) 학습 경로의 일환으로 개발되었습니다. 이 기회를 통해 Android 개발에 대한 깊은 이해를 얻을 수 있었습니다.

이 프로젝트는 다양한 리소스와 도구의 도움으로 가능했습니다. 특별히 감사드립니다:

- [Android Developers](https://developer.android.com/) 문서 및 모범 사례.
- [Android Studio](https://developer.android.com/studio) IDE.
- [AndroidX](https://developer.android.com/jetpack/androidx) 라이브러리.
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) 비동기 프로그래밍.
- [Git](https://git-scm.com/) 버전 관리.
- [GitHub](https://github.com/) 프로젝트 호스팅.
- [Glide](https://github.com/bumptech/glide) 이미지 로딩.
- [Kotlin](https://kotlinlang.org/) 프로그래밍 언어.
- [Lottie](https://airbnb.io/lottie/#/) 애니메이션.
- [Material Components](https://material.io/develop/android/components) UI 구성요소.
- [Material Design](https://material.io/) 디자인 가이드라인.
- [Navigation Component](https://developer.android.com/guide/navigation) 내비게이션.
- [Retrofit](https://square.github.io/retrofit/) 네트워킹 라이브러리.
- [Room](https://developer.android.com/training/data-storage/room) 데이터베이스.
- [SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences) 간단한 데이터 저장.
- [Shimmer](https://github.com/facebookarchive/shimmer-android) 로딩 애니메이션.
- 그리고 그 외 많은 리소스들.

For the English version, please refer to the [README.md](README.md) file.
