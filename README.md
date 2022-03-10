# GitHub 유저 탐색기

## Introduction
GitHub에서 제공하는 API를 사용하여 유저를 검색하고 유저를 클릭하면 해당 유저의 상세정보 및 레파지토리 정보를 볼 수 있는 앱 입니다.

### 검색 화면
![usersearch](https://user-images.githubusercontent.com/53829792/157660909-e98e9ed2-d7ff-42e1-9e0e-5a5f40201d54.gif)
1. 서치바에 검색어(username)를 입력하면 검색어를 포함하는 모든 유저가 검색됩니다.
2. 정렬은 관련성 높은 순을 기준으로 정렬됩니다.
3. 검색 당 최대 20명의 유저를 보여주며, 스크롤이 마지막에 도달하면 자동으로 다음 유저 20명을 검색합니다.
4. 검색결과가 20개 미만일 시, 마지막 페이지로 인식하여 스크롤이 마지막에 도달해도 자동검색을 하지 않습니다.
5. 로딩중인 경우는 Loading Indicator 가 표시됩니다.


### 상세정보 화면
![repository](https://user-images.githubusercontent.com/53829792/157660885-95b60888-4ccb-45cf-a7ee-36847c45516f.gif)
1. 이름, 위치, 계정생성일, 팔로우 수 등 유저 상세정보를 보여줍니다.
2. 화면 스크롤을 감지하여 헤더 사이즈가 자연스럽게 조절됩니다.
3. 프로필사진의 밝기에 따라 뒤로가기 버튼 및 username 텍스트 색상이 전환됩니다.
3. 레파지토리 목록을 ViewPager로 보여줍니다.
4. 레파지토리 클릭 시 ViewPager가 전환되며 레파지토리 상세정보를 보여줍니다.
5. 레파지토리 상세정보엔 마크다운으로 렌더링된 README가 포함됩니다.


## Architecture
```bash
githubusersearch
├── business
│   ├── domain
│   ├── interactors
│   └── util
├── framework
│   ├── datasource
│   └── presentation
├── common
└── di
``` 
- business: 비지니스 로직의 집합
  - domain: 도메인 모델이 정의됨.
  - interactors: framework계층과 business계층을 연결하는 중간역할. usecase가 정의됨.
  - util: 비지니스 계층에서 사용되는 유틸모음.

- framework: 뷰, 프레임워크의 집합
  - datasource: 저장소를 다루는 패키지. Github API 호출시 사용되는 service, model, mapper 등이 정의됨.
  - presentation: UI 계층을 다루는 패키지. activity, fragment, theme 등이 정의됨.

- common: extension, util, constant 등 유틸성 패키지.
- di: Hilt를 이용한 Dependency Injection 구현.


## Unit Test
- 유저를 검색하는 기능에 대한 단위테스트 코드 작성
  - API호출 성공 시 유저목록 업데이트 확인
  - API호출 실패 시 Dialog Queue에 새로운 메세지(오류)가 들어 갔는 지 확인


## Development Environment
- Android Studio Bumblebee | 2021.1.1 Beta 5
- JAVA 8
- Kotlin 1.5.31


## Application Version
- minSdkVersion: 24
- targetSdkVersion: 32


## APIs
- Jetpack Compose 1.1.0-beta03
- Jetpack Navigation 2.3.5
- Hilt 2.38.1
- Retrofit 2.9.0
- Okhttp3 4.9.3
- Accompanist 0.24.3-alpha
- Coil 2.0.0.-rc01
- Webkit 1.4.0
- Material Color 0.0.7
- Palette 1.0.0

## Unit Test APIs
- Junit 4.13.2
- jupiter 5.7.0
- mockito 4.0.0