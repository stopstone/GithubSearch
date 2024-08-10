# GithubSearch

깃허브 API를 활용하여 사용자 검색 기능을 구현한 안드로이드 애플리케이션입니다.
이 애플리케이션은 MVVM 아키텍처를 따르며, Clean Architecture의 개념을 적용하여 각 레이어의 책임을 분리했습니다. 

## 활용 기술

- 언어: Kotlin
- 아키텍처: MVVM (Model-View-ViewModel)
- Jetpack 컴포넌트:
  - ViewModel
  - Navigation Component
  - DataStore
- 비동기 처리: Coroutines, Flow
- 상태 관리: StateFlow
- 의존성 주입: Dagger Hilt
- 네트워크: Retrofit2, OkHttp3
- JSON 파싱: Gson
- 이미지 로딩: Glide

## 프로젝트 구조

```
com.stopstone.githubsearchpractice
├── data
│   ├── model
│   ├── remote
│   │   └── api
│   └── repository
├── domain
│   ├── model
│   ├── repository
│   └── usecase
│       └── favorite
├── util
└── view
    ├── common
    │   └── listener
    ├── favorite
    │   └── viewmodel
    ├── search
    │   └── viewmodel
    └── MainActivity
```

## 주요 기능 및 화면

<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/user-attachments/assets/8bcd4943-385c-4bef-bff2-e4230ea8cf8d" width="24%" alt="검색 화면">
  <img src="https://github.com/user-attachments/assets/e63080a3-33db-4e68-ba1a-0ee529bb060f" width="24%" alt="즐겨찾기 화면">
</div>

1. GitHub 사용자 검색
   - 검색창에 GitHub 사용자명을 입력하여 검색할 수 있습니다.
   - 검색 결과는 RecyclerView를 활용하여 표시됩니다.
   - 각 항목에는 사용자의 프로필 이미지, 이름, 점수가 표시됩니다.
     
2. 즐겨찾기 기능
   - 좋아요 버튼을 통해 사용자 정보를 저장할 수 있습니다.
   - 즐겨찾기 정보는 DataStore를 사용하여 로컬에 저장되므로, 앱을 다시 실행해도 유지됩니다.
   - 즐겨찾기 목록에서 사용자를 제거할 수 있습니다.
3. 효율적인 UI 업데이트
   - RecyclerView와 ListAdapter를 사용하여 효율적인 리스트 렌더링을 구현했습니다.
   - DiffUtil을 활용하여 변경된 항목만 업데이트합니다.
4. 네트워크 통신
   - Retrofit2를 사용하여 GitHub API와 통신합니다.
   - OkHttp 인터셉터를 사용하여 로깅 기능을 구현했습니다.
5. 비동기 처리 및 상태 관리
   - Coroutines와 Flow를 사용하여 비동기 작업을 처리합니다.
   - StateFlow를 활용하여 UI 상태를 관리합니다.
