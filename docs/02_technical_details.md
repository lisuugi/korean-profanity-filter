# 핵심 로직 설명
## 파일 로딩
FileLoader가 내부 리소스(.jar 안의 파일)와 외부 파일(사용자 정의 파일)을 어떻게 구분하여 안전하게 로드하는지 설명합니다. (getResourceAsStream 사용의 중요성)

## 마스킹 로직
### 욕설이 중첩 탐지되는 경우 마스킹 방법

마스킹 로직: MaskingUtil이 중첩된 욕설(씨발새끼 vs 씨발)을 어떻게 정확하게 처리하는지
'구간 병합(Interval Merging)' 알고리즘의 필요성에 대해 설명합니다.

### 전처리 이후 마스킹 하는 경우, 원본 문장을 마스킹하여 반환하는 로직


## 필터링 순서
필터링 순서: 전처리 필터들이 어떤 순서로 동작하는지 설명합니다.

### 필터링에 대한 고민
숫자를 필터링 할 것인가.

## 탐색 알고리즘 실험 수치 기반 모델 선정
벤치마크 환경 CPU : Intel(R) Core(TM) Ultra 7 258V(2.20 GHz)

실험 데이터셋: 네이버 뉴스 댓글 모음(225MB)
~~~
Test 1 - 단어 사전 키워드가 적은 경우

Total Sentences: 2033893
Total Keywords: 443
----------------------------------------
Aho-Corasick O(N): 2211.0862ms | Throughput: 919,861.47 sentences/sec
Regex O(N): 40051.0882ms | Throughput: 50,782.47 sentences/sec
Naive-Search O(N*M): 1303.6961ms | Throughput: 1,560,097.48 sentences/sec
Aho-Corasick + Regex: 43777.9587ms | Throughput: 46,459.29 sentences/sec
----------------------------------------
Aho-Corasick is 94.48% faster than Regex.
Aho-Corasick is 69.60% slower than Naive-Search.


Test 2 - 단어 사전 키워드가 많은 경우

Total Sentences: 2033893
Total Keywords: 3715
----------------------------------------
Aho-Corasick O(N): 2574.6873ms | Throughput: 789,957.29 sentences/sec
Regex O(N): 40014.1494ms | Throughput: 50,829.34 sentences/sec
Naive-Search O(N*M): 88564.9590ms | Throughput: 22,964.99 sentences/sec
Aho-Corasick + Regex: 43953.9271ms | Throughput: 46,273.29 sentences/sec
----------------------------------------
Aho-Corasick is 93.57% faster than Regex.
Aho-Corasick is 97.09% faster than Naive-Search.


Test 3 - 아호-코라식과 완전 탐색이 비슷한 성능인 경우

Total Sentences: 2033893
Total Keywords: 470
----------------------------------------
Aho-Corasick O(N): 2139.8184ms | Throughput: 950,497.95 sentences/sec
Regex O(N): 40327.6640ms | Throughput: 50,434.19 sentences/sec
Naive-Search O(N*M): 2089.5657ms | Throughput: 973,356.81 sentences/sec
Aho-Corasick + Regex: 43461.6441ms | Throughput: 46,797.42 sentences/sec
----------------------------------------
Aho-Corasick is 94.69% faster than Regex.
Aho-Corasick is 2.40% slower than Naive-Search.
~~~

아호-코라식과 Regex는 사전에 단어들을 기반으로 자료구조를 제작하기 때문에 항상 일정한 속도를 유지합니다.
완전 탐색의 경우, String.contains() 메서드의 JVM 최적화로 단어 사전 갯수가 적을 때는 매우 빠르지만, 단어 갯수의 영향을 크게 받기 때문에 최대 470개까지 최고 성능인 점을 관찰할 수 있었습니다.

결과적으로 단어 사전에 포함된 단어 갯수가 470개 이하인 경우, 완전 탐색 알고리즘으로 작동하게 설계하였습니다.
이외의 경우에는 모두 아호-코라식으로 작동합니다.