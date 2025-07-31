[//]: # (> This document is written in Korean. [**Click here to read in English.**]&#40;./README.en.md&#41;)


> ### 주의!
> 설명을 위해 **욕설이 다수 기재**되어 있어 불쾌할 수 있습니다.

---

[![Build Status](https://github.com/lisuugi/korean-profanity-filter/actions/workflows/build.yaml/badge.svg)](https://github.com/lisuugi/korean-profanity-filter/actions)
[![codecov](https://codecov.io/gh/lisuugi/korean-profanity-filter/graph/badge.svg?token=h1E8Oi3Uin)](https://codecov.io/gh/lisuugi/korean-profanity-filter)

![Java](https://img.shields.io/badge/Java-8-red?logo=openjdk)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.lisuugi/korean-profanity-filter)](https://search.maven.org/artifact/io.github.lisuugi/korean-profanity-filter)
[![Stargazers](https://img.shields.io/github/stars/lisuugi/korean-profanity-filter?style=social)](https://github.com/lisuugi/korean-profanity-filter/stargazers)


# 한국어 비속어, 욕설 필터 라이브러리

### 개요
한국어 비속어 탐지 Java 라이브러리입니다.

압도적으로 빠르고, 가볍고, 범용적인 용도를 목표로 개발한 욕설 필터입니다.


### 기능
1. String 넣으면, 욕설인지 아닌지 boolean으로 판단.

[//]: # (2. String 넣으면, 욕설 부분만 별표&#40;*&#41;로 대체.)

[//]: # (    - 욕설 단어가 2글자 이상인 경우, 마스킹 정책 3가지 제공.)

[//]: # (      1. 첫 글자만 마스킹 안함)

[//]: # (         - 예&#41; 씨발새끼 좆같네. -> 씨*** *같네.)

[//]: # (      2. 마지막 글자만 마스킹 안함)

[//]: # (         - 예&#41; 씨발새끼 좆같네. -> ***끼 *같네.)

[//]: # (      3. 모든 글자 마스킹)

[//]: # (         - 예&#41; 씨발새끼 좆같네. -> **** *같네.)

[//]: # (    - 욕설 마스킹 유형 커스텀 가능)

[//]: # (      - 예&#41; 이 씨발새끼야. -> 이 \[검열\]야.)
2. 설정을 통해 화이트리스트, 블랙리스트 단어 수동 추가. (모든 필터링 레벨에 최우선 적용)
   - profanityWhitelist.txt, profanityBlacklist.txt를 프로젝트 최상단에 만들면 자동 인식.
3. 필터 수동 선택 적용

### 사용 방법

[//]: # (Maven Import)

[//]: # (~~~Maven)

[//]: # (<dependencies>)

[//]: # (    <dependency>)

[//]: # (        <groupId>io.github.lisuugi</groupId>)

[//]: # (        <artifactId>korean-profanity-filter</artifactId>)

[//]: # (        <version>0.1.0-alpha.1</version>)

[//]: # (    </dependency>)

[//]: # (</dependencies>)

[//]: # (~~~)

[//]: # ()
[//]: # (Gradle Import)

[//]: # (~~~Gradle)

[//]: # (dependencies {)

[//]: # (    implementation 'io.github.lisuugi:korean-profanity-filter:0.1.0-alpha.1')

[//]: # (})

[//]: # (~~~)

Spring Bean 등록 활용 예시
~~~Java
@Configuration
public class FilterConfiguration {

   @Bean
   public ProfanityFilter profanityDefaultFilter() {
      return ProfanityFilter.createDefault();
   }
   
   @Bean
   public ProfanityFilter profanityCustomFilter() {
      return ProfanityFilter.builder()
              .setLevel(WordLevel.AGGRESSIVE)
              .useRegexFilter(false)
              .build();
   }
}
~~~

### 사용자 정의 단어장 사용 방법
1. 프로젝트 최상단에 **profanityBlacklist.txt, profanityWhitelist.txt** 파일을 작성합니다.
2. **각 줄마다 하나의 단어를 작성**합니다.
3. 프로젝트를 실행할 때, **해당 위치에 파일이 있다면 자동으로 감지**하여 사전 목록에 추가, 또는 제외합니다.

### 단어장 필터링 레벨 기준
- 레벨 1 - 사전에 등재된 정확한 단어, 또는 매우 보편적으로 쓰이는 혐오의 의도가 명확한 단어.
- 레벨 2 - 사전에 등재된 단어의 변형, 또는 인터넷에서 볼 수 있는 특이 표현 **(권장)**
- 레벨 3 - 혐오의 표현이 아닐 확률이 높은 단어. (긍정 오류가 커질 우려가 있음.)

### 커스텀 필터 옵션
1. 단어 유형 선택 (복수 선택 가능)
   - 외설적인 단어
   - 폭력적인 단어
   - 혐오감을 주는 단어
   - 정치 비하, 지역 비하, 성별 비하 단어
2. 단어 매칭 API (하나만 선택 가능)
   - 정규 사전 용어 (레벨 1 - 사전에 등재된 정식 단어, 매우 빠름)
   - 범용 우회 용어 (레벨 2 - 일반적인 우회 단어)
   - 정규식 (레벨 3, 패턴 매칭을 활용, 약간 느려짐)
   - 우선순위가 떨어지는 우회 용어 (레벨 4 - 매우 엄격)

### 탐지 로직
1. 특수문자 정상 자모음 변경 (人 | 발 -> 시발)
2. 숫자 제거 (씨123발 -> 씨발)
3. 반복 문자 제거 (ㅅㅅㅅㅅㅂㅂㅂ -> ㅅㅂ)
4. 자음 모음 합치기 (ㅆ ㅣ 발 -> 씨발)
5. 문자 탐지 알고리즘 사용
   - 사전 문자 470개 이하: 완전 탐색
   - 사전 문자 470개 초과: 아호-코라식 탐색

### 사용시 주의 사항
AI를 활용하지 않기 때문에, **문맥을 고려하지 않는다**는 한계점이 존재합니다.
1. **욕설을 사용하지 않는 공격적인 글**은 탐지하지 못합니다.
2. **긍정적인 비속어 모두 필터링**됩니다.
   - 예) 존나 맛있네 -> ** 맛있네, 와 씨발 대박이다! -> 와 ** 대박이다!



### 성능 측정

추가 예정

[//]: # (원본 데이터셋은 문맥적 공격성까지 포함하고 있으나, 본 라이브러리는 명백한 키워드 기반 탐지를 목표로 하므로, 사전에 정의된 욕설 단어가 포함된 문장만 '욕설'로 재라벨링하여 평가를 진행했습니다.)

[//]: # (모든 데이터셋들을 통합하고, 전부 검수하여 명백한 비속어 사용 문장만 다시 라벨링하여 데이터를 제작하였습니다.)

[//]: # (재현율)

[//]: # (정밀도)

[//]: # (F1 Score)

[//]: # ()
[//]: # (속도 벤치마크)

[//]: # ()
[//]: # ()
[//]: # (각 단계별 측정)

[//]: # ()
[//]: # (아호코라식과 일반 대조 비교 성능 측정)

[//]: # ()
[//]: # (타 라이브러리와 비교 측정)


### 기대 효과
확실한 욕설은 빠르게 거를 수 있기 때문에, 낮은 레이턴시가 필요한 대규모 트래픽 실시간 처리 환경 또는 스타트업에서 간편하게 1차 거름망을 준비할 수 있습니다.


## 상세 문서

이 프로젝트의 아키텍처, 설계 결정, 핵심 로직에 대한 더 자세한 기술적인 설명은 [상세 문서(docs)](./docs/01_architecture.md)를 참고해 주세요.


## Credit

### 활용 정규식

https://github.com/curioustorvald/KoreanCursewordRegex


### 사전 데이터셋

나무위키 욕설/한국어 : https://namu.wiki/w/%EC%9A%95%EC%84%A4/%ED%95%9C%EA%B5%AD%EC%96%B4

https://github.com/Tanat05/korean-profanity-resources/blob/main/%EB%A6%AC%EA%B7%B8%EC%98%A4%EB%B8%8C%EB%A0%88%EC%A0%84%EB%93%9C_%ED%95%84%ED%84%B0%EB%A7%81%EB%A6%AC%EC%8A%A4%ED%8A%B8_2020.txt

https://github.com/Tanat05/korean-profanity-resources/blob/main/slang.csv

https://github.com/hlog2e/bad_word_list


### 테스트 데이터셋
adlnlp/K-MHaS : https://github.com/adlnlp/K-MHaS

ZIZUN/korean-malicious-comments-dataset : https://github.com/ZIZUN/korean-malicious-comments-dataset

2runo/Curse-detection-data : https://github.com/2runo/Curse-detection-data

https://www.kaggle.com/datasets/captainnemo9292/korean-extremist-website-womad-hate-speech-data

https://www.kaggle.com/datasets/tanat05/korean-hate-chat-data

https://github.com/kocohub/korean-hate-speech

https://github.com/smilegate-ai/korean_unsmile_dataset

https://github.com/boychaboy/KOLD

https://github.com/2runo/Curse-detection-data

https://www.kaggle.com/datasets/junbumlee/lgbt-hatespeech-comments-at-naver-news-korean


### 알고리즘 벤치마크 데이터셋

https://www.kaggle.com/datasets/junbumlee/lgbt-hatespeech-comments-at-naver-news-korean


### 참고 라이브러리
https://github.com/Tanat05/korcen

https://github.com/VaneProject/bad-word-filtering



아호코라식 라이브러리 : https://github.com/robert-bor/aho-corasick
